package csr.capestart.com;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;

public class CsrApplication extends Application {

    @Override
    public void onCreate() {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate();
    }
}
