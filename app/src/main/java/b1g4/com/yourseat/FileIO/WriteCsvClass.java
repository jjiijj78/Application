package fileIO;

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

import bus.BusInfoClass;
import bus.CongestionClass;
import bus.RouteClass;
import bus.StationClass;

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
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("UTF-8")));

            Collection<RouteClass> values = routelist.values();
            for(RouteClass value : values){

                fw.write("\"" +value.getRouteId()+"\""+","+"\""+value.getRouteName()+"\""+",");
                for(int i = 0 ; i < value.getStationList().size() ; i++){
                    fw.write("\""+value.getStationList().get(i)+"\"" + ",");

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
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("UTF-8")));

            Collection<RouteClass> values = routelist.values();
            for(RouteClass value : values){
                fw.write("\""+value.getRouteId()+"\""+","+"\""+value.getRouteName()+"\""+",");

                //배차간격
                fw.write("\""+value.getInterval().get(0)+"\""+",");
                fw.write("\""+value.getInterval().get(1)+"\""+",");
                fw.write("\""+value.getInterval().get(2)+"\""+",");
                //첫차 막차
                fw.write("\""+value.getTime().get(0)+"\""+",");
                fw.write("\""+value.getTime().get(1)+"\""+",");
                //노선리스트
                for(int i = 0 ; i < value.getStationList().size() ; i++){
                    fw.write("\""+value.getStationList().get(i) + "\""+",");
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
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("UTF-8")));
            
            Collection<StationClass> values = stationlist.values();
            for(StationClass value : values){
                String stationId = "\"" + value.getStationId() +"\"";
                String stationArsNum = "\"" + value.getStationArsNum() + "\"";
                String stationName = "\"" + value.getStationName() + "\"";
                String stationX = "\"" + value.getStationX() + "\"";
                String stationY = "\"" + value.getStationY() + "\"";

                fw.write(stationId + "," + stationArsNum + "," + stationName + "," + stationX + "," + stationY + ",");              
                Collection<String> keys = value.getRouteListHashMap().keySet();
                for(String key : keys){
                    key = "\"" + key + "\"";
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

/*
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
*/

    public void writeCongestion()throws IOException{
        try {
            String currentPath = System.getProperty("user.dir");
            String csvfilename = currentPath + "/congestioncsv.csv";
                        
            BufferedWriter fw;
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvfilename), Charset.forName("UTF-8")));

            BusInfoClass  busInfoClass=BusInfoClass.getInstance();

            Iterator<String> iter= busInfoClass.getRouteHashMap().keySet().iterator();
            while(iter.hasNext()){
                String routeName=(String)iter.next();
                System.out.println("test==writeCongestion() routeName="+routeName);
               
                CongestionClass congestionClass=busInfoClass.getCongestionClass(routeName);
                if(congestionClass != null){
                    //정류장 개수만큼
                    for(String station : congestionClass.stationList){

                        HashMap<Integer,Double[]> tempP=congestionClass.passengerNum.get(station);//재차인원
                        HashMap<Integer,double[]> tempC= congestionClass.congestion.get(station);//혼잡도
                        if(tempP!=null && tempC !=null){
                            //노선명
                            fw.write("\""+routeName+"\""+",");
                            //정류장
                            fw.write("\""+station+"\""+",");
                            //재차인원
                            for(int i=0;i<3;i++){
                                for(int j=0;j<24;j++){
                                    //System.out.println("test=="+routeName+" "+station+"  "+tempP.get(i)[j]);
                                    fw.write("\""+tempP.get(i)[j] +"\""+ ",");
                                }
                            }
                            //혼잡도
                            for(int i=0;i<3;i++){
                                for(int j=0;j<24;j++){
                                    //System.out.println("test=="+routeName+" "+station+"  "+tempC.get(i)[j]);
                                    fw.write("\""+tempC.get(i)[j] +"\""+ ",");
                                }
                            }
                            fw.newLine();
                        }else{
                            System.out.println("test==해당 노선의 해당 정류장은 perYear에 값이 없는 듯 "+routeName+" "+station);
                        }
                    }
                }else{
                    System.out.println("test==해당 노선은 "+routeName+" congestionClass가 없음");
                }
            }
            fw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}