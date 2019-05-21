package b1g4.com.yourseat;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


/*
 * AsyncTask<doInBackground()의 변수 종류, onProgressUpdate()에서 사용할 변수 종류, onPostExecute()에서 사용할 변수종류>
 * doInBackground()의 변수 종류: 해당 AsyncTask execute할 때 전달할 값 종류
 * onProgressUpdate()에서 사용할 변수 종류: 진행상황을 알릴 때 전달할 값 종류
 * onPostExecute()에서 사용할 변수종류: AsyncTask가 끝난 뒤 결과값의 종류
 * */
public class GetNewRouteList extends AsyncTask<Code, Code, Code> {


    // 백그라운드 작업 시작전 호출
    // 준비작업 구현, ex) 네트워크 준비, 객체의 new..
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    // 본격적인 작업
    // 도중에 publishProgress()호출하면 onProgressUpdate() 실행됨 -> 사용자에게 진행을 알릴 때 사용
    @Override
    protected Code doInBackground(Code... Code) {
        Code code = Code[0];

        return code;
    }

    @Override
    protected void onProgressUpdate(Code... params) {

    }

    @Override
    protected void onPostExecute(Code result) {
        super.onPostExecute(result);
    }

}
