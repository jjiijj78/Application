package b1g4.com.yourseat.api;


import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SearchRoute {

    //나온 경로들을 {출발 정류소 id, 노선 id, 노선명, 도착정류소 id, 소요시간} 순으로 2차원 삽입
    //ex) {100000384, 광화문, 721, 100000165, 숭인동, 23} = {10000384, 광화문, 100100112노선, 721번버스, 100000165, 숭인동, 23분소요}
    //ex) {100000367, 정류소이름, 103, 100000387, 정류소이름, 100000387 ,정류소이름, 741 , 100000368, 정류소이름, 21} = 103번버스~환승~741번버스, 21분 소요
    private ArrayList<ArrayList<String>> apiRouteLists = new ArrayList<ArrayList<String>>();

    // 원래 코드
    // public void searchRouteByAPI( String startXPos, String startYPos, String endXPos, String endYPos  ){

    public void searchRouteByAPI(){

        try {

            //원래 코드, 밑에는 테스트용 코드로 임의의 x와 y 넣어둠
            //String urlStr =  "http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBus" + "?ServiceKey=hRcdNI0WTwvb4waUa3XtdJdOYRyeZzqFg4m7aYwpkjvp13%2BMaSjxMizRLWp0uJrrAX6BN9BWarqYoGR0Bu8l2A%3D%3D"
            //+ "&startX=" + startXPos + "&startY=" + startYPos + "&endX=" + endXPos + "&endY=" + endYPos;


            String urlStr =  "http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBus"
                    + "?ServiceKey=hRcdNI0WTwvb4waUa3XtdJdOYRyeZzqFg4m7aYwpkjvp13%2BMaSjxMizRLWp0uJrrAX6BN9BWarqYoGR0Bu8l2A%3D%3D"
                    + "&startX=" + "127.01966520359342" + "&startY=" + "37.574197493878486" + "&endX=" + "126.9675787181807" + "&endY=" + "37.567058032371364";

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dbuilder = dbFactory.newDocumentBuilder();
            Document apiDocument = dbuilder.parse(urlStr);

            apiDocument.getDocumentElement().normalize();
            NodeList nodeList = apiDocument.getElementsByTagName("itemList");//itemlist라는 태그 밑의 값을 가져올 것

            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element element = (Element) node;
                    NodeList subNodeList = element.getElementsByTagName("pathList");
                    ArrayList<String> routeList = new ArrayList<String>();
                    for(int j = 0; j < subNodeList.getLength(); j++){
                        Node subNode = subNodeList.item(j);
                        if(subNode.getNodeType() == subNode.ELEMENT_NODE){
                            Element subElement = (Element) subNode;
                            routeList.add(getTagValue("fid", subElement));
                            routeList.add(getTagValue("fname", subElement));
                            routeList.add(getTagValue("routeNm", subElement));
                            routeList.add(getTagValue("tid", subElement));
                            routeList.add(getTagValue("tname", subElement));
                        }
                    }
                    routeList.add(getTagValue("time", element));
                    apiRouteLists.add(routeList);
                }
            }


            //arraylist에 잘 들어갔는지 확인용 코드, 삭제예정
            for (List<String> arraylist : apiRouteLists){
                for (String element : arraylist) {
                    System.out.print(element+" ");
                }
                System.out.println();
                System.out.println("row end");
            }
        }

        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<ArrayList<String>> getapiRouteLists(){
        return this.apiRouteLists;
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
