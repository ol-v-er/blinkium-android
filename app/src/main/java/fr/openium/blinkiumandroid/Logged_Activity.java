package com.example.kevin.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Kevin on 20/01/2017.
 */

public class Logged_Activity extends AppCompatActivity {

    //Unused for the moment
    final String EXTRA_LOGIN = "user_login";
    final String EXTRA_PASSWORD = "user_password";

    private String login;

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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        Runnable loop = new Runnable() {

            //Datas to send. First 3 bits are the start code. Next 8 bits are the number of "groups" of data
            //Next 4 are the size of each group of data in Bytes.
            private byte[] datas = { 1, 0, 1,  0, 0, 0, 0, 0, 0, 1, 1,  0, 0, 0, 1, /*header*/
                    1, 1, 1, 1, 1, 1, 1, 1,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    1, 0, 1, 0, 1, 0, 1, 0};

            @Override
            public void run() {
                boolean running = true;
                int count = 0;
                int freq = 25;

                //To give the time to put the phone on top of the light detector
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long time = 0;
                while (running) {
                    if (System.currentTimeMillis() - time > 1000.f / freq) {
                        time = System.currentTimeMillis();
                        //Sending the value of the actual bit to the handler
                        Message msg = handler.obtainMessage(0, datas[count], 0);
                        handler.sendMessage(msg);

                        count++;
                    }

                    if (count >= datas.length)
                        running = false;
                }
            }
        };

        Thread test = new Thread(loop);
        test.start();
    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Logged_ Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
