package b1g4.com.yourseat.bus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * CalcCongestionClass
 * 모든 계산 결과는 Congestino Class 내부에 저장된다!!!!
 */
public class CalcCongestionClass {
    private Boolean status;
    private BusInfoClass busInfo = BusInfoClass.getInstance();

    /**
     * constructor for calculate congestion
     */
    public CalcCongestionClass(boolean status){
        if(status){
            if(calc_getOnOff()){
                if(calc_Passenger()){
                    this.status=calc_congestion();
                }else{
                    System.out.println("CalcCongestionClass : 재차인원 계산 실패");
                }
            }else{
                System.out.println("CalcCongestionClass : 승,하차 인원 계산 실패");
            }
        }
    }

    /**
     * 특정 정류장, 특정 노선, 특정 요일, 시간별로 승차, 하차 인원수 계산
     * 각 congestino class instance에 값을 저장한다.
     * @return 수행성공여부
     */
    private boolean calc_getOnOff(){
        for(String routeName : busInfo.getCongestionHashMap().keySet()){
            busInfo.getCongestionHashMap().get(routeName).calcGettingOnAndOff();
        }
        return true;
    }

    /**
     * 각 노선별로 재차인원을 구함.
     * @return
     */
    private boolean calc_Passenger(){
        boolean status=true;
        for(String routeName : busInfo.getCongestionHashMap().keySet()){
            for(String stationId : busInfo.getCongestionHashMap().get(routeName).dayPassengerNum_getOnOff.keySet()){
                HashMap<Integer, Double[]> tmp = new HashMap<Integer, Double[]>();
                for(int day = 0; day < 3; day++){
                    Double[] tmpD = new Double[24];
                    for(    int i =0; i<24; i++){
                        // 이 함수가 수행된 이후로는 각 station에 모든 재차인원 정보가 저장됨.
                        tmpD[i] = calc_Passenger_subFunction(day, i, routeName, stationId);
                    }
                    tmp.put(day, tmpD);
                }
                busInfo.getCongestionHashMap().get(routeName).passengerNum.put(stationId, tmp);
            }
        }
        return status;
    }

    private Double calc_Passenger_subFunction(int day,int nowHour, String routeName, String stationId){
        Double result=0.0;
        int busMinute=nowHour*60+1440;
        boolean status=true;

        //busMinute에 현재 정류장busS_R의 값이 0이면 끝!
        while(status){
            if(busInfo.getCongestionHashMap().containsKey(routeName)){
                //항상 사람이 탈 경우를 대비해 첫차시간을 고려함
                if(busMinute >  getFirstBusTime(routeName)){
                    //busMinute 시간에 승하차 인원이 없다면 0 반환
                    //예) busMinute=3시7분 ==> 시간대=3-4시 ==> 승차=0,하차=0이면 승객이 없으므로 False
                    if(!busInfo.getCongestionHashMap().get(routeName).dayPassengerNum_getOnOff.containsKey(stationId))
                        continue;
                    if(busInfo.getCongestionHashMap().get(routeName).IsPassengerExist(stationId, (int)(busMinute%1440)/60)){
                        Double getonn=getOnPassenger(day,(int)busMinute/60,busMinute%60,routeName, stationId);
                        Double getoff=getOffPassenger(day,(int)busMinute/60,busMinute%60,routeName, stationId);
                        result += (getonn - getoff);

                        // 다음 정류장 탐색
                        String before_stationId = this.getBeforeStation(routeName, stationId);
                        int timeInterval = getTimeInterval(routeName,stationId, before_stationId);
                        busMinute = busMinute-timeInterval;
                    }
                    else {
                        status = false;
                    }
                }
                else{
                    status = false;
                }
            }else{
                System.out.println("Error" + routeName+ "이 없음");
                status = false;
            }
        }
        return result;
    }


    //hdy
    private int getFirstBusTime(String routeName){
        //노선별로 첫차시간을 구해야 함!!!
        Integer time = busInfo.getRouteInfo(routeName).getTime().get(0);
        int val = time.intValue();

        return val;

    }

    /**
     * 노선에서 이전 정류장의 stationID를 반환
     * 시점의 이전 정류장은 종점을 말한다 ==> % 사용
     * @param routeName
     * @param stationID
     */
    private String getBeforeStation(String routeName, String stationId){
        String result="";
        RouteClass route = busInfo.getRouteInfo(routeName);
        int idx = route.stationList.indexOf(stationId);
        result=route.stationList.get((idx+route.stationList.size()-1)%route.stationList.size());
        while(!busInfo.getCongestionHashMap().get(routeName).isStationExist(stationId)){
            idx--;
            result=route.stationList.get((idx+route.stationList.size()-1)%route.stationList.size());
        }
        return result;
    }

    /**
     * stationID정류장과 before_stationID정류장 사이의 시간간격을 반환
     * @param routeName
     * @param stationID
     * @param before_stationID
     * @return
     */
    private int getTimeInterval(String routeName, String stationID,String before_stationID){
        int result=10;//여기도 임의로 테스트용으로 5분간격이라고 한거임!

        //희수 코드와 합쳐야 함

        return result;
    }


    /**
     * 원하는 시간대를 쪼개 임의로 hh시 mm분에 대한 승차인원 계산
     * 수식 검사 후 수정 여부 판단.
     * @param day : 평일, 토요일, 일요일 중 입력
     * @param hour : 0~23시
     * @param minute : 0~59분
     * @return hh시 mm분일 때 버스의 승차인원
     */
    private Double getOnPassenger(int day, int hour, int minute,String routeName, String stationId){
        if(!busInfo.getCongestionHashMap().get(routeName).dayPassengerNum_getOnOff.containsKey(stationId)){
            System.out.println("getOnPassenger : "+stationId +" "+ routeName+" not exist");
            return 0.0;
        }

        Double a=busInfo.getCongestionHashMap().get(routeName).dayPassengerNum_getOnOff.get(stationId).get(day)[0][(hour+1)%24];
        Double b=busInfo.getCongestionHashMap().get(routeName).dayPassengerNum_getOnOff.get(stationId).get(day)[0][(hour)%24];
        Double c=(a-b)*minute/60+b;
        return c;
    }

    /**
     * 원하는 시간대를 쪼개 임의로 hh시 mm분에 대한 하차인원 계산
     * 수식 검사 후 수정 여부 판단.
     * @param day : 평일, 토요일, 일요일 중 입력
     * @param hour : 0~23시
     * @param minute : 0~59분
     * @return hh시 mm분일 때 버스의 하차인원
     */
    private Double getOffPassenger(int day, int hour, int minute, String routeName, String stationId){
        if(!busInfo.getCongestionHashMap().get(routeName).dayPassengerNum_getOnOff.containsKey(stationId)){
            System.out.println("getOnPassenger : "+stationId +" "+ routeName+" not exist");
            return 0.0;
        }

        HashMap<Integer,Double[][]> hi=busInfo.getCongestionHashMap().get(routeName).dayPassengerNum_getOnOff.get(stationId);
        Double[][] hello=hi.get(day);
        Double a=hello[1][(hour+1)%24];
        Double b=hello[1][(hour)%24];
        Double c=(a-b)*minute/60+b;
        return c;
    }

    private boolean calc_congestion(){
        boolean status=true;
        for(String routeName : busInfo.getCongestionHashMap().keySet()){
            ArrayList<Integer> chair_handle = getBusType(routeName);

            for(String stationId : busInfo.getCongestionHashMap().get(routeName).stationList){
                if(!busInfo.getCongestionHashMap().get(routeName).passengerNum.containsKey(stationId))
                    continue;
                HashMap<Integer, int[]> tmpCong = new HashMap<Integer, int[]>();
                for(int day = 0; day < 3; day++){
                    int icong[] = new int[24];
                    for(int i=0; i<24; i++){
                        double num = busInfo.getCongestionHashMap().get(routeName).passengerNum.get(stationId).get(day)[i];

                        // 좌석에 앉을 수 있다 : -1
                        // 앉을 순 없지만 손잡이를 잡을 수 있다 : 0
                        // 둘다 불가능하다 : 1
                        if(num < chair_handle.get(0)){
                            icong[i] = -1;
                        }
                        else if(num < chair_handle.get(0) + chair_handle.get(1)){
                            icong[i] = 0;
                        }
                        else{
                            icong[i] = 1;
                        }
                    }
                    tmpCong.put(day, icong);
                }
                busInfo.getCongestionHashMap().get(routeName).congestion.put(stationId, tmpCong);
            }
        }
        return status;
    }

    /**
     *
     * @param routeName
     * @return 버스 종류
     * 광역버스(빨강) - 숫자4자리, 9로 시작                  45/0 ex)구글링+뇌피셜
     * 지선버스(초록) - 숫자4자리           좌석20 손잡이17  ex)5511번 버스 조사
     * 간선버스(파랑) - 숫자3자리           좌석24 손잡이24  ex)151번 버스 조사
     * 순환버스(노랑) - 숫자2자리                           20/17 ex)구글링+뇌피셜
     * 마을버스         한글로 시작+숫자    좌석20 손잡이17  ex)동작01번 버스 조사
     * N버스            N으로 시작+숫자                     24/24 ex)구글링+뇌피셜
     */
    private ArrayList<Integer> getBusType(String routeName){

        Pattern pD4_9=Pattern.compile("^9[0-9]{3}");
        Pattern pD4=Pattern.compile("^[0-9]{4}");
        Pattern pD3=Pattern.compile("^[0-9]{3}");
        Pattern pD2=Pattern.compile("^[0-9]{2}");
        Pattern pHD=Pattern.compile("^[가-힣]*[0-9]{2}");
        Pattern pN= Pattern.compile("^N");

        ArrayList<Integer> result=new ArrayList<>();
        if(pD4_9.matcher(routeName).find()){
            System.out.println("test== "+routeName+" 광역");
            result.add(45);
            result.add(0);

        }else if(pD4.matcher(routeName).find()){
            System.out.println("test== "+routeName+" 지선");
            result.add(20);
            result.add(17);

        }else if(pD3.matcher(routeName).find()){
            System.out.println("test== "+routeName+" 간선");
            result.add(24);
            result.add(24);

        }else if(pD2.matcher(routeName).find()){
            System.out.println("test== "+routeName+" 순환");
            result.add(20);
            result.add(17);

        }else if(pHD.matcher(routeName).find()){
            System.out.println("test== "+routeName+" 마을");
            result.add(20);
            result.add(17);

        }else if(pN.matcher(routeName).find()){
            System.out.println("test== "+routeName+" N심야");
            result.add(24);
            result.add(24);

        }else{
            System.out.println("test== "+routeName+" 알수없는 형식의 버스번호입니다.");
            result.add(-1);
            result.add(-1);
        }
        return result;
    }
}
