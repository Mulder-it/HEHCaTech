package be.heh.hehcatech.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class UserAccessDB {
    private static final int VERSION = 1;
    private static final String NOM_DB = "HEHCatechDB";
    private static final String TABLE_USER = "User";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_NOM = "Nom";
    private static final int NUM_COL_NOM = 1;
    private static final String COL_PRENOM = "Prenom";
    private static final int NUM_COL_PRENOM = 2;
    private static final String COL_EMAIL = "Email";
    private static final int NUM_COL_EMAIL = 3;
    private static final String COL_PASSWORD = "Password";
    private static final int NUM_COL_PASSWORD = 4;
    private static final String COL_ROLE = "Role";
    private static final int NUM_COL_ROLE = 5;
    private SQLiteDatabase db;
    private DBSqlite userdb;

    public UserAccessDB(Context c){

        userdb = new DBSqlite(c, NOM_DB,null,VERSION);
    }

    public void openForWrite(){

        db = userdb.getWritableDatabase();
    }

    public void openForRead(){

        db = userdb.getReadableDatabase();
    }

    public void Close(){

        db.close();
    }

    public long insertUser(User u){

        ContentValues content = new ContentValues();
        content.put(COL_NOM, u.getNom());
        content.put(COL_PRENOM,u.getPrenom());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_EMAIL, u.getEmail());
        content.put(COL_ROLE,u.getRole());
        return db.insert(TABLE_USER, null, content);
    }
    public int updateUser(int i, User u){

        ContentValues content = new ContentValues();
        content.put(COL_NOM, u.getNom());
        content.put(COL_PRENOM, u.getPrenom());
        content.put(COL_PASSWORD, u.getPassword());
        content.put(COL_EMAIL, u.getEmail());
        content.put(COL_ROLE, u.getRole());
        return db.update(TABLE_USER, content, COL_ID + " = " + i, null);
    }

    public ArrayList<User> getAllUser() {

        Cursor c = db.query(TABLE_USER, new String[] {COL_ID, COL_NOM, COL_PRENOM,  COL_EMAIL, COL_PASSWORD, COL_ROLE }, null, null, null, null, COL_ID);
        ArrayList<User> tabUser = new ArrayList<> ();
        if (c.getCount() == 0) {

            c.close();
            return tabUser;
        }
        while (c.moveToNext()) {

            User user1 = new User();
            user1.setId(c.getInt(NUM_COL_ID));
            user1.setNom(c.getString(NUM_COL_NOM));
            user1.setPrenom(c.getString(NUM_COL_PRENOM));
            user1.setPassword(c.getString(NUM_COL_PASSWORD));
            user1.setEmail(c.getString(NUM_COL_EMAIL));
            user1.setRole(c.getString(NUM_COL_ROLE));
            tabUser.add(user1);
        }
        c.close();
        return tabUser;
    }
}
