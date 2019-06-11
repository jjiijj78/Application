package com.example.tcptest;

import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.tcptest.*;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            String startX="126.9403932611921";
            String startY="37.51070581104394";
            String endX="126.9678703483273";
            String endY="37.506391727320924";
            SocketConnect socketConnect=new SocketConnect();
            ArrayList<ArrayList<String>> routelist=socketConnect.execute(startX,startY,endX, endY).get();
            ArrayList<ArrayList<String>> stationlist=socketConnect.returnMsgWithStop;
            Log.d("tcp", "tcp=============="+"결과나옴!!!!!!!!!!!!!!!!!!!!!!!!!");
            //String result=new SocketConnect().execute(startX,startY,endX, endY).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
