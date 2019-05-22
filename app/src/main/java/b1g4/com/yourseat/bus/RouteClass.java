package b1g4.com.yourseat.bus;

import java.util.ArrayList;
import java.util.List;

public class RouteClass{
    // public member variable
    public String routeId; // index : 0번 column
    public String routeName; // index : 1번 column
    public ArrayList<String> stationList = new ArrayList<String>();
    //hdy 0평/1토/2일 배차간격 - 단위:분
    public ArrayList<Integer> interval=new ArrayList<Integer>();
    //hdy 0첫차시간 1막차시간 - 단위:분  ex)3시 30분이면 3*60+30
    public ArrayList<Integer> time=new ArrayList<>();

    // constructor
    public RouteClass(List<String> infoList, String stationId){
        // routeInfo : id, Name
        this.routeId = infoList.get(0);
        this.routeName = infoList.get(1);
        this.stationList.add(stationId); // 반드시 순서대로 추가해야함
    }

    public void setStationInfo(String stationId){
        this.stationList.add(stationId);
    }

    public String getRouteId(){
        return this.routeId;
    }

    public String getRouteName(){
        return this.routeName;
    }

    public ArrayList<String> getStationList(){
        return this.stationList;
    }

    //hdy
    public ArrayList<Integer> getInterval(){
        return this.interval;
    }

    //hdy
    public ArrayList<Integer> getTime(){
        return this.time;
    }

    //hdy
    public void setInterval(ArrayList<Integer> intervals){
        this.interval=intervals;
    }

    //hdy
    public void setTime(ArrayList<Integer> times){
        this.time=times;
    }


    //한 노선에 대해 각각의 정류장 순서대로 들어갔는지 확인가능
    public void showStationInfo(){
        int count = this.stationList.size();
        for(int i=0;i<count;i++){
            System.out.print(this.stationList.get(i) + " ");
        }
        
    }


}