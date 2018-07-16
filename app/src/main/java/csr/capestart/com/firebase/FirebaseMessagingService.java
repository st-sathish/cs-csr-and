package csr.capestart.com.firebase;

import android.content.Intent;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

import csr.capestart.com.LandingPageActivity;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.extras.SessionStore;
import csr.capestart.com.utils.MyNotificationManager;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingService";

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        AppLog.log(TAG, "Refreshed Token: "+ token);
        SessionStore.saveToken(getApplicationContext(), token);
        FirebaseUtils.registerToken(getApplicationContext());
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
        //creating an intent for the notification
        Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
        myNotificationManager.showNotification("Alert", remoteMessage.getData().get("message"), intent);
    }
}
