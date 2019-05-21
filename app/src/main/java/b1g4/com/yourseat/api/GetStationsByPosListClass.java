package b1g4.com.yourseat.api;

import java.util.ArrayList;

/**
 * getStaionsByPosListClass 정류소정보조회 서비스 중 getStaionsByPosList(좌표기반근접 정류소 조회)기능을
 * 이용하기 위한 URL 예시 (https://www.data.go.kr/dataset/15000303/openapi.do) urlstr =
 * http://ws.bus.go.kr/api/rest/stationinfo/getStationByPos?ServiceKey=인증키&tmX=126.95584930&tmY=37.53843986&radius=100
 */
public class GetStationsByPosListClass{
    private String arsId=new String();  //정류소고유번호==>정류소고유번호
    private String dist=new String();   //거리==>거리
    private String gpsX=new String();   //정류소좌표X==>정류소 좌표X(WGS84)
    private String gpsY=new String();   //정류소좌표Y==>정류소 좌표Y(WGS84)
    private String posX=new String();   //정류소좌표X==>정류소 좌표X(GRS80)
    private String posY=new String();   //정류소좌표Y==>정류소 좌표Y(GRS80)
    private String stationId=new String();  //정류소ID==>정류소ID
    private String stationNm=new String();  //정류소명==>정류소명
    /*
    0:공용
    1:일반형 시내/농어촌버스
    2:좌석형 시내/농어촌버스
    3:직행좌석형 시내/농어촌버스
    4:일반형 시외버스
    5:좌석형 시외버스
    6:고속형 시외버스
    7:마을버스
    */
    private String stationTp=new String();  //정류소타입

    //constructor
    public GetStationsByPosListClass(String arsId, String dist, String gpsX,String gpsY,String posX, String posY,
                                    String stationId, String stationNm, String stationTp) {
        this.arsId=arsId;
        this.dist=dist;
        this.gpsX=gpsX;
        this.gpsY=gpsY;
        this.posX=posX;
        this.posY=posY;
        this.stationId=stationId;
        this.stationNm=stationNm;
        this.stationTp=stationTp;
    }
    public String getgpsX(){
        return this.gpsX;
    }
    public String getgpsY(){
        return this.gpsY;
    }
}
