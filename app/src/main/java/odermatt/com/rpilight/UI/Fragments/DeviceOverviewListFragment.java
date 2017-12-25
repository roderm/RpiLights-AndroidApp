package odermatt.com.rpilight.UI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import odermatt.com.rpilight.R;

/**
 * Created by roman on 25.12.17.
 */

public class DeviceOverviewListFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_overview_list, container, false);
        ListView lv = view.findViewById(R.id.list_deviceoverview);
        if(lv == null){
            Log.e("TAG", "Fuck this shit is still null");
        }else{
            Log.i("TAG", "We got a value");
        }
        return view;
    }
}
