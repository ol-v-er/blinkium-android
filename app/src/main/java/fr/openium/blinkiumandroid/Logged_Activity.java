package fr.openium.blinkiumandroid;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;


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
}
