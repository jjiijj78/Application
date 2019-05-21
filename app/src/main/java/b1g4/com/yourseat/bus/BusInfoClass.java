package b1g4.com.yourseat.bus;

import java.util.ArrayList;
import java.util.HashMap;

public class BusInfoClass{

    private static BusInfoClass busInfo = new BusInfoClass();
    // 전체 경로정보 <RouteName, RouteClass Instance> 형식으로 저장
    private HashMap<String, RouteClass> RouteList = new HashMap<String, RouteClass>();
    // 전체 정류장 정보 <StationID, StationClass Instance> 형식으로 저장
    private HashMap<String, StationClass> StationList = new HashMap<String, StationClass>();

    /**
     * Key : RouteName
     * Value : CongestionClass, 정류장별 혼잡도 정보를 가지고 있음
     * */
    private HashMap<String, CongestionClass> CongestionList = new HashMap<String, CongestionClass>();


    /**
     * 인스턴스를 한개만 생성하기 위함
     * @return BusInfoClass Instance
     */
    public static BusInfoClass getInstance(){
        if(busInfo == null){
            busInfo = new BusInfoClass();
        }
        return busInfo;
    }

    /**
     * Constructor
     * 여러 instance 생성을 막기 위해 private으로 지정
     */
    private BusInfoClass(){

    }

    /**
     * Route hashmap에 정보를 저장하는 함수
     * @param instance : RouteClass instance
     */
    public void setRoute(RouteClass instance){
        // this.RouteList.put(instance.routeId, instance);
        this.RouteList.put(instance.routeName, instance);
    }

    /**
     * Station hashmap에 정보를 저장
     * @param instance : StationClass instance
     */
    public void setStation(StationClass instance){
        this.StationList.put(instance.stationId, instance);
    }

    /**
     * 특정 Route정보를 반환
     * @param routeName : 노선 이름, 9자리 숫자로 이루어짐
     * @return RouteClass : routeName에 해당하는 RouteClass instance를 반환
     */
    public RouteClass getRouteInfo(String routeName){
        return this.RouteList.get(routeName);
    }

    /**
     * 특정 Station정보를 반환
     * @param stationId : 표준 정류장 ID,
     * @return StationClass : stationID에 해당하는 StationClass instance를 반환
     */
    public StationClass getStationInfo(String stationId){
        return this.StationList.get(stationId);
    }

    /**
     * StationID를 이용해 특정 정류장에 관한 class instance가 생성되어 있는지 검사한다.
     * @param stationId
     * @return boolean
     */
    public boolean isStationExist(String stationId){
        return this.StationList.containsKey(stationId);
    }

    /**
     * RouteID를 이용해 특정 노선에 관한 class instance가 생성되어 있는지 검사한다.
     * @param routeName
     * @return boolean
     */
    public boolean isRouteExist(String routeName){
        return this.RouteList.containsKey(routeName);
    }

    /**
     * Route에 관해 저장된 모든 정보를 반환
     * 왜 만들었는지 확인하고 필요없으면 삭제
     * @return HashMap<String, RouteClass> routeList
     */
    public HashMap<String, RouteClass> getRouteHashMap(){
        return RouteList;
    }

    /**
     * Station에 관하여 저장된 모든 정보를 반환
     * 왜 만들었는지 확인하고 필요없으면 삭제
     * @return HashMap<String, StationClass> stationList
     */
    public HashMap<String, StationClass> getStationHashMap(){
        return StationList;
    }


    /**
     * Congestion hashmap에 정보를 저장
     * @param instance : CongestinoClass instance
     */
    public void setCongestion(CongestionClass instance){
        this.CongestionList.put(instance.routeName, instance);
    }

    /**
     * 특정 Congestion정보를 반환
     * @param routeName : 노선이름
     * @return CongestionClass : 표준정류장ID_노선이름에 해당하는 CongestionClass instance를 반환
     */
    public CongestionClass getCongestinoClass(String routeName){
        return this.CongestionList.get(routeName);
    }

    /**
     * routeName를 이용해 특정 노선 혼잡도에 관한 class instance가 생성되어 있는지 검사한다.
     * @param routeName
     * @return boolean
     */
    public boolean isCongestionExist_station(String routeName, String stationId){
        if(this.CongestionList.containsKey(routeName)){
            if(this.CongestionList.get(routeName).isStationExist(stationId)){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    /**
     * routeName를 이용해 특정 노선 혼잡도에 관한 class instance가 생성되어 있는지 검사한다.
     * @param routeName
     * @return boolean
     */
    public boolean isCongestionExist(String routeName){
        return this.CongestionList.containsKey(routeName);
    }

    /**
     * Congestion에 관하여 저장된 모든 정보를 반환
     * 왜 만들었는지 확인하고 필요없으면 삭제
     * @return HashMap<String, CongestionClass> CongestionList
     */
    public HashMap<String, CongestionClass> getCongestionHashMap(){
        return CongestionList;
    }

    //hdy
    public void setRoute_Interval(String routeName, ArrayList<Integer> intervals){
        this.RouteList.get(routeName).setInterval(intervals);
    }

    //hdy
    public void setRoute_Time(String routeName, ArrayList<Integer> times){
        this.RouteList.get(routeName).setTime(times);
    }

}
