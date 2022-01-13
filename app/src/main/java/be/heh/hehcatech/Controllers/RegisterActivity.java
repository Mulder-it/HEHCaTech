package be.heh.hehcatech.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import be.heh.hehcatech.DB.User;
import be.heh.hehcatech.DB.UserAccessDB;
import be.heh.hehcatech.R;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    private EditText create_nom;
    private EditText create_prenom;
    private EditText create_login;
    private EditText create_password;
    private EditText confirm_password;
    private Button bt_submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        create_nom = (EditText) findViewById(R.id.create_nom);
        create_prenom = (EditText) findViewById(R.id.create_prenom);
        create_login = (EditText) findViewById(R.id.create_login);
        create_password = (EditText) findViewById(R.id.create_password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        bt_submit = (Button) findViewById(R.id.bt_submit);
    }
    public void onMainClickManager(View v) {

        if(testNom() && testPrenom() && testLogin() && testPassword() && testConfirmPassword()){

            EditText create_nom=findViewById(R.id.create_nom);
            String sNom = create_nom.getText().toString();

            EditText create_prenom=(EditText)findViewById(R.id.create_prenom);
            String sPrenom = create_prenom.getText().toString();

            EditText create_login=(EditText)findViewById(R.id.create_login);
            String sLogin = create_login.getText().toString();

            EditText create_password=(EditText)findViewById(R.id.create_password);
            String sPassword = create_password.getText().toString();

            UserAccessDB userDB = new UserAccessDB(this);
            userDB.openForRead();
            ArrayList<User> tab_user = userDB.getAllUser();
            userDB.Close();
            userDB.openForWrite();
            User u;

            if(tab_user.isEmpty()){
                u = new User(sNom,sPrenom,sPassword,sLogin,"SUPERADMIN");

            }
            else{
                u=new User(sNom,sPrenom,sPassword,sLogin,"READ");
            }

            userDB.insertUser(u);
            userDB.Close();

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public boolean testNom(){
        EditText create_nom=findViewById(R.id.create_nom);
        String sNom = create_nom.getText().toString();
        if(sNom.matches("")){
            create_nom.setError("Vous n'avez pas entré de nom");
            return false;
        }
        else{
            return true;
        }
    }
    public boolean testPrenom(){
        EditText create_prenom=(EditText)findViewById(R.id.create_prenom);
        String sPrenom = create_prenom.getText().toString();
        if(sPrenom.matches("")){
            create_prenom.setError("Vous n'avez pas entré de prénom");
            return false;
        }
        else{
            return true;
        }
    }
    public boolean testLogin(){
        EditText create_login=(EditText)findViewById(R.id.create_login);
        String sLogin = create_login.getText().toString();
        if(sLogin.matches("")){
            create_login.setError("Vous n'avez pas entré de mail");
            return false;

        }
        else{
            int i=0;
            UserAccessDB userDB = new UserAccessDB(this);
            userDB.openForRead();
            ArrayList<User> tab_user = userDB.getAllUser();
            for(User u : tab_user){
                if(u.getEmail().equals(sLogin)){
                    i=1;
                    break;
                }
            }
            userDB.Close();
            if(i==1){
                create_login.setError("Le mail existe déjà dans la BDD");
                return false;
            }
            else{
                return true;
            }

        }
    }
    public boolean testPassword(){
        EditText create_password=(EditText)findViewById(R.id.create_password);
        String sPassword = create_password.getText().toString();
        if(sPassword.matches("")){
            create_password.setError("Vous n'avez pas entré de mot de passe");
            return false;
        }
        else{
            if(sPassword.length()<4){
                create_password.setError("Le mot de passe n'est pas assez long (4 caractères minimum)");
                return false;
            }
            else {
                return true;
            }
        }
    }

    public boolean testConfirmPassword(){
        EditText create_password=(EditText)findViewById(R.id.create_password);
        EditText confirm_password=(EditText)findViewById(R.id.confirm_password);
        String sConfPwd = confirm_password.getText().toString();
        String sPwd = create_password.getText().toString();
        if(sConfPwd.matches("")){
            confirm_password.setError("Vous n'avez pas entré de confirmation de mot de passe");
            return false;
        }
        else{
            if(!(sConfPwd.equals(sPwd))){
                confirm_password.setError("Les mots de passes ne correspondent pas");
                return false;
            }
            else{
                return true;
            }
        }
    }
}