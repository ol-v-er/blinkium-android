package fr.openium.blinkiumandroid;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import fr.openium.blinkiumandroid.utils.Blinking;
import fr.openium.blinkiumandroid.utils.ConvertUtils;


/**
 * Created by Kevin on 20/01/2017.
 */

public class Blink_Activity extends AppCompatActivity {

    //Unused for the moment
    final public static String EXTRA_LOGIN = "user_login";
    final public static String EXTRA_PASSWORD = "user_password";

    private String login;

    // find a way to make this activity to return the good intent to start it

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged);

        RelativeLayout view = (RelativeLayout)findViewById(R.id.activity_logged);
        view.setBackgroundColor(Color.BLACK);

        Intent intent = getIntent();
        if (intent != null) {
            login = intent.getStringExtra(EXTRA_LOGIN);
        }

        Blinking blinking = new Blinking(ConvertUtils.encode("salut"), handler);

        Thread test = new Thread(blinking);
        test.start();
    }


    final private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            RelativeLayout view = (RelativeLayout)findViewById(R.id.activity_logged);
            if (msg.arg1 == 0) {
                view.setBackgroundColor(Color.BLACK);
            } else {
                view.setBackgroundColor(Color.WHITE);
            }
        }
    };



}
