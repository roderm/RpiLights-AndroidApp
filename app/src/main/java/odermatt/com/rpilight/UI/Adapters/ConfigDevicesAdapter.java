package odermatt.com.rpilight.UI.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import odermatt.com.rpilight.Classes.Storage;
import odermatt.com.rpilight.R;
import odermatt.com.rpilight.models.RpiLight;

/**
 * Created by roman on 24.12.17.
 */


public class ConfigDevicesAdapter extends BaseAdapter{

    private ArrayList<RpiLight> rpiLights;
    private Context ctx;
    private int position;
    private Storage strg;

    private class ListButtonListener implements View.OnClickListener{
        RpiLight light;
        public ListButtonListener(RpiLight l){
            light = l;
        }

        public void onClick(View vw){
            if(light.stored){
                removeItem(light);
            }else{
                addLight(light);
            }
        }
    }

    public void removeItem(RpiLight l){
        strg.Delete(l);
        rpiLights.remove(l);
        this.notifyDataSetChanged();
    }

    public void addLight(RpiLight l){
        strg.Put(l);
        l.stored = true;
        rpiLights.add(l);
        this.notifyDataSetChanged();
    }

    public ConfigDevicesAdapter(Context context, ArrayList<RpiLight> lights) {
        this.strg = new Storage(context);
        this.rpiLights = lights;
        this.ctx= context;
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
        RpiLight light = rpiLights.get(position);

        LayoutInflater mInflater = (LayoutInflater) ctx.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.item_config_list, null);

        ((ImageView)convertView.findViewById(R.id.img_config_device)).setImageResource(R.drawable.ic_lightbulb);
        ((TextView)convertView.findViewById(R.id.title_config_device)).setText(light.Hostname);
        ((TextView)convertView.findViewById(R.id.ip_config_device)).setText(light.IP + ":" + light.Port);
        Button btn = (Button)convertView.findViewById(R.id.btn_config_device);
        btn.setText(light.stored ? "Remove" : "Add");
        btn.setOnClickListener(new ListButtonListener(rpiLights.get(position)));

        return convertView;
    }


}