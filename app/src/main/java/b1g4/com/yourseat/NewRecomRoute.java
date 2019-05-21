package b1g4.com.yourseat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NewRecomRoute extends Fragment {

    //TextView textView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_recom_route,null);

        ArrayList<ArrayList<String>> newRoutes = new ArrayList<ArrayList<String>>();

        //test용 tv
        //textView = (TextView)view.findViewById(R.id.newRoute);
        listView = (ListView) view.findViewById(R.id.fragmentList);

        //newRouteString과 리스트뷰 연결
        ArrayList<String> newRouteString = new ArrayList<>();
        newRouteString.add("fragment test1");
        newRouteString.add("fragment test2");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,newRouteString);
        //리스트뷰의 어댑터를 지정
        listView.setAdapter(adapter);

        //리스트뷰와 sRouteList를 연결해준다.
        //ArrayList<String> srouteStringList = new ArrayList<String>();

        return view;
    }


}
