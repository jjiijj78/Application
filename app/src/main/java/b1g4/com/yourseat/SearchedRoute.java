package b1g4.com.yourseat;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("serial")
public class SearchedRoute implements Serializable {

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
    //endStation이후에 탈 Route가 없으니까 endRoute는 없다.
    ArrayList<String> transferStationList = new ArrayList<>();
    ArrayList<String> transferRouteList = new ArrayList<>();
    String time;

    /*
    //heesu: expandedlistView에 사용할 childStations 배열.
    ArrayList<String> childStations;

    SearchedRoute(){
        childStations = new ArrayList<String>();
    }*/

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
            this.startStation = apiRouteLists.get(1);
            this.startRoute = apiRouteLists.get(2);
            this.endStation = apiRouteLists.get(apiRouteLists.size() - 2);
            this.time = apiRouteLists.get(apiRouteLists.size()-1);
            if(transferNum == 0){
                transferStationList = null;
                transferRouteList = null;
            }
            else {
                Log.d("TransferCheck", Integer.toString(apiRouteLists.size()));
                for (int k = 0; k < transferNum; k++) {
                    transferStationList.add(apiRouteLists.get(5 * k + 4));
                    transferRouteList.add(apiRouteLists.get(5 * k + 7));
                }
            }
    }




}
