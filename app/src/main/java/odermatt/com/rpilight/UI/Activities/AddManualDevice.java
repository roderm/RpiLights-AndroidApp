package odermatt.com.rpilight.UI.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import odermatt.com.rpilight.Classes.Storage;
import odermatt.com.rpilight.R;
import odermatt.com.rpilight.models.RpiLight;

/**
 * Created by roman on 25.12.17.
 */

public class AddManualDevice extends Activity {

    Storage strg = new Storage(this);
    private class SaveClickListener implements View.OnClickListener{
        @Override
        public void onClick(View vw){
            RpiLight l = new RpiLight();
            try{
                l.Hostname = ((EditText)findViewById(R.id.manual_hostname)).getText().toString();
                l.IP = ((EditText)findViewById(R.id.manual_ipaddress)).getText().toString();
                l.Port = Integer.parseInt(((EditText)findViewById(R.id.manual_port)).getText().toString());
            }catch (Exception e){
                Log.d("Add-Manual", e.getMessage());
            }
            if(l.Hostname == null){
                Toast.makeText(AddManualDevice.this, "Invalid Hostname", Toast.LENGTH_SHORT).show();
                return;
            }
            if(l.IP == null){
                Toast.makeText(AddManualDevice.this, "Invalid IP-Address or not reachable", Toast.LENGTH_SHORT).show();
                return;
            }

            if(l.Port == 0){
                Toast.makeText(AddManualDevice.this, "Invalid Port can't be 0", Toast.LENGTH_SHORT).show();
                return;
            }
            strg.Put(l);
            endActivity();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmanual);
        ((Button)findViewById(R.id.manual_save)).setOnClickListener(new SaveClickListener());
    }
    private void endActivity(){
        finish();
    }
}
