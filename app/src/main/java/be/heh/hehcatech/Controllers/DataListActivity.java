package be.heh.hehcatech.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import be.heh.hehcatech.DB.User;
import be.heh.hehcatech.DB.UserAccessDB;
import be.heh.hehcatech.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataListActivity extends AppCompatActivity {

    AppContext ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);
        ctx = (AppContext) this.getApplicationContext() ;


        List<HashMap<String,Object>> aList = new ArrayList<HashMap<String,Object>>();
        ListView lvUsers = (ListView) findViewById(R.id.lv_right_list);

        final UserAccessDB userDB = new UserAccessDB(this);
        userDB.openForWrite();

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lv, View item, int position, long id) {

                ListView lView = (ListView) lv;

                SimpleAdapter adapter = (SimpleAdapter) lView.getAdapter();

                HashMap<String,Object> hm = (HashMap) adapter.getItem(position);

                // élément qui sera selectionné dans le layout
                RelativeLayout rLayout = (RelativeLayout) item;

                // toggle bouton correspondant à l'élément sélectionné
                ToggleButton tgl = (ToggleButton) rLayout.getChildAt(1);

                String strStatus = "";
                if(tgl.isChecked()){
                    tgl.setChecked(false);
                    strStatus = "Read";
                }else{
                    tgl.setChecked(true);
                    strStatus = "Write";
                }

                ArrayList<User> tab_user = userDB.getAllUser();

                for(User u : tab_user){
                    if(u.getEmail().equals(hm.get("usr")) ){
                        if ( u.getRole().equals("SUPERADMIN") ){
                            tgl.setChecked(true);
                            strStatus = "SuperAdmin(Write)";
                        }else{
                            u.setRole(tgl.isChecked()?"WRITE":"READ");
                            userDB.updateUser(u.getId(), u );
                        }
                        Toast.makeText(getBaseContext(), (String) hm.get("usr") + " : " + strStatus, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        lvUsers.setOnItemClickListener(itemClickListener);

        ArrayList<User> tab_user = userDB.getAllUser();

        if(tab_user.isEmpty())
            Toast.makeText(this, "Aucun utilisateur !", Toast.LENGTH_SHORT).show();
        else {
            for(User u : tab_user){
                HashMap<String, Object> hm = new HashMap<String,Object>();
                hm.put("usr", u.getEmail() );
                if ( u.getRole().equals("SUPERADMIN"))
                    hm.put("role",true);
                else
                    hm.put("role",u.getRole().equals("WRITE")?true:false );

                aList.add(hm);
            }
        }

        // Clés à utiliser dans le Hashmap
        String[] from = {"usr","role" };

        // Identifiants des vues dans lv_right_layout
        int[] to = { R.id.tv_item, R.id.tgl_role};

        // Instantiation d'un adapter pour stocker chaque enregistrement
        // R.layout.lv_right_layout définit le layout de chaque enregistrement
        SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.lv_right_layout, from, to);

        lvUsers.setAdapter(adapter);
    }
    public void onMainClickManager(View v) {

        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
    }
}