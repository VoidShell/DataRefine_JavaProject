package Java_Project;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvUtil {

    private String filename;

    public CsvUtil(String filename) {
        this.filename = filename;
    }

    public CsvUtil() {
    }
    // OpenCSV 라이브러리를 활용한 CSV 파일 read 메소드
    public List<String[]> readCsv() throws ParseException {
        List<String[]> data = new ArrayList<String[]>();

        try {   // MS Excel에서 한글이 잘 보이도록 charsetName을 EUC-KR로 설정
                // EUC-KR 설정을 위해서는 InputStreamReader를 거쳐야 한다.
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(filename), "EUC-KR"));
            String[] s;
            // CSVReader 형의 reader 객체의 메소드 readNext()를 사용해 라인단위로 CSV를 읽어온다.
            // 한 라인을 String 형 s배열에 컬럼별로 저장하고(readNext의 반환값이 null이 아닐때까지 반복)
            while ((s = reader.readNext()) != null) {
                data.add(s);    // 그렇게 저장한 배열을 List<String[]> data에 다시 넣는다.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;    // data 리스트 객체를 반환한다.
    }
    // OpenCSV 라이브러리를 활용한 CSV 파일 write 메소드
    public void writeCsv(List<String[]> data, String filename) throws ParseException {
        try {   // MS Excel에서 한글이 잘 보이도록 charsetName을 EUC-KR로 설정
            CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filename), "EUC-KR"));
            //List 컬렉션에서 String[] 출력을 위한 Iterator 형 it 객체 생성. (data를 iterator() 하는)
            Iterator<String[]> it = data.iterator();
            try {
                while (it.hasNext()) {  //hasNext() 메소드는 읽어올 요소가 있는지 한 줄씩 확인. 있으면 true 리턴
                    String[] s = (String[]) it.next(); // next() 메소드는 다음 요소를 나타냄. 요소들을 배열에 저장
                    writer.writeNext(s); // writer 객체로 배열을 CSV 파일에 쓴다.
                }
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}