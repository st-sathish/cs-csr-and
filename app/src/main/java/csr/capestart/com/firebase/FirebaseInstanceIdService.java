package csr.capestart.com.firebase;


import com.google.firebase.iid.FirebaseInstanceId;

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
       String token = FirebaseInstanceId.getInstance().getToken();
       registerToken(token);
    }

    public void registerToken(String token) {
        // api call to save the token
    }
}
