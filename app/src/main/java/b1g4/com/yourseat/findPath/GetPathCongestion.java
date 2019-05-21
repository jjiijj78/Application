package b1g4.com.yourseat.findPath;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * GetPathCongestion
 */
public class GetPathCongestion {

    public GetPathCongestion(){

    }

    /**
     * 환승이 포함된 경로의 혼잡도를 계산
     * @param path
     * @return
     */
    public int getTransferPathCongestion(ArrayList<String> path){
        int congestion=0;

        int transfer=(int)path.size()/4;//타는 버스 대수 = 환승횟수+1
        for(int i=0;i<transfer;i++){
            //출발 정류소 id, 노선 id, 노선명, 도착정류소 id
            String startStstionId=path.get(i*(transfer)+0);
            //String routeId=path.get(i*(transfer)+1);
            String routeName=path.get(i*(transfer)+2);
            String endStationId=path.get(i*(transfer)+3);
            congestion=getOnePathCongestion(startStstionId,  routeName, endStationId);
        }
        return congestion;
    }
    /**
     * 환승이 없는 경로의 혼잡도를 계산
     * @param startStstionId
     * @param routeName
     * @param endStationId
     * @return
     */
    public int getOnePathCongestion(String startStstionId,String routeName,String endStationId){
        int result=0;


        return result;
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