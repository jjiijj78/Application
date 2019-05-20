package b1g4.com.yourseat;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import net.daum.mf.map.api.*;

public class MainActivity extends AppCompatActivity {

    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText addrSearch;        // 검색어를 입력할 Input 창
    private AddressListViewAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> addressList;
    private EditText startEditText;
    private EditText endEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // printHashKey(); // 해시키 확인

        MapView mapView = new MapView(this);
        //ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        //mapViewContainer.addView(mapView);

        startEditText = findViewById(R.id.startLocation);
        endEditText = findViewById(R.id.endLocation);

        BtnOnClickListener onClickListener = new BtnOnClickListener() ;
        Button startSearchBtn = (Button)findViewById(R.id.startSearchBtn);
        Button endSearchBtn = (Button)findViewById(R.id.endSearchBtn);
        startSearchBtn.setOnClickListener(onClickListener);
        endSearchBtn.setOnClickListener(onClickListener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        if(resultCode == Code.resultCode) {
            String selectedResult = resultIntent.getStringExtra("selectedAddr");
            if(requestCode == Code.requestCodeStart) {
                startEditText.setText(selectedResult);
            } else if(requestCode == Code.requestCodeEnd) {
                endEditText.setText(selectedResult);
            }

        }
    }

    // 인풋으로 주소 키워드를 받아와서 REST API를 호출한 후 결과 Address 데이터를 반환해준다.
    public PointFromAddressData getAddrData(String input) {
        try {
            String result = new GetPointFromAddress().execute(input).get();

            Log.d("getPointResult", result);
            Gson gsonResult = new Gson();
            PointFromAddressData pointFromAddressData = gsonResult.fromJson(result, PointFromAddressData.class);
//            for (int i=0; i<pointFromAddressData.documents.size(); i++) {
//                list.add(pointFromAddressData.documents.get(i).address_name);
//                String x = pointFromAddressData.documents.get(i).x;
//                String y = pointFromAddressData.documents.get(i).y;
//                Log.d("XY", "x: " + x + "y: "+ y);
//            }
            return pointFromAddressData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    class BtnOnClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            String location = null;
            PointFromAddressData addrData = null;
            ArrayList<String> addressList = new ArrayList<>();
            switch(v.getId()) {
                case R.id.startSearchBtn:
                    location = startEditText.getText().toString();
                    addrData = getAddrData(location);
                    break;
                case R.id.endSearchBtn:
                    location = endEditText.getText().toString();
                    addrData = getAddrData(location);
                    break;
            }
            Log.d("ResultCheck", addrData.documents.get(0).x);
            for(int i=0; i<addrData.documents.size(); i++) {
                addressList.add(addrData.documents.get(i).address_name);
            }
            Intent intent = new Intent(getApplicationContext(), AddrSelectActivity.class);
            intent.putExtra("addrList", addressList);
            intent.putExtra("input", location);
            switch(v.getId()) {
                case R.id.startSearchBtn:
                    startActivityForResult(intent, Code.requestCodeStart);//액티비티 띄우기
                    break;
                case R.id.endSearchBtn:
                    startActivityForResult(intent, Code.requestCodeEnd);//액티비티 띄우기
                    break;
            }
        }
    }

}
