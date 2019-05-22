package findPath;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import api.ApigetArrInfoByRouteList;
import bus.BusInfoClass;

/**
 * GetPathCongestion
 */
public class GetPathCongestion {

    public GetPathCongestion(){

    }


        //{arrmsg1, nstnId1, reride_Num1} {arrmsg2, nstnId2, reride_Num2}
        //arrmsg : 첫번째 도착예정 버스의 도착정보메시지(2분4초후[0번째 전]) ,
        //nstnId : 첫번째 도착예정 버스의 다음정류소 ID, 
        //reride_Num : 첫번째 도착예정 버스의 버스내부 제공용 현재 재차 인원}
        //해석하자면 nstnId의 이전 정류장의 재차인원이 reride_Num이 된다.
    /**
     * 특정 노선에 특정 정류장에 다가올 버스의 재차인원을 구함
     * 뭐하나 정보가 없을 경우는 무조건 재차인원 21명으로 하게 함
     */
    public Double getPersonNum_RealTime(String stId, String busRouteName, String ord){
        Double result=0.0;


        if(!BusInfoClass.getInstance().isRouteExist(busRouteName)){
            System.out.println("test==getOnePathCongestion() 에서 "+busRouteName+" busInfo에 없음 gmdmdmdmdmmddmdmmd");
            return 21.0;
        }
        String busRouteId=BusInfoClass.getInstance().getRouteInfo(busRouteName).getRouteId();

        ApigetArrInfoByRouteList apigetArrInfoByRouteList=new ApigetArrInfoByRouteList();
        apigetArrInfoByRouteList.getArrInfoByRouteList(stId, busRouteId, ord);
        ArrayList<String> ArrbusInfo= apigetArrInfoByRouteList.getgetArrInfoByRouteLists();
        if(ArrbusInfo.size()==0){
            System.out.println("test==getPersonNum_RealTime() 에서"+stId+" "+busRouteName+" "+ord+" 도착정보조회 했을때 ArrbusInfo ==null");
            return 999999999999.0;
        }
        if(ArrbusInfo.get(0).equals("운행종료") ){
            System.out.println("test==getPersonNum_RealTime() 에서"+stId+" "+busRouteName+" "+ord+" 도착정보조회 했을때 운행종료");
            return 999999999999.0;
        }

        ArrayList<Integer> NowDayTime=getNowDayTime();//현재시간용

        if(stId.equals(ArrbusInfo.get(1))){
            //버스가 곧 도착할 예정이라면 재차인원은 그냥 사용하면 됨
            result=Double.valueOf(ArrbusInfo.get(2));
        }else{
            //버스가 전전전...정류장에 있다면 그 정류장 부터 stId까지 +승차-하차를 반복해서 더해나가면 됨
            //reride_Num1 + nstnId1(승차-하차) +nstnId1다음정류장(승차-하차) ......stId이전정류장(승차-하차)

            //노선의 정류장리스트
            ArrayList<String> stationList=BusInfoClass.getInstance().getRouteInfo(busRouteName).getStationList();
           
            int nstnId1_Ord_1=stationList.indexOf( ArrbusInfo.get(1));//버스가 도착할 예정인 정류장의 Ord-1
            int nowStationOrd_1=stationList.indexOf(stId);//출발정류장의 Ord-1 =======Ord는 1부터시작, indx는 0부터 시작

            if(nstnId1_Ord_1==-1){
                System.out.println("test==getPersonNum_RealTime() 에서 첫번째로 도착예정 버스가 향하고있는 정류장이 stationList에 없음");
                return 21.0;
            }
            else if(nstnId1_Ord_1==-1){
                System.out.println("test==getPersonNum_RealTime() 에서 사용자가 입력한 정류장이 stationList에 없음");
                return 21.0;
            }
            
            //최종적으로 재차인원을 구함
            result=Double.valueOf(ArrbusInfo.get(2));//reride_Num1를 먼저 더한다
            int minute=NowDayTime.get(2)+NowDayTime.get(1)*60;
            for(int i=nstnId1_Ord_1; i< nowStationOrd_1-1; i++){
                String stationId=stationList.get(i);
                Double personNum=0.0;
                
                if(BusInfoClass.getInstance().isCongestionExist(busRouteName)){
                    //노선에 해당하는 혼잡도정보가 있으면
                    personNum =BusInfoClass.getInstance().getCongestionClass(busRouteName).calcPassengerNum(NowDayTime.get(0), (int)minute/60,  minute%60,  stationId) ;

                }else{
                    //노선에 해당하는 혼잡도정보가 없으면
                    personNum=21.0;
                }
                
                minute=minute+getTimeInterval(busRouteName);
                minute=minute%1440;//24시간을 넘어서면 리셋
                result=result+personNum;
            }
        }
        return result;
    }


    /**
     * 환승이 포함된 경로의 혼잡도를 계산== 통계값만을 사용
     * @param path
     * @return
     */
    public int getTransferPathCongestion(ArrayList<String> path,ArrayList<Integer> now_day_time){
        int congestion=0;

        int transfer=(int)path.size()/5;//타는 버스 대수 = 환승횟수+1
        for(int i=0;i<transfer;i++){
            //출발 정류소 id, 정류소명, 노선명, 도착정류소 id, 도착정류소명
            String startStstionId=path.get(i*(transfer)+0);
            //String routeId=path.get(i*(transfer)+1);
            String routeName=path.get(i*(transfer)+2);
            String endStationId=path.get(i*(transfer)+3);
            congestion=getOnePathCongestion(startStstionId,  routeName, endStationId,now_day_time);
        }
        return congestion;
    }



    /**
     * 환승이 없는 경로의 혼잡도를 계산 ===>>>>>통계값만을 사용
     * 만약 뭐하나라도 정보가 없으면 경로 혼잡도가 200이라고 함, 정류
     * @param startStstionId
     * @param routeName
     * @param endStationId
     * @return
     */
    public int getOnePathCongestion(String startStstionId,String routeName,String endStationId,ArrayList<Integer> now_day_time){
        int result=0;
        if(!BusInfoClass.getInstance().getRouteHashMap().containsKey(routeName)){
            System.out.println("test==getOnePathCongestion() 에서 "+routeName+" busInfo에 없음");
            return 200;
        }
        for(String stationId : BusInfoClass.getInstance().getRouteInfo(routeName).stationList){ 
            //그다음정류장까지의 걸리는 시간
            int hour=now_day_time.get(1);
            int minute=now_day_time.get(2);
            minute=minute+getTimeInterval(routeName) + hour*60;

            double adderCongestion=0;
            if(BusInfoClass.getInstance().isCongestionExist(routeName)){
                if(BusInfoClass.getInstance().getCongestionClass(routeName).isStationExist(stationId)){
                    //노선, 정류장에 혼잡도 정보가있으면
                    adderCongestion=BusInfoClass.getInstance().getCongestionClass(routeName).getCongestion(now_day_time.get(0), (int)minute/60 , minute%60 , stationId);
                }else{
                    //노선은 있는제 해당 정류장에 혼잡도가 없을 경우
                    adderCongestion=200;
                }
            }else{
                //노선에 해당하는 혼잡도정보가 없으면
                adderCongestion=200;
            }


            result+=adderCongestion;
        }
        return result;
    }
    
    /**
     * stationID정류장과 before_stationID정류장 사이의 시간간격을 반환
     * 동일버스에서 정류장 사이 시간간격은 모두 동일하다고 가정
     * @param routeName
     * @param stationID
     * @param before_stationID
     * @return
     */
    private int getTimeInterval(String routeName){
        Pattern pD4_9=Pattern.compile("^9[0-9]{3}");
        Pattern pD4=Pattern.compile("^[0-9]{4}");
        Pattern pD3=Pattern.compile("^[0-9]{3}");
        Pattern pD2=Pattern.compile("^[0-9]{2}");
        Pattern pHD=Pattern.compile("^[가-힣]*[0-9]{2}");
        Pattern pN=Pattern.compile("^N");

        ArrayList<Integer> result=new ArrayList<>();
        if(pD4_9.matcher(routeName).find()){
            //System.out.println("test== "+routeName+" 광역");
            return 15;

        }else if(pD4.matcher(routeName).find()){
            //System.out.println("test== "+routeName+" 지선");
            return 4;

        }else if(pD3.matcher(routeName).find()){
            //System.out.println("test== "+routeName+" 간선");
            return 5;

        }else if(pD2.matcher(routeName).find()){
            //System.out.println("test== "+routeName+" 순환");
            return 5;

        }else if(pHD.matcher(routeName).find()){
            //System.out.println("test== "+routeName+" 마을");
            return 3;

        }else if(pN.matcher(routeName).find()){
            //System.out.println("test== "+routeName+" N심야");
            return 15;

        }else{
            System.out.println("test== "+routeName+" 알수없는 형식의 버스번호입니다.");
            return 10;
        }
    }

    /**
     * 현재날짜 현재요일을 반환
     * @return 0 = 평0/토1/일2
     *          1=시간
     *          2=분
    */
    public ArrayList<Integer> getNowDayTime(){
        ArrayList<Integer> result=new ArrayList<Integer>();
        Calendar cal=Calendar.getInstance();

        //요일
        int dayNum = cal.get(Calendar.DAY_OF_WEEK);
        if(dayNum==7)
            result.add(1);//토요일
        else if(dayNum==1)
            result.add(2); //일요일   
        else if(dayNum == 2 | dayNum == 3|dayNum == 4|dayNum == 5 | dayNum == 6)
            result.add(0);//평일
        
        //hour
        int hour=cal.get(Calendar.HOUR_OF_DAY);
        result.add(hour);

        //minute
        int minute=cal.get(Calendar.MINUTE);
        result.add(minute);

        return result;
    }

}