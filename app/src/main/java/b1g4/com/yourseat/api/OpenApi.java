package b1g4.com.yourseat.api;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
/**
 * OpenApi 클래스
 */
public class OpenApi {
    //constructor
    public OpenApi(){}

    /**
     * API에 url을 보내서 받은 xml 데이터를 NodeList로 변환하여 return.
     * NodeList를 원하는 방식으로 가공, 저장하여 사용하면 됨
     * @param urlstr API에 보낼 url
     * @return NodeList 데이터 형식으로 반환
     */
    NodeList getAPIresult(String urlstr){
        BufferedReader br=null;
        String result="";
        DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        try{
            //openAPI호출
            URL url=new URL(urlstr);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            //응답읽기
            br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String line;
            while((line=br.readLine())!=null){
                //System.out.println("\n====\n"+line+"\n====\n");
                result=result+line.trim(); //
            }
            //xml파싱
            DocumentBuilder builder;
            Document doc=null;
            InputSource is = new InputSource(new StringReader(result));
            builder=factory.newDocumentBuilder();
            doc=builder.parse(is);
            XPathFactory xPathFactory=XPathFactory.newInstance();
            XPath xPath=xPathFactory.newXPath();
    
            //현재위치와 상관없이 엘리먼트 이름이 <itemList>인 모든 엘리먼트를 읽는다.
            XPathExpression expr= xPath.compile("//itemList");
            NodeList nodeList=(NodeList)expr.evaluate(doc,XPathConstants.NODESET);
            
            return nodeList;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    
}