package odermatt.com.rpilight.UI.Adapters;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import odermatt.com.rpilight.Classes.Grpc;
import odermatt.com.rpilight.Classes.Storage;
import odermatt.com.rpilight.LightState;
import odermatt.com.rpilight.R;
import odermatt.com.rpilight.models.RpiLight;

/**
 * Created by roman on 25.12.17.
 */

public class OverviewDevicesAdapter extends BaseAdapter{

    private ArrayList<RpiLight> rpiLights;
    private Storage strg;
    private Context ctx;

    public OverviewDevicesAdapter(Context context, ArrayList<RpiLight> lights) {
        this.strg = new Storage(context);
        this.rpiLights = lights;
        this.ctx= context;
    }

    private class ListButtonListener implements View.OnClickListener{
        RpiLight mlight;
        public ListButtonListener(RpiLight l){
            this.mlight = l;
        }
        @Override
        public void onClick(View vw){
            switch (mlight.lightstate.getState()){
                case ON:
                    mlight.connection.Off();
                    break;
                case OFF:
                    mlight.connection.On();
                    break;
                case UNKNOWN:
                    mlight.connection.On();
                    break;
            }
        }
    }
    @Override
    public int getCount() {
        return rpiLights.size();
    }
    @Override
    public Object getItem(int position) {
        return rpiLights.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = null;


        LayoutInflater mInflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.item_overview_list, null);

        Button btn = (Button)convertView.findViewById(R.id.btn_overview_power);

        OverviewDevicesAdapter adapter = this;
        RpiLight light = rpiLights.get(position);
        light.GrpcStateChannel = new Grpc(light.IP, light.Port);
        light.GrpcStateChannel.ObservableForLight(light, new Grpc.OnStateChangeReceive(){
            @Override
            public void run(RpiLight mlight){
                rpiLights.set(position, mlight);
                adapter.notifyDataSetChanged();
                if(light.lightstate != null){
                    btn.setText(light.lightstate.getState() == LightState.ON ? "Off" : "On");
                    btn.setOnClickListener(new OverviewDevicesAdapter.ListButtonListener(rpiLights.get(position)));
                }
            }
        });

        ((ImageView)convertView.findViewById(R.id.img_overview_device)).setImageResource(R.drawable.ic_lightbulb);
        ((TextView)convertView.findViewById(R.id.title_overview_device)).setText(light.Hostname);
        ((TextView)convertView.findViewById(R.id.ip_overview_device)).setText(light.IP + ":" + light.Port);


        return convertView;
    }
}
