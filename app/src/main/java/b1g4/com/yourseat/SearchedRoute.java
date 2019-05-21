package b1g4.com.yourseat;

import java.util.ArrayList;

public class SearchedRoute {

    /*
    private ArrayList<ArrayList<String>> apiRouteLists = new ArrayList<ArrayList<String>>();
      받아오는 ArrayList형식
      {출발정류소id, 출발정류소명, 노선명, 도착정류소id, 도착정류소명, 소요시간}
    */
    int count;
    int transferNum;
    String startStation;
    String startRoute;
    String endStation;
    ArrayList<String> transferStationList;
    ArrayList<String> transferRouteList;
    String time;

    public int getTransferNum() {
        return transferNum;
    }

    public String getStartStation() {
        return startStation;
    }

    public String getStartRoute() {
        return startRoute;
    }

    public String getEndStation() {
        return endStation;
    }

    public ArrayList<String> getTransferStationList() {
        return transferStationList;
    }

    public ArrayList<String> getTransferRouteList() {
        return transferRouteList;
    }

    public String getTime() {
        return time;
    }


    //모듈의 SearchRoute 클래스의 결과값 그대로 인자로 받는다.
    public SearchedRoute(ArrayList<String> apiRouteLists){

            this.transferNum = (apiRouteLists.size() / 5) - 1;
            this.startStation = apiRouteLists.get(0);
            this.startRoute = apiRouteLists.get(1);
            this.endStation = apiRouteLists.get(apiRouteLists.size() - 1);
            this.time = apiRouteLists.get(apiRouteLists.size());
            for (int k = 0; k < transferNum; k++) {
                transferStationList.add(apiRouteLists.get(k* 5 + 1));
                transferRouteList.add(apiRouteLists.get(k * 5 + 2));
        }
    }




}
