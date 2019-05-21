package b1g4.com.yourseat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class UserSelectedRoute extends AppCompatActivity {

    private TextView startPoint;
    private TextView endPoint;
    private TextView finalRoute;
    private String startAddress;
    private String endAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selected_view);

        //설희 화면에서 얻어온 출발지 주소와 도착지 주소 startPoint와 endPoint에 넣기!!!!!!!
        startPoint = (TextView)findViewById(R.id.startPoint);
        endPoint = (TextView)findViewById(R.id.endPoint);
        finalRoute = (TextView)findViewById(R.id.finalRoute);

        Intent intent = getIntent();
        String selectedRoute = (String) intent.getStringExtra("selectedRoute");
        startAddress = intent.getStringExtra("startAddress");
        endAddress = intent.getStringExtra("endAddress");

        //사용자가 선택한 경로찾기를 받아와서 finalRoute에 넣기.
        startPoint.setText(startAddress);
        endPoint.setText(endAddress);
        finalRoute.setText(selectedRoute.toString());

    }
}
