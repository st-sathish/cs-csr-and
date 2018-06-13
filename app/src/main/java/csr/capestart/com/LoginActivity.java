package csr.capestart.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.extras.AppLog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    EditText uEditText;
    EditText pEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        uEditText = findViewById(R.id.input_email);
        uEditText.setText("admin@gmail.com");
        pEditText = findViewById(R.id.input_password);
        pEditText.setText("admin");
        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                this.doLogin();
                break;
            default:
                break;
        }
    }

    private void openLandingPageActivity() {
        Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
        startActivity(intent);
    }

    public void doLogin() {
        Map<String, String> body = new HashMap<>();
        body.put("email", uEditText.getText().toString());
        body.put("password", pEditText.getText().toString());
        Rx2AndroidNetworking
                .post(ApiEndpoints.POST_SIGNIN_API)
                .addHeaders("Content-Type","application/json")
                .addApplicationJsonBody(body)
                .build()
                .getJSONObjectObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject user) {
                        AppLog.message(TAG, user.toString());
                        openLandingPageActivity();
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.message(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
