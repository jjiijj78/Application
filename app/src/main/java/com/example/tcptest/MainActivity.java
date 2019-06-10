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
            String startX="1";
            String startY="2";
            String endX="3";
            String endY="4";
            ArrayList<ArrayList<String>> ressssss=new SocketConnect().execute(startX,startY,endX, endY).get();
            Log.d("tcp", "tcp=============="+"결과나옴!!!!!!!!!!!!!!!!!!!!!!!!!");
            //String result=new SocketConnect().execute(startX,startY,endX, endY).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
