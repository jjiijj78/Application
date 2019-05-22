package fileIO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * ReadCSVClass
 * csv 파일을 읽고 구분자에 따라 구분하여 2차원 배열에 저장한다.
 * 최종 결과는 getCSVData()함수의 반환으로 받을 수있다.
 * return은 ArrayList<ArrayList<String>> 형태
 */
public class ReadCSVClass {
    private boolean show;
    private String fileName;
    private ArrayList<ArrayList<String>> csvFileValues = new ArrayList<ArrayList<String>>();

    /**
     * 생성자
     * 파일이름과 읽어들인 파일 내용을 콘솔에 출력할지를 결정한다.
     */
    public ReadCSVClass(String fileName, boolean show){
        this.show = show;
        this.fileName = fileName;
    }

    /**
     * csv파일을 읽고 구분자를 이용해 파싱하고 파일을 닫음
     * BUS_STATION .csv 파일에선 구분자가 쉼표(,)
     * 2015년_버스노선별_정류장별~~.csv 파일에선 구분자가 (;)
     * 
     * @param separator 구분자
     * @return 성공여부
     */
    public boolean readFile(){
        BufferedReader br =  null;
        String seporator;
        try {
            
            br = Files.newBufferedReader(Paths.get(this.fileName));
            Charset.forName("UTF-8");
            String line = "";
            line = br.readLine(); // 1행을 이용해 현재 파일의 Seporator를 찾음
            if(line.contains("\";\"")){
                seporator = ";";
            }
            else if(line.contains("\",\"")) {
                seporator = ",";
            }
            else {
                return false;
            }
            while((line = br.readLine()) != null){
                this.csvFileValues.add(this.splitLine(line, seporator)); // ","로 구분, 수정필요
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();;
        } catch (IOException e){
            System.out.println("파일 입출력 오류 " + e);
        } finally{
            try{
                if(br != null){
                    br.close();
                }
            } catch (IOException e){
                e.printStackTrace();
            }
            
            if(this.show){
                //this.showAllData();
            } else {
                System.out.println("파일 읽기 완료");
            }
        }
        return true;
    }

    /**
     * line의 맨 앞과 뒤에서 따옴표(")를 1개씩 제거하고 구분자로 자름
     * @param line csv파일에서 읽은 1행
     * @param separator 구분자로 "," 또는 ";" 이 있다.
     * @return line을 처리하여 ArrayList<String> 형태로 return한다.
     */
    private ArrayList<String> splitLine(String line, String seporator){
        // CSV 1행을 구분해서 저장
        ArrayList<String> tmpList = new ArrayList<String>();
        line = line.substring(1, line.length()- 1); // 전체 문자열 앞뒤로 따옴표 한개씩 제거
        if(seporator == ","){
            String[] array = line.split("\",\""); // 각 셀의 내용중에 ,가 포함된 내용이 있어서 ","를 한묶음으로 생각해서 제거
            tmpList = new ArrayList<String>(Arrays.asList(array));
        }
        else if(seporator == ";"){
            String[] array = line.split("\";\"");
            tmpList = new ArrayList<String>(Arrays.asList(array));
        }

        // for test
        //System.out.println(tmpList.get(0)+" "+tmpList.get(2));
        //for(String cell : tmpList){
        //    System.out.print(cell + " ");
        //}
        //System.out.println(" ");
        
        return tmpList;
    }

    /**
     * data를 출력. 확인용도
     */
    public void showAllData(){
        for(int i=0; i<10; i++){
            for(int j=0; j<this.csvFileValues.get(i).size(); j++){
                System.out.print(csvFileValues.get(i).get(j) + " ");
            }
            System.out.println("");
        }
        
        System.out.println(this.csvFileValues.size());
        System.out.println(this.csvFileValues.get(0).size());
    }
    
    /**
     * @return csvFileValues 반환
     */
    public ArrayList<ArrayList<String>> getCsvData(){
        return this.csvFileValues;
    }
    
}