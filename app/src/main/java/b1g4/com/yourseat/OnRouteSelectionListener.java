package b1g4.com.yourseat;

import android.view.View;
import android.widget.AdapterView;

interface OnRouteSelectionListener {
    public void onDataSelected(AdapterView parent, View v, int position, long id);
}
