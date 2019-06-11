package b1g4.com.yourseat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SRouteListViewExpandAdapter extends BaseExpandableListAdapter {
    Context context;
    LayoutInflater inflater;
    private ArrayList<sRouteGroup> sRouteList = null;
    private ViewHolder viewHolder;

    private int childLayout=0;

    //생성자에서 두 번째 인자로 List<String>을 넣어줌
    public SRouteListViewExpandAdapter(Context context, ArrayList<sRouteGroup> sRouteList) {
        this.context = context;
        this.sRouteList = sRouteList;
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return 0;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }



    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            view = inflater.inflate(R.layout.row_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) view.findViewById(R.id.label);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.label.setText(sRouteList.get(position).searchedRouteList.toString());
        return view;
    }

    @Override
    public int getGroupCount() {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.row_listview,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) convertView.findViewById(R.id.label);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.label.setText(sRouteList.get(groupPosition).searchedRouteList.toString());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(this.childLayout,parent,false);
        }
        TextView childName = (TextView)convertView.findViewById(R.id.childStations);
        childName.setText(sRouteList.get(childPosition).child.toString());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder{
        public TextView label;
    }
}
