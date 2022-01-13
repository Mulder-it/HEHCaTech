package be.heh.hehcatech.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.hehcatech.R;
import be.heh.hehcatech.Thread.EnumRegulationDB;
import be.heh.hehcatech.Thread.PlcHandler;
import be.heh.hehcatech.Thread.PlcThreadRead;
import be.heh.hehcatech.Thread.PlcThreadWrite;

public class RegulationActivity extends AppCompatActivity {
    LinearLayout li_regulation_read;
    LinearLayout li_regulation_write;

    PlcThreadWrite plcThreadWrite;

    private AppContext ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regulation);


        Intent intent = getIntent();
        if (intent != null) {

            String rIp = "";
            if (intent.hasExtra("et_Ip_Regu")) {
                rIp = intent.getStringExtra("et_Ip_Regu");
            }

            String rRack = "";
            if (intent.hasExtra("et_Rack_Regu")) {
                rRack = intent.getStringExtra("et_Rack_Regu");
            }
            int nRack = new Integer(rRack).intValue();

            String rSlot = "";
            if (intent.hasExtra("et_Slot_Regu")) {
                rSlot = intent.getStringExtra("et_Slot_Regu");
            }
            int nSlot = new Integer(rSlot).intValue();

            initComponent();

            byte[] data = new byte[512];

            PlcThreadRead plcThreadRead = new PlcThreadRead(
                    (LinearLayout) findViewById(R.id.li_regulation_read),
                    EnumRegulationDB.class,
                    new PlcHandler(),
                    (TextView) findViewById(R.id.t_regulation_plc), data, rIp, nRack, nSlot);

            Thread threadRead = new Thread(plcThreadRead);
            threadRead.start();

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            plcThreadWrite = new PlcThreadWrite(data, rIp, nRack, nSlot);

            Thread threadWrite = new Thread(plcThreadWrite);
            threadWrite.start();

        }
    }
    public void initComponent () {
        li_regulation_read = findViewById(R.id.li_regulation_read);
        li_regulation_write = findViewById(R.id.li_regulation_write);

        ctx = (AppContext) this.getApplicationContext() ;

        if ( ctx.getUserRole().equals("READ")) {
            li_regulation_write.setVisibility(View.GONE);
        }
    }


    public void OnClickUpdateByteManager (View view){
        String tag = view.getTag().toString();
        EditText editText = new EditText(this);
        switch (tag) {
            case "DB2":
                editText = (EditText) findViewById(R.id.et_regulation_db2);
                break;
            case "DB3":
                editText = (EditText) findViewById(R.id.et_regulation_db3);
                break;
        }

        try {
            EnumRegulationDB enumRegulationDB = Enum.valueOf(EnumRegulationDB.class, tag);
            int db = enumRegulationDB.getDb();
            plcThreadWrite.setWrite(db, Byte.parseByte(editText.getText().toString(), 2));
            Toast.makeText(this, "Modification Réussie", Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, " Erreur: La valeur doit être un byte", Toast.LENGTH_LONG).show();
        }
    }

    public void OnClickUpdateWordManager (View view){
        String tag = view.getTag().toString();
        EditText editText = new EditText(this);
        switch (tag) {
            case "DB24":
                editText = (EditText) findViewById(R.id.et_regulation_db24);
                break;
            case "DB26":
                editText = (EditText) findViewById(R.id.et_regulation_db26);
                break;
            case "DB28":
                editText = (EditText) findViewById(R.id.et_regulation_db28);
                break;
            case "DB30":
                editText = (EditText) findViewById(R.id.et_regulation_db30);
                break;
        }
        EnumRegulationDB enumRegulationDB = Enum.valueOf(EnumRegulationDB.class, tag);
        int db = enumRegulationDB.getDb();
        try {
            int value = Integer.parseInt(editText.getText().toString());
            if (value >= -32768 && value <= 32767) {
                plcThreadWrite.setWrite(db, Integer.parseInt(editText.getText().toString()));
                Toast.makeText(this, "Modification Réussie !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this,
                        "Erreur: La valeur doit être un entier entre -32768 et 32767",
                        Toast.LENGTH_LONG).show();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Erreur: La valeur doit être un entier entre -32768 et 32767",
                    Toast.LENGTH_LONG).show();
        }
    }

    public void onMainClickManager(View v) {

        switch (v.getId()) {

            case R.id.Bt_web:

                String url = "http://10.1.0.118";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
        }
    }
}