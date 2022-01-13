package be.heh.hehcatech.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import be.heh.hehcatech.DB.User;
import be.heh.hehcatech.DB.UserAccessDB;
import be.heh.hehcatech.R;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {
    TextView tv_file_datas;
    ListView lv_file_liste;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        tv_file_datas = (TextView) findViewById(R.id.tv_file_datas);
        tv_file_datas.setText("Contenu de la table Utilisateurs:\n");
        lv_file_liste = (ListView) findViewById(R.id.lv_file_liste);
        UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForRead();
        ArrayList<User> tab_user = userDB.getAllUser();
        userDB.Close();

        if(tab_user.isEmpty())Toast.makeText(this, "Base  de données vide !",
                Toast.LENGTH_SHORT).show();
        else{ ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, tab_user);
            lv_file_liste.setAdapter(adapter); }
    }
}