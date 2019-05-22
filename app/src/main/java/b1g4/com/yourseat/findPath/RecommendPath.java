package b1g4.com.yourseat.findPath;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import b1g4.com.yourseat.bus.BusInfoClass;
import b1g4.com.yourseat.bus.RouteClass;

public class RecommendPath {

    private int transferNum = 0;
    private ArrayList<String> stationListOnPath = new ArrayList<String>();
    private BusInfoClass businfo = BusInfoClass.getInstance();
    String minCongestionStop = "";
    String lastStop = "";

    public RecommendPath() {
    }

    //경로 사이의 모든 정류장 구하기
    public void getStationListOnPath(ArrayList<String> selectedPath) {

        this.transferNum = (selectedPath.size() / 5) - 1;

        String startSta = "";
        String endSta = "";
        String routeName = "";

        for (int k = 0; k <= transferNum; k++) {

            startSta = selectedPath.get(5 * k);
            endSta = selectedPath.get(5 * k + 3);
            routeName = selectedPath.get(5 * k + 2);

            RouteClass rta = businfo.getRouteInfo(routeName);

            for (int i = 0; i < rta.getStationList().size(); i++) {
                if (rta.getStationList().get(i).equals(startSta)) {
                    for (int j = i; (!rta.getStationList().get(j).equals(endSta)); j++) {
                        stationListOnPath.add(rta.getStationList().get(j));
                    }
                }
            }
        }

        lastStop = selectedPath.get(5 * transferNum + 3);

        stationListOnPath.add(endSta);

        // 확인용 코드, 삭제 예정
        for (int i = 0; i < stationListOnPath.size(); i++) {
            System.out.println(stationListOnPath.get(i));
        }
    }

    //가장 통계치가좋은 정류소를 고름
    public boolean calcTotalCongestionInPath() {

        ArrayList <Integer> nowDayTime = new ArrayList<Integer>();
        GetPathCongestion getpathcongestion = new GetPathCongestion();
        nowDayTime = getpathcongestion.getNowDayTime();

        int totalMinCongestion = 10;

        if(stationListOnPath.size() > 6) { //가는 길 속 정류장이 6개 미만이면 혼잡도에 따른 다른 경로를 추천

            for (int i = 0; i < stationListOnPath.size() - 3; i++) {
                Set tempRouteSet = businfo.getStationInfo(stationListOnPath.get(i)).getRouteListHashMap().keySet();
                Iterator iterator = tempRouteSet.iterator();
                int totalCongestionByStop = 0;
                while (iterator.hasNext()) {
                    String routeIdKey = (String) iterator.next();
                    totalCongestionByStop += businfo.getCongestinoClass(routeIdKey).getCongestion(nowDayTime.get(0), nowDayTime.get(1),
                            nowDayTime.get(2), stationListOnPath.get(i));
                }

                if (totalMinCongestion > totalCongestionByStop) {
                    minCongestionStop = stationListOnPath.get(i);
                    totalMinCongestion = totalCongestionByStop;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isNearestStop( double startXPos, double startYPos ){

        ArrayList<String> stationListBeforeStop = new ArrayList<String>();
        ArrayList<Double> distanceByDistance = new ArrayList<Double>();

        for(int i = 0 ; (!stationListOnPath.get(i).equals(minCongestionStop)) ; i++){
            stationListBeforeStop.add(stationListOnPath.get(i));
        }

        for(int i = 0 ; i < stationListBeforeStop.size() ; i++){
            double stationX = Double.parseDouble(businfo.getStationInfo(stationListBeforeStop.get(i)).getStationX());
            double stationY = Double.parseDouble(businfo.getStationInfo(stationListBeforeStop.get(i)).getStationY());
            distanceByDistance.add(Math.pow(startXPos - stationX , 2) + Math.pow(startYPos - stationY , 2));
        }

        double minDistance = distanceByDistance.get(0);

        for(double d: distanceByDistance){
            if(minDistance > d) {
                minDistance  = d;
            }
        }

        for(int i = 0 ; i < distanceByDistance.size() ; i++){
            if(minDistance == distanceByDistance.get(distanceByDistance.size())) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Double> getCoordinateOnMidPath(){
        ArrayList<Double> result = new ArrayList<Double>();
        result.add(Double.parseDouble(businfo.getStationInfo(minCongestionStop).stationX));
        result.add(Double.parseDouble(businfo.getStationInfo(minCongestionStop).stationY));
        result.add(Double.parseDouble(businfo.getStationInfo(lastStop).stationX));
        result.add(Double.parseDouble(businfo.getStationInfo(lastStop).stationX));
        return result;
    }

    public String getMinCongestionStop(){
        return minCongestionStop;
    }
}
