package fr.openium.blinkiumandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private EditText mEditTextIdentifier;
    private EditText mEditTextPassword;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditTextIdentifier = (EditText) findViewById(R.id.idField);
        mEditTextPassword = (EditText) findViewById(R.id.passField);


        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.loginButton) {
            String login = mEditTextIdentifier.getText().toString();
            String password = mEditTextPassword.getText().toString();

            Intent intent = Blink_Activity.getIntent(this, login, password);
            startActivity(intent);
        }
    }
}
