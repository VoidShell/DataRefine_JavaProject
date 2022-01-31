package Java_Project;

import java.text.ParseException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        boolean i = true;

        while(i) {
            System.out.println("#### Log CSV 출력기 ####");
            System.out.println("1. 남자 고객 출력: " + CsvExport.ExportType.MALE.getFileName());
            System.out.println("2. 여자 고객 출력: " + CsvExport.ExportType.FEMALE.getFileName());
            System.out.println("3. 마지막 접속 날짜: " + CsvExport.ExportType.LAST_LOGIN.getFileName());
            System.out.println("4. 새벽 로그인 시간: " + CsvExport.ExportType.AM.getFileName());
            System.out.println("5. 포인트 가장 높은: " + CsvExport.ExportType.BEST.getFileName());
            System.out.println("6. 모든 파일 출력");
            System.out.print("> 메뉴를 선택하시오(0번 프로그램 종료) ---> ");

            switch (scanner.nextInt()) {
                case 0:
                    i = false;
                    break;
                case 1:
                    CsvExport maleCsvExport = new CsvExport(CsvExport.ExportType.MALE);
                    maleCsvExport.executeExport();
                    System.out.println("");
                    System.out.println("> " + maleCsvExport.executeExport().size() + "행 " +  CsvExport.ExportType.MALE.getFileName() + " 파일 출력 완료.");
                    System.out.println("");
                    break;
                case 2:
                    CsvExport femaleCsvExport = new CsvExport(CsvExport.ExportType.FEMALE);
                    femaleCsvExport.executeExport();
                    System.out.println("");
                    System.out.println("> " + femaleCsvExport.executeExport().size() + "행 " +  CsvExport.ExportType.FEMALE.getFileName() + " 파일 출력 완료.");
                    System.out.println("");
                    break;
                case 3:
                    CsvExport loginCsvExport = new CsvExport(CsvExport.ExportType.LAST_LOGIN);
                    loginCsvExport.executeExport();
                    System.out.println("");
                    System.out.println("> " + loginCsvExport.executeExport().size() + "행 " +  CsvExport.ExportType.LAST_LOGIN.getFileName() + " 파일 출력 완료.");
                    System.out.println("");
                    break;
                case 4:
                    CsvExport amCsvExport = new CsvExport(CsvExport.ExportType.AM);
                    amCsvExport.executeExport();
                    System.out.println("");
                    System.out.println("> " + amCsvExport.executeExport().size() + "행 " +  CsvExport.ExportType.AM.getFileName() + " 파일 출력 완료.");
                    System.out.println("");
                    break;
                case 5:
                    CsvExport bestCsvExport = new CsvExport(CsvExport.ExportType.BEST);
                    bestCsvExport.executeExport();
                    System.out.println("");
                    System.out.println("> " + bestCsvExport.executeExport().size() + "행 " +  CsvExport.ExportType.BEST.getFileName() + " 파일 출력 완료.");
                    System.out.println("");
                    break;
                case 6:
                    CsvExport maleCsvExportA = new CsvExport(CsvExport.ExportType.MALE);
                    maleCsvExportA.executeExport();
                    System.out.println("");
                    System.out.println("> " + maleCsvExportA.executeExport().size() + "행 " +  CsvExport.ExportType.MALE.getFileName() + " 파일 출력 완료.");

                    CsvExport femaleCsvExportA = new CsvExport(CsvExport.ExportType.FEMALE);
                    femaleCsvExportA.executeExport();
                    System.out.println("> " + femaleCsvExportA.executeExport().size() + "행 " +  CsvExport.ExportType.FEMALE.getFileName() + " 파일 출력 완료.");

                    CsvExport loginCsvExportA = new CsvExport(CsvExport.ExportType.LAST_LOGIN);
                    loginCsvExportA.executeExport();
                    System.out.println("> " + loginCsvExportA.executeExport().size() + "행 " +  CsvExport.ExportType.LAST_LOGIN.getFileName() + " 파일 출력 완료.");

                    CsvExport amCsvExportA = new CsvExport(CsvExport.ExportType.AM);
                    amCsvExportA.executeExport();
                    System.out.println("> " + amCsvExportA.executeExport().size() + "행 " +  CsvExport.ExportType.AM.getFileName() + " 파일 출력 완료.");

                    CsvExport bestCsvExportA = new CsvExport(CsvExport.ExportType.BEST);
                    bestCsvExportA.executeExport();
                    System.out.println("> " +bestCsvExportA.executeExport().size() + "행 " +  CsvExport.ExportType.BEST.getFileName() + " 파일 출력 완료.");
                    System.out.println("");
                    break;
                default:
                    System.out.println("잘 못 입력하였습니다. 초기 메뉴로 돌아갑니다.");
            }
        }
    }
}