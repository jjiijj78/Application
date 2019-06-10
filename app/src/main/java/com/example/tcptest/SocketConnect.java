package com.example.tcptest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class SocketConnect extends AsyncTask<String, String, ArrayList<ArrayList<String>>> {

    public boolean status=false;
    public ArrayList<ArrayList<String>> returnMsg;
    public String testString="";
    private Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private String host = "111.118.51.9"; // IP
    private int port = 8000; // PORT번호
    int timeout = 3000;


    // 백그라운드 작업 시작전 호출
    // 준비작업 구현, ex) 네트워크 준비, 객체의 new..
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    // 본격적인 작업
    // 도중에 publishProgress()호출하면 onProgressUpdate() 실행됨 -> 사용자에게 진행을 알릴 때 사용
    @Override
    protected ArrayList<ArrayList<String>> doInBackground(String... String) {
        String startX = String[0];
        String startY=String[1];
        String endX=String[2];
        String endY=String[3];

        try{
            socket=new Socket();
            socket.setSoTimeout(timeout);//read 메서드가 connection_timeout 시간동안 응답을 기다린다.
            socket.setSoLinger(true, timeout);//서버와의 정상 종료를 위해서 connection_timeout 시간동안 close 호출 후 기다린다.
            socket.connect(new InetSocketAddress(host,port), timeout);

            //서버로 보내는 부분
            PrintWriter writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.println(startX+" "+startY+" "+endX+" "+endY);
            writer.flush();
            Log.d("tcp","서버로 보냄!");

            //클라이언트에서 받는 부분
            BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
            String clientMsg = reader.readLine();
            Log.d("tcp","받은 데이터 !"+clientMsg);
            translate(clientMsg);
            status=true;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket=null;
            }
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //return this.testString;
        return  this.returnMsg;
    }

    private void translate(String str){


    }

    @Override
    protected void onProgressUpdate(String... params) {

    }

    @Override
    protected void onPostExecute(ArrayList<ArrayList<String>> result) {
        super.onPostExecute(result);
    }
}
