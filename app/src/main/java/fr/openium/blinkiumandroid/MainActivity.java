package fr.openium.blinkiumandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import fr.openium.blinkiumandroid.R;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private EditText mEditTextIdentifier;
    private EditText mEditTextPassword;

    //Unused for the moment. Names will be changed
    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PASSWORD = "user_password";

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
            String identifiant = mEditTextIdentifier.getText().toString();
            String password = mEditTextPassword.getText().toString();

            Intent intent = new Intent(MainActivity.this, Logged_Activity.class);
            intent.putExtra(EXTRA_LOGIN, identifiant);
            intent.putExtra(EXTRA_PASSWORD, password);
            startActivity(intent);
        }
    }
}
