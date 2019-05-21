package b1g4.com.yourseat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AddressListViewAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    private List<String> addressList = null;
    private ViewHolder viewHolder;
    public AddressListViewAdapter(Context context, List<String> addressList) {
        this.context = context;
        this.addressList = addressList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return addressList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = inflater.inflate(R.layout.row_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.label = (TextView) view.findViewById(R.id.label);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.label.setText(addressList.get(position));
        return view;
    }

    class ViewHolder{
        public TextView label;
    }
}
