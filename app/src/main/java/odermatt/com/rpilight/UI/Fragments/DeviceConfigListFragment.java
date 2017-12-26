package odermatt.com.rpilight.UI.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;

import odermatt.com.rpilight.Classes.Storage;
import odermatt.com.rpilight.R;
import odermatt.com.rpilight.UI.Activities.AddManualDevice;
import odermatt.com.rpilight.UI.Adapters.ConfigDevicesAdapter;
import odermatt.com.rpilight.models.RpiLight;

/**
 * Created by roman on 25.12.17.
 */

public class DeviceConfigListFragment extends Fragment {

    ConfigDevicesAdapter adapter;
    ArrayList<RpiLight> dataList;
    Storage strg;

    private class AddClickListener implements View.OnClickListener{
        public void onClick(View v){
            Intent intent = new Intent(getActivity(), AddManualDevice.class);
            startActivity(intent);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_device_configlist, container, false);
        ListView lv = view.findViewById(R.id.list_deviceconfig);
        initList(lv);
        Button btn = view.findViewById(R.id.btn_addmanualdevice);
        btn.setOnClickListener(new AddClickListener());
        return view;
    }

    private void initList(ListView lv){
        strg = new Storage(getActivity());
        dataList = new ArrayList<>();
        dataList.addAll(strg.Get());
        adapter = new ConfigDevicesAdapter(getActivity(), dataList);
        lv.setAdapter(adapter);
    }
}
