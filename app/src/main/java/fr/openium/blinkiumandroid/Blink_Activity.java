package fr.openium.blinkiumandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import fr.openium.blinkiumandroid.utils.Blinking;
import fr.openium.blinkiumandroid.utils.ConvertUtils;


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

        RelativeLayout view = (RelativeLayout) findViewById(R.id.activity_logged);
        view.setBackgroundColor(Color.BLACK);

        blink_view = (Blink_View) findViewById(R.id.blink_view);

        Intent intent = getIntent();
        if (intent != null) {
            login = intent.getStringExtra(EXTRA_LOGIN);
            password = intent.getStringExtra(EXTRA_PASSWORD);
        }

        blink_view.go(login, password);
    }




}
