package csr.capestart.com;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class CsrApplication extends Application {

    @Override
    public void onCreate() {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate();
    }
}
