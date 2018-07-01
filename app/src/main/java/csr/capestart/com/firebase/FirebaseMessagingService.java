package csr.capestart.com.firebase;

import android.content.Intent;

import com.google.firebase.messaging.RemoteMessage;

import csr.capestart.com.LandingPageActivity;
import csr.capestart.com.utils.MyNotificationManager;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        MyNotificationManager myNotificationManager = new MyNotificationManager(getApplicationContext());
        //creating an intent for the notification
        Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
        myNotificationManager.showNotification("Hello", remoteMessage.getData().get("message"), intent);
    }
}
