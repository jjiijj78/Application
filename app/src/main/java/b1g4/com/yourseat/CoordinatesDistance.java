package b1g4.com.yourseat;

import android.util.Log;

public class CoordinatesDistance {

    public boolean isTooShort(String x1Str, String y1Str, String x2Str, String y2Str) {

        double x1 = Double.parseDouble(x1Str);
        double y1 = Double.parseDouble(y1Str);
        double x2 = Double.parseDouble(x2Str);
        double y2 = Double.parseDouble(y2Str);
        if(calDistance(x1, y1, x2, y2) < 700) {
            return true;
        } else{
            return false;
        }
    }

    // 좌표간 거리 계산 (WGS84좌표계. 미터단위로 변환)
    public double calDistance(double lat1, double lon1, double lat2, double lon2){

        double theta, dist;
        theta = lon1 - lon2;
        dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);

        dist = dist * 60 * 1.1515;
        dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
        dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

        Log.d("Distance", Double.toString(dist));
        return dist;
    }

    // 주어진 도(degree) 값을 라디언으로 변환
    private double deg2rad(double deg){
        return (double)(deg * Math.PI / (double)180d);
    }

    // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
    private double rad2deg(double rad){
        return (double)(rad * (double)180d / Math.PI);
    }

}
