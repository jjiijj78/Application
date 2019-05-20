package b1g4.com.yourseat;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.daum.mf.map.api.*;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        printHashKey();

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);

        try {
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
        }
    }

    /*
    HashKey을 가져와서 Log로 띄운다.
    다음 지도 API에 HashKey값을 저장해야 연동가능
    */
    public void printHashKey() {
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
