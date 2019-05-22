package b1g4.com.yourseat.bus;



import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import b1g4.com.yourseat.FileIO.WriteCsvClass;

/**
 * ParseBusClass
 * 전반적인 버스와 정류장 정보를 모두 정리 한 후
 * Congestion관련 정보를 정리한다.
 */
public class ParseBusClass {
    // private member variable
    private BusInfoClass busInfo;
    private ArrayList<ArrayList<String>> valuesInFile;
    private int rowNum;
    private int columnNum;

    /**
     * Constructor
     * @param valuesInFile : 파일을 읽고 저장된 정보
     */
    public ParseBusClass(ArrayList<ArrayList<String>> valuesInFile){
        this.busInfo = BusInfoClass.getInstance();
        this.valuesInFile = valuesInFile;
        this.rowNum = this.valuesInFile.size();
        this.columnNum = this.valuesInFile.get(0).size();
    }

    /**
     * BusInfoClass의 Route, Station관련 HashMap에 정보를 추가하는 작업
     * 이 작업이 끝나면 배차간격과 첫차막차시간을 읽고 //hdy
     * Congestion관련 작업이 수행되어야 한다. 
     * @return 모든 파일 입출력 작업이 정상적으로 끝마치면 true
     */
    public boolean parsingRouteStationInfo(){
        for (int i = 0; i < this.rowNum; i++) {

            StationClass sta;
            RouteClass rta;
            String routeName = this.valuesInFile.get(i).get(1);
            String stationId = this.valuesInFile.get(i).get(4);

            if (busInfo.isRouteExist(routeName)) {
                // route instance 존재
                if (busInfo.isStationExist(stationId)) {
                    // route instance 존재, station instance 존재
                    sta = busInfo.getStationInfo(stationId);
                    sta.setRouteInfo(routeName);
                } else {
                    // route instance 존재, station instance 존재 x
                    sta = new StationClass(this.valuesInFile.get(i).subList(4, columnNum),
                            this.valuesInFile.get(i).get(1));
                            //this.valuesInFile.get(i).get(0));
                    busInfo.setStation(sta);

                }
                rta = busInfo.getRouteInfo(routeName);
                rta.setStationInfo(stationId);
            } else {
                // route instance 존재 x
                if (busInfo.isStationExist(stationId)) {
                    // route instance 존재x, station instance 존재
                    rta = new RouteClass(this.valuesInFile.get(i), this.valuesInFile.get(i).get(4));
                    busInfo.setRoute(rta);
                    sta = busInfo.getStationInfo(stationId);
                    sta.setRouteInfo(routeName);
                } else {
                    // route instance 존재 x, station instance 존재 x
                    rta = new RouteClass(this.valuesInFile.get(i), this.valuesInFile.get(i).get(4));
                    sta = new StationClass(this.valuesInFile.get(i).subList(4, columnNum),
                            //this.valuesInFile.get(i).get(0));
                            this.valuesInFile.get(i).get(1));
                    busInfo.setStation(sta);
                    busInfo.setRoute(rta);
                }
            }
        }


        //csv파일 생성확인 코드 , 삭제예정
      /*  try {
            WriteCsvClass tmpc = new WriteCsvClass();
            //tmpc.writeRouteClass(busInfo.getRouteHashMap());
            tmpc.writeRouteClass_new(busInfo.getRouteHashMap());
           
            tmpc.writeStationClass(busInfo.getStationHashMap());
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return true;
    }

    //hdy
    /**
     * 노선별로 평/토/일 배차간격과 첫차,막차시간을 route에 저장
     * @return 모든 파일 입출력 작업이 정상적으로 끝마치면 true
     */
    public boolean parsing_IntervalTime_Info(){
        System.out.println("test=="+this.rowNum);
        for (int i = 0; i < 655; i++) {
            String routeName = this.valuesInFile.get(i).get(1);

                if(busInfo.isRouteExist(routeName)){
                    System.out.println("test==parsing_InternalTime_Info : "+routeName+" "+this.valuesInFile.get(i).size());
                    //route instance 존재
                    ArrayList<Integer> intervals=new ArrayList<>();
                    ArrayList<Integer> times=new ArrayList<>();
    
                    for(int j=2;j<5;j++){
                        String intString=this.valuesInFile.get(i).get(j).substring(0,this.valuesInFile.get(i).get(j).indexOf("."));
                        intervals.add(Integer.valueOf(intString));
                    }
    
                    for(int j=5;j<7;j++){
                        String tmp=this.valuesInFile.get(i).get(j).substring(0,this.valuesInFile.get(i).get(j).indexOf("."));
                        Integer hour=0;
                        Integer minute=0;
                        if(tmp.length()==1 || tmp.length()==2){  //00:30, 00:04 같은 경우
                            hour=0;
                            minute=Integer.valueOf(tmp);
                        }
                        else if(tmp.length()==3){ //05:32 같은 경우
                            hour=Integer.valueOf(tmp.substring(0, 1));
                            minute=Integer.valueOf(tmp.substring(1));
                        }else if(tmp.length()==4){ //12:57 같은 경우
                            hour=Integer.valueOf(tmp.substring(0, 2));
                            minute=Integer.valueOf(tmp.substring(2));
                        }else{
                            System.out.println("test==parsing_InternalTime_Info() 첫차막차 시간 파싱 오류..."+routeName);
                        }                        
                        minute=minute+60*hour;
                        times.add(minute);
                    }
                    busInfo.setRoute_Interval(routeName, intervals);
                    busInfo.setRoute_Time(routeName, times);
                }else{
                    //route instance 존재 x
                    System.out.println("test=="+routeName+"에 해당하는 instance가 존재x");
                }
        }

    

        //csv파일 생성확인 코드 , 삭제예정
        try {
            WriteCsvClass tmpc = new WriteCsvClass();
            tmpc.writeRouteClass_new(busInfo.getRouteHashMap());
           
            tmpc.writeStationClass(busInfo.getStationHashMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean ParseRouteCsvFile(){
        boolean result = true;
        RouteClass rta;
        for(int i=0; i<this.rowNum; i++){
            ArrayList<Integer> interval = new ArrayList<Integer>();
            ArrayList<Integer> time = new ArrayList<Integer>();
            interval.add(Integer.parseInt(this.valuesInFile.get(i).get(2)));
            interval.add(Integer.parseInt(this.valuesInFile.get(i).get(3)));
            interval.add(Integer.parseInt(this.valuesInFile.get(i).get(4)));
            time.add(Integer.parseInt(this.valuesInFile.get(i).get(5)));
            time.add(Integer.parseInt(this.valuesInFile.get(i).get(6)));
            String stationId = this.valuesInFile.get(i).get(7);
            rta = new RouteClass(this.valuesInFile.get(i).subList(0,2), stationId);
            rta.setInterval(interval);
            rta.setTime(time);
            this.columnNum = this.valuesInFile.get(i).size();
            for(int j=8; j<this.columnNum-1; j++){
                rta.setStationInfo(this.valuesInFile.get(i).get(j));
            }
            busInfo.setRoute(rta);
        }
        return result;
    }

    public boolean ParseStationCsvFile(){
        boolean result = true;
        StationClass sta;
        for(int i=0; i<this.rowNum; i++){
            sta = new StationClass(this.valuesInFile.get(i).subList(0,5), this.valuesInFile.get(i).get(5));
            this.columnNum = this.valuesInFile.get(i).size();
            for(int j=6; j<this.columnNum; j++){
                sta.setRouteInfo(this.valuesInFile.get(i).get(j));
            }
            busInfo.setStation(sta);
        }
        return result;
    }

    public boolean ParseCongestionCsvFile(){
        boolean result = true;
        String currentRouteName = new String();
        String pastRouteName = new String();
        String stationId = new String();
        CongestionClass cong;

        // congestion class 초기화를 위한 부분 첫번째 줄 파싱
        currentRouteName = this.valuesInFile.get(0).get(0);
        cong = new CongestionClass(currentRouteName);
        cong.routeName = currentRouteName;
        pastRouteName = currentRouteName;
        ParseCongRow(this.valuesInFile.get(0), cong);

        // 2번째 row부터 마지막 row까지 파싱
        for(int i=1; i<this.rowNum; i++){
            currentRouteName = this.valuesInFile.get(i).get(0);
            if(!currentRouteName.equals(pastRouteName)){
                busInfo.setCongestion(cong);
                cong = new CongestionClass(currentRouteName);
                cong.routeName = currentRouteName;
                pastRouteName = currentRouteName;
            }
            ParseCongRow(this.valuesInFile.get(i), cong);
        }


        return result;
    }
    private CongestionClass ParseCongRow(ArrayList<String> rowInfo, CongestionClass cong){
        //0번 column은 처리 후 불러짐
        //1번 column부터 처리
        String stationId = rowInfo.get(1);
        cong.stationList.add(stationId);
        ArrayList<Double> values = new ArrayList<Double>();
        for(int i=2; i<this.columnNum; i++){
            if(i == this.columnNum-1){
                String tmp = rowInfo.get(i).substring(0, rowInfo.get(i).length()-1);
                values.add(Double.parseDouble(tmp));
                break;
            }
            values.add(Double.parseDouble(rowInfo.get(i)));
        }

        // ArrayList
        Double dArr[] = new Double[24];
        HashMap<Integer, Double[]> tmpPas = new HashMap<Integer, Double[]>();
        for(int i=0; i<24*3; i++){
            dArr[i%24] = Double.parseDouble(rowInfo.get(i+2));
            if(i%24 == 0 && i != 0){
                tmpPas.put(i/24 - 1, dArr);
            }
        }
        tmpPas.put(2, dArr);
        cong.passengerNum.put(stationId, tmpPas);

        double iArr[] = new double[24];
        HashMap<Integer, double[]> tmpCong = new HashMap<Integer, double[]>();
        for(int i=24*3; i< 24*6; i++){
            if(i == 24*6 -1){
                String tmp = rowInfo.get(i+2).substring(0, rowInfo.get(i+2).length()-1);
                iArr[i%24] = Double.parseDouble(tmp);
                break;
            }
            iArr[i%24] = Double.parseDouble(rowInfo.get(i+2));
            if(i%24 == 0 && i != 24*3){
                tmpCong.put((i/24 -1)%3, iArr);
            }
        }
        tmpCong.put(2, iArr);
        cong.congestion.put(stationId, tmpCong);
        return cong;
    }

}