package csr.capestart.com;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rx2androidnetworking.Rx2AndroidNetworking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import csr.capestart.com.data.ApiEndpoints;
import csr.capestart.com.extras.AppLog;
import csr.capestart.com.extras.SessionStore;
import csr.capestart.com.utils.Parser;
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
        uEditText.setText("abey.george@capestart.com");
        pEditText = findViewById(R.id.input_password);
        pEditText.setText("admin123$");
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
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        //progressDialog.setMessage("Authenticating");
        progressDialog.show();

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
                        AppLog.log(TAG, user.toString());
                        progressDialog.dismiss();
                        try {
                            if(!user.getBoolean("valid")) {
                                Toast.makeText(getApplicationContext(), user.getString("message"), Toast.LENGTH_LONG).show();
                                return;
                            }
                            SessionStore.user = Parser.parseUser(user);
                            openLandingPageActivity();
                        } catch (JSONException e) {
                            AppLog.log(TAG, e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        AppLog.log(TAG, e.getMessage());
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
