package com.example.tcptest;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class TcpSocket extends Thread {

    public ArrayList<ArrayList<String>> returnMsg;
    public String testString="";
    private Socket socket;
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
    private String host = "111.118.51.9"; // IP
    private int port = 8000; // PORT번호
    int timeout = 3000;

    public void run(){

    }


}
