package fr.openium.blinkiumandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Kevin on 20/01/2017.
 */

public class Blink_Activity extends AppCompatActivity {

    final public static String EXTRA_LOGIN = "user_login";
    final public static String EXTRA_PASSWORD = "user_password";
    private Blink_View blink_view;

    private String login;
    private String password;

    public static final Intent getIntent(Context context, String login, String password){
        Intent intent = new Intent(context, Blink_Activity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        intent.putExtra(EXTRA_PASSWORD, password);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinking);

       blink_view = (Blink_View) findViewById(R.id.blink_view);

        Intent intent = getIntent();
        if (intent != null) {
            login = intent.getStringExtra(EXTRA_LOGIN);
            password = intent.getStringExtra(EXTRA_PASSWORD);
        }

        ArrayList<String> d = new ArrayList<String>();
        d.add(login);
        d.add(password);

        blink_view.go(d);
    }




}
