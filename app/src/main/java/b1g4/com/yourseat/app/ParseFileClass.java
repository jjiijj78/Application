package app;

import java.io.IOException;
import java.util.ArrayList;
import bus.*;
import fileIO.*;

/**
 * 파싱관련 class관리 및 method호출하는 class
 * 버스관련 파싱은 Bus package의 ParseBusClass가 담당
 * 지하철관련 파싱은 Subway package의 ParsSubwayClass가 담당
 * 
 */
public class ParseFileClass {
    
    // private member variable
    private ArrayList<ArrayList<String>> valuesInFile;
    private String fileDir;
    private boolean result;

    // public member variable

    /**
     * constructor
     * @param fileDir : 파싱하려는 파일 경로
     * @param isXls : 파일 확장자가 Xls인지 아닌지 검사. 읽는 방식이 다름
     * @param isBus : bus와 subway에 대한 정보를 모두 파싱해야 하므로 둘중 어느 파일인지 검사
     */
    public ParseFileClass(String fileDir, boolean isXls, boolean isBus) {
        if (isBus) {
            this.fileDir = fileDir;
            this.valuesInFile = new ArrayList<ArrayList<String>>();

            if (isXls) {
                this.result = this.readXls(true);

                if (this.result) {
                    ParseBusClass parse = new ParseBusClass(this.valuesInFile);
                    result = parse.parsingRouteStationInfo();
                }
            } else {
                //2018년 1월부터 9월까지 perMonth 파일 파싱
                for(int month=1; month<10; month++){ 
                    this.fileDir=fileDir+"\\perMonth\\2018\\BUS_STATION_BOARDING_MONTH_20180"+month+".csv";
                    this.result=this.readCSV(true);
                    if(this.result){
                        ParsingCongestionClass parseM = new ParsingCongestionClass(this.valuesInFile);
                        this.result=parseM.parsingCongestionInfo_Month();
                        if(!this.result){
                            System.out.print("실패==parseM.parsingCongestionInfo_Month()");
                        }
                    }
                }
                if(this.result){
                    this.fileDir=fileDir+"\\perYear\\2018년_버스노선별_정류장별_시간대별_승하차_인원_정보.csv";
                    this.result=this.readCSV(true);
                    if(this.result){
                        ParsingCongestionClass parseY = new ParsingCongestionClass(this.valuesInFile);
                        this.result=parseY.parsingCongestionInfo_Year();
                        if(!this.result){
                            System.out.print("실패==parseY.parsingCongestionInfo_Year()");
                        }
                    }
                }
                if(this.result){
                    //저장한 정보를 바탕으로 혼잡도 계산
                    CalcCongestionClass calcC=new CalcCongestionClass(this.result);
                    // //혼잡도 파일로 저장
                     try {
                         WriteCsvClass tmpc = new WriteCsvClass();
                         tmpc.writeCongestion();            
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                }
            }
        } else {
            //버스가 아닐때
        }
    }


    //hdy
    /**
     * 배차간격과 첫차막차 정보가 들어있는 xls파일을 읽어야 하는데 
     * ParseFileClass(String fileDir, boolean isXls, boolean isBus)로는 
     * 코드를 추가할 곳이 없어서 새로 만들었음
     * constructor
     * @param fileDir : 파싱하려는 파일 경로
     * @param fileType : 0-배차간격과 첫차막차 정보가 들어있는 xls파일
     */
    public ParseFileClass(String fileDir, int fileType) {

        this.fileDir = fileDir;
        this.valuesInFile = new ArrayList<ArrayList<String>>();
        if(fileType==0){
            this.result = this.readXls(true);
            if (this.result) {
                ParseBusClass parse = new ParseBusClass(this.valuesInFile);
                result = parse.parsing_IntervalTime_Info();
            }
        }
    }
    

    /**
     * 모든 정보를 정리한 후 사용되는 생성자
     * 실제 어플리케이션 구동시 사용됨
     * @param fileDir : 정리된 파일의 위치
     * @param isBus : 버스에 관한 파일에 대한 정보면 true
     */
    public ParseFileClass(String fileDir, String type, boolean isBus) {
        // 최종 파일 생성 후 사용되는 생성자
        boolean result = true;
        this.fileDir = fileDir;
        this.valuesInFile = new ArrayList<ArrayList<String>>();
        result = this.readCSV(false);
        if(!result){
            System.out.println("read file Error");
            return;
        }
        ParseBusClass parse = new ParseBusClass(this.valuesInFile);
        if(type.equals("route")){
            result = parse.ParseRouteCsvFile();
        }
        else if(type.equals("station")){
            result = parse.ParseStationCsvFile();
        }
        else if(type.equals("congestion")){
            result = parse.ParseCongestionCsvFile();
        }

    }

    /**
     * isXls가 true일 시 호출되는 함수
     * xls파일을 읽고 해당 내용을 valuesInFile에 저장한다. 
     * @param isShow : true면 읽은 내용을 콘솔에 출력
     * @return 파일을 정상적으로 읽어들였을 시 true 반환
     */
    private boolean readXls(boolean isShow) {
        ReadXlsClass readxls = new ReadXlsClass(this.fileDir, isShow);
        boolean result = true;
        result = readxls.readFile();
        if (result) {
            this.valuesInFile = readxls.getXlsData();
            return result;
        } else {
            System.out.println("파일 읽기 실패");
            return result;
        }
    }

    /**
     * isXls가 false일 시 호출되는 함수
     * csv파일을 읽고 해당 내용을 valuesInFile에 저장한다.
     * @param isShow : true면 읽은 내용을 콘솔에 출력
     * @return 파일을 정상적으로 읽어들였을 시 true 반환
     */
    private boolean readCSV(boolean isShow){
        ReadCSVClass readCSV =new ReadCSVClass(this.fileDir, isShow);
        boolean result = true;
        result = readCSV.readFile();
        if(result){
            this.valuesInFile = readCSV.getCsvData();
            return result;
        }
        else{
            System.out.println("파일 읽기 실패" + this.fileDir);
            return result;
        }
    }
}