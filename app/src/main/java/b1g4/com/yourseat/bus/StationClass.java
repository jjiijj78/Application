package b1g4.com.yourseat.bus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.HashMap;

/**
 * 정류장에 관한 정보를 저장하는 Class 혼잡도에 대한 정보가 Station에 저장된다.(모든 혼잡도는 각 노선별 정류장에서의 승하차
 * 인원을 기준으로 판단.)
 */
public class StationClass {
    // public member variable
    public String stationId; // index : stnd_bsst_id, 4번 column
    public String stationArsNum; // index : bsst_ars_no, 5번 column
    public String stationName; // index : bus_sta_nm, 6번 column
    public String stationX; // index : bus_sta_x, 7번 column
    public String stationY; // index : bus_sta_y, 8번 column

    // 각 노선별 혼잡도 정보가 저장될 클래스
    // 혼잡도는 평일, 토요일, 일요일에 대해 0~24시를 1시간 간격으로 나눠 저장한다.
    public HashMap<String, HashMap<String, ArrayList<Double>>> routeList = new HashMap<String, HashMap<String, ArrayList<Double>>>();

    /**
     * Constructor
     * @param infoList : Station에 관한 기본적인 정보 한줄
     * @param routeId  : 현재 정류장을 지나는 버스 노선 ID
     */
    public StationClass(List<String> infoList, String routeId) {
        // infoList : id, arsNum, name, x, y 순서로 입력
        this.stationId = infoList.get(0);
        this.stationArsNum = infoList.get(1);
        this.stationName = infoList.get(2);
        this.stationX = infoList.get(3);
        this.stationY = infoList.get(4);
        HashMap<String, ArrayList<Double>> dump = new HashMap<String, ArrayList<Double>>();
        this.routeList.put(routeId, dump);
    }
    
    /**
     * @param routeId : 현재 정류장을 지나는 노선ID
     */
    public void setRouteInfo(String routeId) {
        HashMap<String, ArrayList<Double>> dump = new HashMap<String, ArrayList<Double>>();
        this.routeList.put(routeId, dump);
    }

    /**
     * 정류장과 노선에 대한 정보가 모두 정리된 뒤에 수행되어야 한다. 
     * 특정 노선에 대한 정류장의 혼잡도를 저장
     * @param routeId : 현재 정류장을 지나는 노선ID
     * @param congestion : 현재 정류장을 지나는 노선의 평일, 토요일, 일요일에 관한 모든 혼잡도 정보
     */
    public void setCongestionInfo(String routeId, HashMap<String, ArrayList<Double>> congestion) {
        this.routeList.put(routeId, congestion);
    }

    /**
     * 현재 정류장의 표준ID를 반환
     * @return stationId
     */
    public String getStationId(){
        return this.stationId;
    }

    /**
     * 현재 정류장의 ARS 번호를 반환
     * @return stationArsNum
     */
    public String getStationArsNum(){
        return this.stationArsNum;
    }

    /**
     * 현재 정류장의 이름을 반환
     * @return stationName
     */
    public String getStationName(){
        return this.stationName;
    }

    /**
     * 현재 정류장의 X좌표를 반환
     * 좌표는 WGS84를 따른다.
     * @return stationX
     */
    public String getStationX(){
        return this.stationX;
    }

    /**
     * 현재 정류장의 Y좌표를 반환
     * 좌표는 WGS84를 따른다. 
     * @return stationY
     */
    public String getStationY(){
        return this.stationY;
    }

    /**
     * 현재 정류장을 지나는 노선에 대한 정보를 반환한다. 
     * @return HashMap<String, HashMap<String, ArrayList<Double>>> routeList
     */
    public HashMap<String, HashMap<String, ArrayList<Double>>> getRouteListHashMap(){
        return routeList;
    }

    /**
     * For test
     * 현재 정류장을 지나는 모든 노선이 잘 저장되어 있는지 검사
     */
    public void showRouteInfo(){
        Collection<String> keys = routeList.keySet();
        for(String key : keys){
            System.out.println(key);
        }
    }
}