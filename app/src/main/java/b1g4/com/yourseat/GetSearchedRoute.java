package b1g4.com.yourseat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GetSearchedRoute extends AppCompatActivity {

    private TextView selectedItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_searched_route);

        Intent intent = getIntent();
        ArrayList<ArrayList<String>> srouteList = (ArrayList<ArrayList<String>>) intent.getSerializableExtra("sRouteList");

        ListView listView = (ListView)findViewById(R.id.srouteList);
        selectedItemView = (TextView)findViewById(R.id.selectableRoute);
        selectedItemView.setText("이동 초기의 혼잡도 순으로 정렬된 아래 길찾기 중 선택하세요.");

        //리스트뷰와 sRouteList를 연결해준다.
        ArrayList<String> srouteStringList = new ArrayList<String>();

        for(int i=0;i<srouteList.size();i++){
            String srouteListString = "출발 정류장: " + srouteList.get(i).get(1) + "\n - 노선명: " + srouteList.get(i).get(2) + "\n도착 정류장: " + srouteList.get(i).get(4);
            srouteStringList.add(srouteListString);
        }
        //final ArrayAdapter<ArrayList<String>> adapter = new ArrayAdapter<ArrayList<String>>(this, android.R.layout.simple_list_item_1,srouteList);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,srouteStringList);
        //리스트뷰의 어댑터를 지정
        listView.setAdapter(adapter);

        //리스트뷰의 아이템 클릭 시 문자열 가져옴
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String)parent.getItemAtPosition(position);
                selectedItemView.setText((position+1) + "번째 경로를 선택하셨습니다.");

                Intent resultIntent = new Intent(getApplicationContext(), UserSelectedRoute.class);
                //selectedItem을 putExtra
                resultIntent.putExtra("selectedRoute",selectedItem);
                startActivity(resultIntent);
;            }
        });
    }
}
