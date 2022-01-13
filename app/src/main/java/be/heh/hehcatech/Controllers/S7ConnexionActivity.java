package be.heh.hehcatech.Controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.Bundle;

import be.heh.hehcatech.R;

public class S7ConnexionActivity extends AppCompatActivity {

    private Button bt_data_list;

    private AppContext ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s7_connexion);

        ctx = (AppContext) this.getApplicationContext() ;

        bt_data_list = (Button) findViewById(R.id.Bt_data_list);
        if ( ctx.getUserRole().equals("SUPERADMIN")) {
            bt_data_list.setVisibility(View.VISIBLE);
        }else{
            bt_data_list.setEnabled(false);
        }
    }
    public void onMainClickManager(View v) {

        switch (v.getId()) {


            case R.id.Bt_data_list:

                Intent intent = new Intent(this, DataListActivity.class);
                startActivity(intent);
                break;

            case R.id.bt_Comprime:

                EditText et_Ip = (EditText) findViewById(R.id.et_Ip);
                String sIp = et_Ip.getText().toString();

                EditText et_Rack = (EditText) findViewById(R.id.et_Rack);
                String sRack = et_Rack.getText().toString();

                EditText et_Slot = (EditText) findViewById(R.id.et_Slot);
                String sSlot = et_Slot.getText().toString();

                Intent intentComprime = new Intent(this, ComprimesActivity.class);
                intentComprime.putExtra("et_Ip",sIp);
                intentComprime.putExtra("et_Rack",sRack);
                intentComprime.putExtra("et_Slot",sSlot);
                startActivity(intentComprime);
                break;


            case R.id.bt_Regulateur:

                EditText et_Ip_Regu = (EditText) findViewById(R.id.et_Ip);
                String rIp = et_Ip_Regu.getText().toString();

                EditText et_Rack_Regu = (EditText) findViewById(R.id.et_Rack);
                String rRack = et_Rack_Regu.getText().toString();

                EditText et_Slot_Regu = (EditText) findViewById(R.id.et_Slot);
                String rSlot = et_Slot_Regu.getText().toString();

                Intent intentRegu = new Intent(this, RegulationActivity.class);
                intentRegu.putExtra("et_Ip_Regu",rIp);
                intentRegu.putExtra("et_Rack_Regu",rRack);
                intentRegu.putExtra("et_Slot_Regu",rSlot);
                startActivity(intentRegu);
                break;



        }

    }
}