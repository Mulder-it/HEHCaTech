package be.heh.hehcatech.Controllers;

import android.app.Application;

public class AppContext extends Application {

    private int idLoginConnected;
    private String userRole;

    public int getIdLoginConnected(){
        return idLoginConnected;
    }
    public void setIdLoginConnected (int id) {
        idLoginConnected = id;
    }

    public String getUserRole() {
        return userRole;
    }
    public void setUserRole(String id) {
        userRole = id;
    }
    @Override public void onCreate(){
        super.onCreate();
        idLoginConnected = -1;
        userRole = "READ";
    }
}
