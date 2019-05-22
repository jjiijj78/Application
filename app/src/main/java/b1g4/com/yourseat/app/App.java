
package b1g4.com.yourseat.app;

import java.util.ArrayList;

import b1g4.com.yourseat.bus.BusInfoClass;


public class App {

    //routecsv.csv, stationcsv.csv, congestioncsv.csv 를 읽는 코드 ==> 안드로이드 앱에선 이 함수를 사용
    public boolean saveBusInfo_for_android(String routecsvDir, String stationcsvDir, String congestioncsvDir){
        boolean status=true;
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");

        ParseFileClass parse_route= new ParseFileClass(routecsvDir,"route", true);

        ParseFileClass parse_station= new ParseFileClass(stationcsvDir,"station", true);

        ParseFileClass parse_congestion= new ParseFileClass(congestioncsvDir,"congestion", true);

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
        return status;
    }

    //routecsv.csv, stationcsv.csv, congestioncsv.csv 를 읽는 코드 ==> 안드로이드 앱에선 이 함수를 사용
    public boolean saveBusInfo(){
        boolean status=true;
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");

        String routecsvDir = currentPath + "\\routecsv.csv";
        ParseFileClass parse_route= new ParseFileClass(routecsvDir,"route", true);

        String stationcsvDir= currentPath+"\\stationcsv.csv";
       ParseFileClass parse_station= new ParseFileClass(stationcsvDir,"station", true);

        String congestioncsvDir= currentPath+"\\congestioncsv.csv";
        ParseFileClass parse_congestion= new ParseFileClass(congestioncsvDir,"congestion", true);

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
        return status;
    }


    //routecsv.csv, stationcsv.csv, congestioncsv.csv 를 만드는 코드
    public boolean makeBusInfo(){
        boolean status=true;
        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

       BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");

        String fileDir1 = currentPath + "\\OSSWProj\\20190124기준_서울시_버스노선정보_new.xls";
        ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);

        String fileDir2 = currentPath + "\\OSSWProj\\20190124기준_서울시_노선현황_첫차막차배차_new.xls";
        ParseFileClass parse2 = new ParseFileClass(fileDir2, 0);

        String fileDir3 = currentPath + "\\OSSWProj\\data\\bus";
        ParseFileClass parse3= new ParseFileClass(fileDir3, false, true);

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
        return status;
    }



    /*
    *       SearchPath s=new SearchPath(); //노량진에서 중앙대
       ArrayList<ArrayList<String>> str= s.getPathsFromStations(126.9403932611921, 37.51070581104394,126.95678703483273,37.506391727320924);

       ArrayList<String> yhpath=str.get(0);

       RecommendPath recommendPath=new RecommendPath();
       //경로 사이의 모든 정류장 구하기
      // recommendPath.getStationListOnPath(yhpath);
        //가장 통계치가좋은 정류소를 고름
       //boolean hey=recommendPath.calcTotalCongestionInPath();
    *
    *
    * */
}