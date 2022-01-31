package Java_Project;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CsvExport extends CsvUtil {
    // 받아오는 배열 번호들 필드에 매칭되게 상수로 선언
    private final static int ID = 0;
    private final static int FIRST_NAME = 1;
    private final static int LAST_NAME = 2;
    private final static int EMAIL = 3;
    private final static int GENDER = 4;
    private final static int SIGNUP_DATE = 5;
    private final static int LAST_LOGIN_DATE = 6;
    private final static int LAST_LOGIN_TIME = 7;
    private final static int POINT = 8;

    private final ExportType exportType;

    // 각 출력 타입 열거형 선언
    public enum ExportType {
        LOG("log.csv"),
        MALE("male.csv"),
        FEMALE("female.csv"),
        LAST_LOGIN("last_login.csv"),
        AM("am.csv"),
        BEST("best.csv");

        private final String fileName;

        ExportType() {
            fileName = null;
        }

        ExportType(String fileName) {
            this.fileName = fileName;
        }

        public String getFileName() {
            return fileName;
        }
    }

    public CsvExport(String filename, ExportType exportType) {
        super(filename);
        this.exportType = exportType;
    }

    public CsvExport(ExportType exportType) {
        super();
        this.exportType = exportType;
    }

    // 각 이넘 타입에 따른 기능 메소드 실행하는 executeExport() 메소드 정의
    public List<String[]> executeExport() throws ParseException {
        switch (exportType) {
            case MALE:
                return genderCsvExport(ExportType.MALE);
            case FEMALE:
                return genderCsvExport(ExportType.FEMALE);
            case LAST_LOGIN:
                return loginCsvExport(ExportType.LAST_LOGIN);
            case AM:
                return amCsvExport(ExportType.AM);
            case BEST:
                return bestCsvExport(ExportType.BEST);
        }
        return null;
    }

    // 성별 출력 기능. 파라미터로 EnumType을 받아 male, femal csv 구분하여 출력
    public List<String[]> genderCsvExport(ExportType exportType) throws ParseException {
        CsvUtil csvUtil = new CsvUtil(ExportType.LOG.getFileName());
        List<String[]> data = csvUtil.readCsv();
        List<Log> gList = new ArrayList<>();
        // 데이트형 String을 Date 형으로 바꾸기 위해 SimpleDateForamt 선언
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        String gender = exportType.name().substring(0, 1) + exportType.name().substring(1).toLowerCase();
        // csvUtil.readCsv()를 통해 만든 data 컬렉션 객체에 Iterator 적용
        Iterator<String[]> it = data.iterator();
        while (it.hasNext()) {  // hasNext()로 요소가 존재할때까지 반복
            String[] array = it.next();     // next()로 요소의 값을 array 배열에 대입
            if (array[GENDER].equals(gender)) {     // 성별 배열이 ExportType과 일치하는 성별일때 gList에 데이터를 담는다.
                gList.add(new Log(array[ID], array[FIRST_NAME], array[LAST_NAME], array[EMAIL], array[GENDER],
                        dateFormat.parse(array[SIGNUP_DATE]), dateFormat.parse(array[LAST_LOGIN_DATE]), array[LAST_LOGIN_TIME], Double.parseDouble(array[POINT])));
            }   // Date 형과 Double은 각 자료형에 맞게 파싱한다.
        }

        List<String[]> writeData = new ArrayList<>();
        // csv 파일을 만들 writeData에 컬렉션에 gList의 값들을 다시 대입한다.
        // OpenCSV는 List<String[]> 형태만을 지원하기 때문에 추후 중간 처리 과정을 위해 import와 export를 분리하여 준다.
        for (int i = 0; i < gList.size(); i++) {
            writeData.add(new String[]{gList.get(i).getId(), gList.get(i).getFullName(), gList.get(i).getEmail(),
                    gList.get(i).getGender(), dateFormat.format(gList.get(i).getSignupDate()),
                    dateFormat.format(gList.get(i).getLastLoginDate()), gList.get(i).getLastLoginTime(), String.valueOf(gList.get(i).getPoint())});
        }

        csvUtil.writeCsv(writeData, exportType.getFileName());

        return writeData;
    }

    public List<String[]> loginCsvExport(ExportType exportType) throws ParseException {
        CsvUtil csvUtil = new CsvUtil(ExportType.LOG.getFileName());
        List<String[]> data = csvUtil.readCsv();
        List<Log> logList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        // 문제에서 최근 3달을 3월 부터라고 설정했기 때문에 month: 2, date: 0을 입력한다. (실제로는 month: 3, date: 1 설정 됨)
        calendar.set(2020, 2, 0);

        Iterator<String[]> it = data.iterator();
        while (it.hasNext()) {
            String[] array = it.next();
            // parse 익셉션 방지를 위해 날짜 형태가 들어간 것 조건절 걸어준다.
            if (array[LAST_LOGIN_DATE].contains("/")) {
                // LAST_LOGIN_DATE와 상단에서 설정한 3월 1을 비교하여 LAST_LOGIN_DATE가 더 크면 조건 작동하여 데이터들 LogList에 담는다.
                if (dateFormat.parse(array[LAST_LOGIN_DATE]).compareTo(calendar.getTime()) > 0) {
                    logList.add(new Log(array[ID], array[FIRST_NAME], array[LAST_NAME], array[EMAIL], array[GENDER],
                            dateFormat.parse(array[SIGNUP_DATE]), dateFormat.parse(array[LAST_LOGIN_DATE]), array[LAST_LOGIN_TIME], Double.parseDouble(array[POINT])));
                }
            }
        }

        // 람다식 표기: Comparator<Log> comparator = (o1, o2) -> o1.getLastLoginDate().compareTo(o2.getLastLoginDate());
        // 날짜순으로 내림차순 정렬을 해줘야 하기 때문에 Comprator를 선언하여 사용한다.
        // 날짜 비교는 compareTo를 이용한다.
        Comparator<Log> comparator = new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return o1.getLastLoginDate().compareTo(o2.getLastLoginDate());
            }
        };
        // 위에서 정의하여 선언한 comprator 객체를 Collections.sort의 매개변수로 넣어 날짜 오름차순으로 logList 컬렉션을 정렬한다.
        Collections.sort(logList, comparator);
        List<String[]> writeData = new ArrayList<>();
        // csv 파일 출력을 위한 writeDate 컬렉션에 데이터들 String 형으로 삽입
        for (int i = 0; i < logList.size(); i++) {
            writeData.add(new String[]{logList.get(i).getId(), logList.get(i).getFullName(), logList.get(i).getEmail(),
                    logList.get(i).getGender(), dateFormat.format(logList.get(i).getSignupDate()),
                    dateFormat.format(logList.get(i).getLastLoginDate()), logList.get(i).getLastLoginTime(), String.valueOf(logList.get(i).getPoint())});
        }

        csvUtil.writeCsv(writeData, exportType.getFileName());

        return writeData;
    }

    public List<String[]> amCsvExport(ExportType exportType) throws ParseException {
        CsvUtil csvUtil = new CsvUtil(ExportType.LOG.getFileName());
        List<String[]> data = csvUtil.readCsv();
        List<Log> logList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        Iterator<String[]> it = data.iterator();
        while (it.hasNext()) {
            String[] array = it.next();
            // LAST_LOGIN_TIME에 AM이 포함 될 경우 조건 작동
            if (array[LAST_LOGIN_TIME].contains("AM")) {
                // 예를들어 06:00 AM에서 뒤에 AM과 중간의 :을 제거하고 0600을 Integer로 바꿔 0 과 600 사이의 LAST_LOGIN_TIME을 구한다.
                // 해당 시간 데이터의 경우 SimpleDateFormat으로 format(HH:MM a)을 일치시켜도 계속 에러가 떠서 이런식으로 프로그래밍 하였다.
                int intLoginTime = Integer.parseInt(array[LAST_LOGIN_TIME].substring(0, array[LAST_LOGIN_TIME].length() - 3).replaceAll(":", ""));
                if (intLoginTime >= 0 && intLoginTime <= 600) {
                    logList.add(new Log(array[ID], array[FIRST_NAME], array[LAST_NAME], array[EMAIL], array[GENDER],
                            dateFormat.parse(array[SIGNUP_DATE]), dateFormat.parse(array[LAST_LOGIN_DATE]), array[LAST_LOGIN_TIME], Double.parseDouble(array[POINT])));
                }
            }
        }

        List<String[]> writeData = new ArrayList<>();

        for (
                int i = 0; i < logList.size(); i++) {
            writeData.add(new String[]{logList.get(i).getId(), logList.get(i).getFullName(), logList.get(i).getEmail(),
                    logList.get(i).getGender(), dateFormat.format(logList.get(i).getSignupDate()),
                    dateFormat.format(logList.get(i).getLastLoginDate()), logList.get(i).getLastLoginTime(), String.valueOf(logList.get(i).getPoint())});
        }

        csvUtil.writeCsv(writeData, exportType.getFileName());

        return writeData;
    }

    public List<String[]> bestCsvExport(ExportType exportType) throws ParseException {
        CsvUtil csvUtil = new CsvUtil(ExportType.LOG.getFileName());
        List<String[]> data = csvUtil.readCsv();
        List<Log> logList = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        Iterator<String[]> it = data.iterator();
        while (it.hasNext()) {
            String[] array = it.next();
            if (array[POINT].contains(".")) {
                logList.add(new Log(array[ID], array[FIRST_NAME], array[LAST_NAME], array[EMAIL], array[GENDER],
                        dateFormat.parse(array[SIGNUP_DATE]), dateFormat.parse(array[LAST_LOGIN_DATE]), array[LAST_LOGIN_TIME], Double.parseDouble(array[POINT])));
            }
        }

        // 람다식 표기: Comparator<Log> comparator = (o1, o2) -> Double.compare(o1.getPoint(),o2.getPoint()) * -1;
        // Double 형 비교에서는 부등호나 compareTo가 아니라 Doulbe.compare를 사용하여야 제대로 된 비교 결과를 얻을 수 있다. (부동소수점)
        // 내림차순 정렬이기 때문에 결과값에 -1을 곱해준다.
        Comparator<Log> comparator = new Comparator<Log>() {
            @Override
            public int compare(Log o1, Log o2) {
                return Double.compare(o1.getPoint(), o2.getPoint()) * -1;
            }
        };

        // 위에서 정의하여 선언한 comprator 객체를 Collections.sort의 매개변수로 넣어 날짜 내림차순으로 logList 컬렉션을 정렬한다.
        Collections.sort(logList, comparator);
        List<String[]> writeData = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            writeData.add(new String[]{logList.get(i).getId(), logList.get(i).getFullName(), logList.get(i).getEmail(),
                    logList.get(i).getGender(), dateFormat.format(logList.get(i).getSignupDate()), dateFormat.format(logList.get(i).getLastLoginDate()),
                    logList.get(i).getLastLoginTime(), String.valueOf(logList.get(i).getPoint())});
        }

        csvUtil.writeCsv(writeData, exportType.getFileName());

        return writeData;
    }
}
