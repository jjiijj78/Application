package b1g4.com.yourseat;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SavedSharedPreference {
    private static String TAG = "SavedSharedPreference";

    static final String PREF_START_ADDRESS = "start_address";
    static final String PREF_END_ADDRESS = "end_address";
    static final String PREF_ADDRESS_LIST = "address_list";

    static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setAddressList(Context context, ArrayList<String> addressList) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();

        editor.remove(PREF_ADDRESS_LIST);
        editor.apply();

        JSONArray jsonArray =  new JSONArray();
        for (int i = 0; i < addressList.size(); i++)
            jsonArray.put(addressList.get(i));

        if (!addressList.isEmpty())
            editor.putString(PREF_ADDRESS_LIST, jsonArray.toString());
        else
            editor.putString(PREF_ADDRESS_LIST, null);
        editor.apply();
        Log.d(TAG, jsonArray.toString());
        Log.d(TAG, String.valueOf(jsonArray.length()));

    }

    public static ArrayList<String> getAddressList(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        String json = sharedPreferences.getString(PREF_ADDRESS_LIST, null);

        ArrayList<String> addressList = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    String address = jsonArray.optString(i);
                    addressList.add(address);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Route : " + addressList.toString());
        return addressList;
    }

    public static void deleteAll(Context context) {
        Log.i(TAG, "Delete All");
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.clear();
        editor.commit();
    }
}
