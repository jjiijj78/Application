package api;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * ApigetArrInfoByRouteList
 */
public class ApigetArrInfoByRouteList {

    //{arrmsg1, nstnId1, reride_Num1} {arrmsg2, nstnId2, reride_Num2}
    //arrmsg : 첫번째 도착예정 버스의 도착정보메시지(2분4초후[0번째 전]) ,
    //nstnId : 첫번째 도착예정 버스의 다음정류소 ID, 
    //reride_Num : 첫번째 도착예정 버스의 버스내부 제공용 현재 재차 인원}
    //해석하자면 nstnId의 이전 정류장의 재차인원이 reride_Num이 된다.
    //

    // 입력값이 stdId=100000028 (서울역사박물관.경교장.강북삼성병원 정류장),  busRouteId=100100585 (N37번버스), ord=37 (버스 노선에서 37번 정류장)
    // {2분4초후[0번째 전], 100000028,  6 } {  29분29초후[13번째 전], 121000006,  13 }
    // {0번 전 정류장에 도착할 예정, 그 정류장명은 서울역사박물관.경교장.강북삼성병원.  재차인원6명}
    // {13번 전 정류장에 도착할 예정, 그 정류장명은 뱅뱅사거리.  재차인원 13명}
    private ArrayList<String> getArrInfoByRouteLists=new ArrayList<>();

    
    public void getArrInfoByRouteList( String stId, String busRouteId, String ord){
            
        try {
            //String urlStr="http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute?"+"ServiceKey=aIcQiHW9KYc8CCUdYfRbOMwvGGXVSzqB2vAHYDK6W4tYYiUd1rkhIIPi3BlOLOGNfgYEfkQpprT05jQcu4xp3g%3D%3D"
            //+"&stId="+"112000001"+"&busRouteId="+"100100118"+"&ord="+"18";
            String urlStr="http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute?"+"ServiceKey=aIcQiHW9KYc8CCUdYfRbOMwvGGXVSzqB2vAHYDK6W4tYYiUd1rkhIIPi3BlOLOGNfgYEfkQpprT05jQcu4xp3g%3D%3D"
            +"&stId="+stId+"&busRouteId="+busRouteId+"&ord="+ord;
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
            Document apiDocument = dbuilder.parse(urlStr);
    
            apiDocument.getDocumentElement().normalize();
            NodeList nodeList = apiDocument.getElementsByTagName("itemList");//itemlist라는 태그 밑의 값을 가져올 것
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node=nodeList.item(i);
                if(node.getNodeType()==node.ELEMENT_NODE){
                    Element element=(Element) node;
                    getArrInfoByRouteLists.add(getTagValue("arrmsg1", element));
                    getArrInfoByRouteLists.add(getTagValue("nstnId1", element));
                    getArrInfoByRouteLists.add(getTagValue("reride_Num1", element));
                    getArrInfoByRouteLists.add(getTagValue("arrmsg2", element));
                    getArrInfoByRouteLists.add(getTagValue("nstnId2", element));
                    getArrInfoByRouteLists.add(getTagValue("reride_Num2", element));
                }
            } 
                
            //arraylist에 잘 들어갔는지 확인용 코드, 삭제예정
            System.out.print("test===getArrInfoByRouteList()   ");
            for(String element : getArrInfoByRouteLists){
                System.out.print(element+" ");
            }
            System.out.println("test===end");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
        
    public ArrayList<String> getgetArrInfoByRouteLists(){  
        return this.getArrInfoByRouteLists;
    }
        
    public String getTagValue(String tag, Element element) { //태그값 접근을 위한 매소드
        NodeList nodelist = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node value = (Node) nodelist.item(0);
        if(value == null) {
            return null; 
        }
        return value.getNodeValue();
    }
}