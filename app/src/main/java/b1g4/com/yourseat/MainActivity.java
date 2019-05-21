package b1g4.com.yourseat;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import net.daum.mf.map.api.*;

public class MainActivity extends AppCompatActivity {
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);

        final ArrayList<ArrayList<String>> searchedRouteArrayList = new ArrayList<ArrayList<String>>();
        /*ArrayList<ArrayList<String>> apiRouteLists = null;
        for(int i=0; i< apiRouteLists.size(); i++) {
            ArrayList<String> tmp = new ArrayList<String>(apiRouteLists.get(i));
            searchedRouteArrayList.add(tmp);
        }*/

        //테스트용 인풋 생성
        final ArrayList<String> sample = new ArrayList<>();
        sample.add("100000384");
        sample.add("동작01");
        sample.add("721");
        sample.add("100000165");
        sample.add("중랑01");
        sample.add("23");
        searchedRouteArrayList.add(sample);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getApplicationContext(), GetSearchedRoute.class);

                //GetSearchedRoute로 intent로 ArrayList<String>형태의 한 경로를 GetSearchedRoute로 putExtra
                intent.putExtra("sRouteList", searchedRouteArrayList);
                startActivity(intent);
            }
        });



        /*printHashKey();

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
*/

        //GSON???설정다시해야함 잠시 주석처리!!!
        /*try {
            String result = new GetPointFromAddress().execute("노량진").get();

            Log.d("getPointResult", result);
            Gson gsonResult = new Gson();
            PointFromAddressData pointFromAddressData = gsonResult.fromJson(result, PointFromAddressData.class);
            for (int i=0; i<pointFromAddressData.documents.size(); i++) {
                String x = pointFromAddressData.documents.get(i).x;
                String y = pointFromAddressData.documents.get(i).y;
                Log.d("XY", "x: " + x + "y: "+ y);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    /*
    HashKey을 가져와서 Log로 띄운다.
    다음 지도 API에 HashKey값을 저장해야 연동가능
    */
    //또 잠시 주석처리
    /*public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("b1g4.com.yourseat", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
