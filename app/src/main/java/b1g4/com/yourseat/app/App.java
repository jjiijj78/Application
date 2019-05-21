package b1g4.com.yourseat.app;

import java.util.ArrayList;

import b1g4.com.yourseat.bus.BusInfoClass;
import b1g4.com.yourseat.findPath.RecommendPath;

public class App {
    public static void main(String[] args) throws Exception {

        Runtime runtime = Runtime.getRuntime();
        int mb = 1024*1024;

        BusInfoClass busInfo = BusInfoClass.getInstance();
        String currentPath = System.getProperty("user.dir");

        //OSSWProj/20190124기준_서울시_버스노선정보.xls
        String fileDir1 = currentPath + "\\20190124기준_서울시_버스노선정보_new.xls";
        ParseFileClass parse1 = new ParseFileClass(fileDir1, true, true);

        //배차간격
        //OSSWProj/20190124기준_서울시_노선현황_첫차막차배차.xls
        //String fileDir1_ = currentPath + "\\20190124기준_서울시_노선현황_첫차막차배차_new.xls";
        //ParseFileClass parse1_ = new ParseFileClass(fileDir1_, 0);

        //perMonth와 perYear파일 파싱, 저장
        //String fileDir2 = currentPath + "\\data\\bus";
        //ParseFileClass parse2 = new ParseFileClass(fileDir2, false, true);

        ArrayList<String> temp = new ArrayList<String>();
        temp.add("100000367");
        temp.add("100100008");
        temp.add("103");
        temp.add("100000387");
        temp.add("100000387");
        temp.add("123000010");
        temp.add("741");
        temp.add("100000368");
        temp.add("21");

        RecommendPath recommendPath = new RecommendPath();
        recommendPath.getStationListOnPath(temp);

        System.out.println("Used Memory : " + (runtime.totalMemory() - runtime.freeMemory())/mb + "MB");
    }
}