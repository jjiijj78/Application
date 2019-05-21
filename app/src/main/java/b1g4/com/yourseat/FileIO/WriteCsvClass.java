package b1g4.com.yourseat.FileIO;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import b1g4.com.yourseat.bus.RouteClass;
import b1g4.com.yourseat.bus.StationClass;


/**
 * 원본 데이터를 읽은 후 가공된 정보를 CSV형태로 저장하기 위해 만든 클래스
 */
public class WriteCsvClass {

    /**
     * 생성자
     */
    public WriteCsvClass(){

    }

    /**
     *
     * @param routelist
     * @throws IOException
     */
    public void writeRouteClass(HashMap<String, RouteClass> routelist) throws IOException {
        try {
            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/routecsv.csv";

            BufferedWriter fw;
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("EUC-KR")));

            Collection<RouteClass> values = routelist.values();
            for(RouteClass value : values){
                fw.write(value.getRouteId()+","+value.getRouteName()+",");
                for(int i = 0 ; i < value.getStationList().size() ; i++){
                    fw.write(value.getStationList().get(i) + ",");
                }
                fw.newLine();
            }
            fw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    //hdy
    /**
     *
     * @param routelist
     * @throws IOException
     */
    public void writeRouteClass_new(HashMap<String, RouteClass> routelist) throws IOException {
        try {
            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/routecsv.csv";

            BufferedWriter fw;
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("EUC-KR")));

            Collection<RouteClass> values = routelist.values();
            for(RouteClass value : values){
                fw.write(value.getRouteId()+","+value.getRouteName()+",");
                //배차간격
                fw.write(value.getInterval().get(0)+",");
                fw.write(value.getInterval().get(1)+",");
                fw.write(value.getInterval().get(2)+",");
                //첫차 막차
                fw.write(value.getTime().get(0)+",");
                fw.write(value.getTime().get(1)+",");
                //노선리스트
                for(int i = 0 ; i < value.getStationList().size() ; i++){
                    fw.write(value.getStationList().get(i) + ",");
                }
                fw.newLine();
            }
            fw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param stationlist
     * @throws IOException
     */
    public void writeStationClass(HashMap<String, StationClass> stationlist) throws IOException{
        try {
            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/stationcsv.csv";

            BufferedWriter fw;
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("EUC-KR")));

            Collection<StationClass> values = stationlist.values();
            for(StationClass value : values){
                fw.write(value.getStationId()+","+value.getStationArsNum() +","+value.getStationName() +","+value.getStationX() +","
                        + value.getStationY()+",");
                Collection<String> keys = value.getRouteListHashMap().keySet();
                for(String key : keys){
                    fw.write(key + ",");
                }
                fw.newLine();
            }
            fw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void writeCongestion(HashMap<String,HashMap<Integer,Double[]>> passenger ,HashMap<String,HashMap<Integer,int[]>> congestion)throws IOException{
        try {
            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/congestioncsv.csv";

            BufferedWriter fw;
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("EUC-KR")));

            Iterator<String> iter=passenger.keySet().iterator();
            while(iter.hasNext()){
                String stationID_routeName=(String)iter.next();

                //정류장
                fw.write(stationID_routeName.substring(0,stationID_routeName.indexOf("___")) + ",");
                //노선
                fw.write(stationID_routeName.substring(stationID_routeName.indexOf("___")+3) + ",");
                //재차인원
                HashMap<Integer,Double[]> tempP=passenger.get(stationID_routeName);
                for(int i=0;i<3;i++){
                    for(int j=0;j<24;j++){
                        fw.write(tempP.get(i)[j] + ",");
                    }
                }
                //혼잡도
                HashMap<Integer,int[]> tempC= congestion.get(stationID_routeName);
                for(int i=0;i<3;i++){
                    for(int j=0;j<24;j++){
                        fw.write(tempC.get(i)[j] + ",");
                    }
                }
                fw.newLine();
            }
            fw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}