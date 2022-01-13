package be.heh.hehcatech.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

import be.heh.hehcatech.R;

public class MainActivity extends AppCompatActivity {

    private EditText et_main_login;
    private EditText et_main_password;
    private Button bt_main_login;
    private Button bt_main_register;
    private AppContext ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = (AppContext) this.getApplicationContext();

        et_main_login = (EditText) findViewById(R.id.et_main_login);
        et_main_password =(EditText) findViewById(R.id.et_main_password);
        bt_main_login = (Button) findViewById(R.id.bt_main_login);
        bt_main_register = (Button) findViewById(R.id.bt_main_register);

        bt_main_login.setEnabled(false);


    }

    public void onMainClickManager(View v){
        switch (v.getId()) {
            case R.id.bt_main_login:

                boolean connexion=false;
        }
    }
}