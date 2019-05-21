package b1g4.com.yourseat.findPath;

import java.util.ArrayList;

import b1g4.com.yourseat.api.GetStationsByPosListClass;
import b1g4.com.yourseat.api.ParsingGetStationsByPosList;
import b1g4.com.yourseat.api.SearchRoute;
import b1g4.com.yourseat.bus.BusInfoClass;


/**
 * SearchPath
 */
public class SearchPath {
    BusInfoClass busInfoClass= BusInfoClass.getInstance();

    //저장할 공간
    ArrayList<ArrayList<String>> resultPathList=new ArrayList<>();
    //최단시간 경로
    ArrayList<String> minPath=new ArrayList<>();
    double minTime; //최단시간
    /**
     * constructor
     */
    public SearchPath(){

    }

    /**
     * @param startX 출발점 X (wgs84)
     * @param startY 출발점 Y (wgs84)
     * @param endX 도착점 X (wgs84)
     * @param endY 도착점 Y (wgs84)
     * @return 혼잡도를 계산해서 낮은 우선순위 별로 경로를 반환해 준다.
     *
     */
    public ArrayList<ArrayList<String>> getPathsFromStations(Double startX,Double startY, Double endX, Double endY){

        //출발지와 도착지 주변에 있는 정류장 목록을 반환 api 호출
        ParsingGetStationsByPosList tmp = new ParsingGetStationsByPosList();
        String servicekey = "aIcQiHW9KYc8CCUdYfRbOMwvGGXVSzqB2vAHYDK6W4tYYiUd1rkhIIPi3BlOLOGNfgYEfkQpprT05jQcu4xp3g%3D%3D";
        double radius=700;
        ArrayList<GetStationsByPosListClass> start = tmp.sendUrltoAPI(servicekey,startX,startY,radius);
        ArrayList<GetStationsByPosListClass> end = tmp.sendUrltoAPI(servicekey, endX,endY,radius);

        //현재요일,시각
        ArrayList<Integer> nowDayTime=new GetPathCongestion().getNowDayTime();

        //길찾기 api 호출
        //출발지는 가장 가까운 정류장 3개, 도착지는 가장 가까운 정류장 2개를 검사
        SearchRoute searchRoute=new SearchRoute();
        int ss=0;
        int ee=0;
        for(GetStationsByPosListClass s : start) {
            if(ss==3)
                break;
            for(GetStationsByPosListClass e : end) {
                if(ee==2)
                    break;
                searchRoute.searchRouteByAPI(s.getgpsX(), s.getgpsY(), e.getgpsX(), e.getgpsY());
                ArrayList<ArrayList<String>> addedPaths=searchRoute.getapiRouteLists();
                //경로 목록 추가 저장
                addPaths_to_resultPathList(addedPaths,nowDayTime);
                ee++;
            }
            ss++;
        }

        //혼잡도 값으로 정렬
        //ArrayList<ArrayList<String>> sendPathList=sortCongetion_GetTopPathList();
        //return sendPathList;

        if(isExistpathList(this.minPath)){
            this.resultPathList.add(this.minPath);
        }
        return this.resultPathList;
    }

    /**
     * 경로 목록들을 파싱한다.
     * 경로 1개 당
     * -혼잡도를 계산한다.
     * -앞뒤 정류장을 고려한 새로운 경로도 혼잡도를 계산한다.
     * -중복이 있는지 검사해서 pathList 리스트에 추가를 한다.
     * ----------환승횟수랑 시간은.........?
     * @param paths
     */
    private void addPaths_to_resultPathList(ArrayList<ArrayList<String>> paths,ArrayList<Integer> nowDayTime){
        GetPathCongestion getPathC=new GetPathCongestion();
        int mincongestionvalue=99999999;
        ArrayList<String> minCongPath=new ArrayList<>();

        int i=0;
        for(ArrayList<String> onePath: paths){
            if(i==3) //api를 호출한 경로에서 best 3가지만 볼거임
                break;

            double time=Double.parseDouble(onePath.get(onePath.size()-1));
            //최단시간 경로 저장
            if(this.minTime>time){
                this.minTime=time;
                this.minPath=onePath;
            }

            //api를 이용해서
            ArrayList<String> FirstRoute_stationList=busInfoClass.getRouteInfo(onePath.get(1)).getStationList();//첫번째 노선의 정류장리스트-가독성위해 변수 설정
            String nowStationId=onePath.get(1);//출발정류장
            int nowStationOrd_1=FirstRoute_stationList.indexOf(nowStationId);//출발정류장의 Ord-1 =======Ord는 1부터시작, indx는 0부터 시작
            Double testPersonNum=getPathC.getPersonNum_RealTime(onePath.get(0), onePath.get(1), String.valueOf(nowStationOrd_1+1));
            int testCong=getPathC.getTransferPathCongestion(onePath, nowDayTime);
            if(testPersonNum<20.0){
                //앉을 수 있는 사람수가 나왔으면 바로 그 길로 가도록!
                minCongPath=onePath;
                mincongestionvalue=testCong;
                break;
            }else{
                //앉을 수 없는 사람수가 나왔다면 기존에 있는 경로보다 혼잡도가 작을경우에만 바꾼다
                if(mincongestionvalue>testCong){
                    minCongPath=onePath;
                    mincongestionvalue=testCong;
                }
            }
        }
        // resultPathList에 제일 좋은 경로 추가
        if(!isExistpathList(minCongPath)){
            this.resultPathList.add(minCongPath);
        }
    }

    /**
     * 기존에 있는 경로인지 검사
     * @param path  resultPathList에 추가할 경로
     * @return  resultPathList에 이미 있으면 true
     *           resultPathList에 없으면 false
     */
    private Boolean isExistpathList( ArrayList<String> path){
        //this.pathList.contains(path); //==>되나?
        for(int i=0;i< this.resultPathList.size();i++){
            if( this.resultPathList.equals(path))
                return true;
        }
        return false;
    }

    /**
     * 경로들을 혼잡도 기준으로 정렬해서 최대 상위10개의 경로를 반환

     private ArrayList<ArrayList<String>> sortCongetion_GetTopPathList(){
     //경로별로 혼잡도를 따로 빼서 정렬을 하기 위한 unsorted에 저장
     Map<Integer,Double> unsorted=new HashMap<>();
     int i=0;
     for( ArrayList<String> p : this.resultPathList){
     double congestion=Double.parseDouble(p.get(p.size()-1));
     unsorted.put(i,congestion);
     i++;
     }
     //혼잡도 오름차순으로 정렬
     Map<Integer,Double> sorted = new LinkedHashMap<>();
     unsorted.entrySet().stream()
     .sorted(Map.Entry.<Integer,Double>comparingByValue(Comparator.naturalOrder()))
     .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));

     //상위 10개만 목록에 넣음
     ArrayList<ArrayList<String>> sendPathList=new ArrayList<ArrayList<String>>();
     int j=0;
     for(Integer idx:sorted.keySet()){
     if(j>10) break;
     sendPathList.add(this.resultPathList.get(idx));
     j++;
     }
     return sendPathList;
     }*/




}