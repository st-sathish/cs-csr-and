package csr.capestart.com;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener {

    EditText uEditText;
    EditText pEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_login);
        uEditText = findViewById(R.id.input_email);
        pEditText = findViewById(R.id.input_password);
        Button login = findViewById(R.id.btn_login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                this.openLandingPageActivity();
                break;
            default:
                break;
        }
    }

    private void openLandingPageActivity() {
        Intent intent = new Intent(LoginActivity.this, LandingPageActivity.class);
        startActivity(intent);
    }
}
