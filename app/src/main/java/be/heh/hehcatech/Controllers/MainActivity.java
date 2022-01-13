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

import be.heh.hehcatech.DB.User;
import be.heh.hehcatech.DB.UserAccessDB;
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

    public void onMainClickManager(View v) {
        switch (v.getId()) {
            case R.id.bt_main_login:

                boolean connexion=false;
                UserAccessDB userDB = new UserAccessDB(this);
                userDB.openForRead();
                ArrayList<User> tab_user = userDB.getAllUser();

                EditText et_main_login=(EditText)findViewById(R.id.et_main_login);
                String sLogin = et_main_login.getText().toString();

                EditText password=(EditText)findViewById(R.id.et_main_password);
                String sPassword = password.getText().toString();

                for(User u : tab_user){
                    if(u.getEmail().equals(sLogin) && u.getPassword().equals(sPassword)){
                        connexion=true;
                        ctx.setIdLoginConnected(u.getId());
                        ctx.setUserRole(u.getRole());
                        break;
                    }
                }
                if(connexion==true){
                    Toast.makeText(this, "Connexion réussie", Toast.LENGTH_LONG).show();

                    Intent intentMain = new Intent(this, S7ConnexionActivity.class);
                    startActivity(intentMain);
                }
                else{
                    Toast.makeText(this, "Connexion ratée", Toast.LENGTH_LONG).show();
                }
                break;


            case R.id.bt_main_register:
                Intent intentUser = new Intent(this, RegisterActivity.class);
                startActivity(intentUser);
                break;

        }
    }
}