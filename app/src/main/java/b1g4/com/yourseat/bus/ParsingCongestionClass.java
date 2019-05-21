package b1g4.com.yourseat.bus;

import java.util.ArrayList;

/**
 * ParsingCongestionClass
 */
public class ParsingCongestionClass{
    private ArrayList<ArrayList<String>> valuesInFile = new ArrayList<ArrayList<String>>();
    private int rowNum;
    private BusInfoClass busInfo = BusInfoClass.getInstance();

    /**
     * constructor
     */
    public ParsingCongestionClass(ArrayList<ArrayList<String>> valuesInFile){
        this.valuesInFile = valuesInFile;
        this.rowNum = this.valuesInFile.size();
    }


    public boolean parsingCongestionInfo_Month(){
        boolean result = true;

        // 모든 정보를 congestion class에 존재하는 변수에 저장
        for(int i=0; i<this.rowNum; i++){
            String date = this.valuesInFile.get(i).get(0);
            String routeName = this.valuesInFile.get(i).get(1);
            String stationId = this.valuesInFile.get(i).get(3);
            Double totalRide = Double.parseDouble(this.valuesInFile.get(i).get(6)); // 승차총승객수
            Double totalAlight = Double.parseDouble(this.valuesInFile.get(i).get(7)); // 하차총승객수

            if(busInfo.isCongestionExist(routeName)){
                // 해당 노선이 있으면 CongestionClass 내부에 Station별 정보를 추가하는것으로 함.
                int day = busInfo.getCongestinoClass(routeName).WhatDay(date);
                busInfo.getCongestinoClass(routeName).setTotalDaysInfo(stationId, day);
                busInfo.getCongestinoClass(routeName).setTotalPeopleInfo(stationId, day, totalRide, totalAlight);
                //busInfo.setCongestion(temp);
            }else{
                //없으면 새로 추가하면 됨
                CongestionClass temp=new CongestionClass(routeName);
                if(busInfo.isRouteExist(routeName)){
                    temp.setStationList(busInfo.getRouteInfo(routeName).stationList);

                    temp.routeName = routeName;
                    int day = temp.WhatDay(date);
                    temp.setTotalDaysInfo(stationId, day);
                    temp.setTotalPeopleInfo(stationId, day, totalRide, totalAlight);
                    busInfo.setCongestion(temp);
                }
            }
        }
        System.out.println("parsingCongestionInfo_Month()");
        return result;
    }

    public boolean parsingCongestionInfo_Year(){
        // CongestionClass 접근방식 전부 수정 필요.
        boolean result = true;
        System.out.println("year file start");
        for(int i=0; i<this.rowNum; i++){
            String routeName = this.valuesInFile.get(i).get(2);
            if(!busInfo.isRouteExist(routeName))
                continue;
            String stationId = this.valuesInFile.get(i).get(4);
            Double timeRide[] = new Double[24];
            Double timeAlight[] = new Double[24];

            for(int h=0; h<24; h++){
                timeRide[h] = Double.parseDouble(this.valuesInFile.get(i).get(8+2*h));
                timeAlight[h] = Double.parseDouble(this.valuesInFile.get(i).get(9+2*h));
            }
            if(busInfo.isCongestionExist_station(routeName, stationId)){

                //정보 추가후 congestionList에 다시 삽입
                //CongestinoClass tmp=busInfo.getCongestinoClass(stationID_routeName);
                for(int h=0; h<24; h++){
                    busInfo.getCongestinoClass(routeName).setTotalByTimeInfo(stationId, h, timeRide[h], timeAlight[h]);
                }
                //busInfo.setCongestion(tmp);
            }else{
                System.out.println("perMonth에는 없는 노선&정류장에 대한 정보가 perYear에는 있다!! 파일오류발생: "+routeName+ " : " + stationId);
                //return false;
            }
        }
        return result;
    }
}
