package b1g4.com.yourseat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class SRouteListViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<String> sRouteList = null;

    private ViewHolder viewHolder;

    //생성자에서 두 번째 인자로 List<String>을 넣어줌
    public SRouteListViewAdapter(Context context, List<String> sRouteList) {
        this.context = context;
        this.sRouteList = sRouteList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            view = inflater.inflate(R.layout.row_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) view.findViewById(R.id.label);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.label.setText(sRouteList.get(position));
        return view;
    }

    class ViewHolder{
        public TextView label;
    }
}
