package b1g4.com.yourseat.api;

import java.util.ArrayList;

import org.w3c.dom.NodeList;

/**
 * parsing_getStationByPosList
 */

/**
 * //api호출 테스트코드
 parsing_getStationByPosList tmp=new parsing_getStationByPosList();
 String servicekey="aIcQiHW9KYc8CCUdYfRbOMwvGGXVSzqB2vAHYDK6W4tYYiUd1rkhIIPi3BlOLOGNfgYEfkQpprT05jQcu4xp3g%3D%3D";
 double tmX=126.95584930;
 double tmY=37.53843986;
 double radius=100;
 ArrayList<getStationByPosListClass> result=tmp.sendUrltoAPI(servicekey, tmX, tmY, radius);
 */
public class ParsingGetStationsByPostList {

    /**
     * 정류소정보조회 서비스 중 getStaionsByPosList(좌표기반근접 정류소 조회)기능을 이용하기 위한 URL 예시 (https://www.data.go.kr/dataset/15000303/openapi.do)
     * urlstr = http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?ServiceKey=인증키&tmX=126.95584930&tmY=37.53843986&radius=100
     * @param serviceKey 인증키
     * @param tmX 기준위치 X (WGS84)
     * @param tmY 기준위치 Y (WGS84)
     * @param radius 검색반경(0~1500m),단위는 m(미터)
     */
    public ArrayList<GetStationsByPosListClass> sendUrltoAPI(String serviceKey, double tmX, double tmY, double radius) {
        String urlstr="http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?ServiceKey="+serviceKey+"&tmX="+tmX+"&tmY="+tmY+"&radius="+radius;
        OpenApi openApi=new OpenApi();
        NodeList tmp=openApi.getAPIresult(urlstr);
        if(tmp==null){
            System.out.println("getStaionsByPosList API의 검색결과가 없습니다");
            return null;
        }
        else{
            ArrayList<GetStationsByPosListClass> result=xmlParsing(tmp);
            return result;
        }
    }

    /**
     * api에서 받은 xml을 원하는 형식으로 바꾸어 반환
     * @param "tmp" api에서 받은 xml을 NodeList로 변환한 것을 매개변수로 받음
     * @return xml파일을 파싱한 결과를 arrayList로 반환
     */
    ArrayList<GetStationsByPosListClass> xmlParsing(NodeList nodeList) {
        ArrayList<GetStationsByPosListClass> result=new ArrayList<>();
        for(int i=0;i<nodeList.getLength();i++){
            NodeList child=nodeList.item(i).getChildNodes();
            String arsId=child.item(0).getTextContent();
            String dist=child.item(1).getTextContent();
            String gpsX=child.item(2).getTextContent();
            String gpsY=child.item(3).getTextContent();
            String posX=child.item(4).getTextContent();
            String posY=child.item(5).getTextContent();
            String stationId=child.item(6).getTextContent();
            String stationNm=child.item(7).getTextContent();
            String stationTp=child.item(8).getTextContent();
            GetStationsByPosListClass tmp=new GetStationsByPosListClass(arsId, dist, gpsX, gpsY, posX, posY, stationId, stationNm, stationTp);
            result.add(tmp);
        }
        return result;
    }
}
