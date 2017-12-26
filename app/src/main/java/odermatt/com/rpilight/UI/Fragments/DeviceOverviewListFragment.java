package odermatt.com.rpilight.UI.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import odermatt.com.rpilight.Classes.Storage;
import odermatt.com.rpilight.R;
import odermatt.com.rpilight.UI.Adapters.ConfigDevicesAdapter;
import odermatt.com.rpilight.UI.Adapters.OverviewDevicesAdapter;
import odermatt.com.rpilight.models.RpiLight;

/**
 * Created by roman on 25.12.17.
 */

public class DeviceOverviewListFragment extends Fragment{

    OverviewDevicesAdapter adapter;
    ArrayList<RpiLight> dataList;
    Storage strg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_overview_list, container, false);
        ListView lv = view.findViewById(R.id.list_deviceoverview);
        initList(lv);
        return view;
    }

    private void initList(ListView lv){
        strg = new Storage(getActivity());
        dataList = strg.Get();
        adapter = new OverviewDevicesAdapter(getActivity(), dataList);
        lv.setAdapter(adapter);
    }
}
