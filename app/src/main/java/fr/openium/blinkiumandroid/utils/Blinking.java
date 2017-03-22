package fr.openium.blinkiumandroid.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * Created by Kevin on 10/03/2017.
 */

public class Blinking implements Runnable {

    private byte[] ssid;
    private byte[] password;
    private Handler handler;

    public Blinking(byte[] ssid, byte[] password, Handler handler){
        //this.ssid = ssid;
        this.password = password;
        this.handler = handler;

        this.ssid = ConvertUtils.encode("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");

    }

    @Override
    public void run() {
        //To give the time to put the phone on top of the light detector
        for(int i = 3; i >= 0; i-- ){
            Message msg = handler.obtainMessage(0, 2, i);
            handler.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        sendDatas(ssid);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sendDatas(password);

    }

    private void sendDatas(byte[] datas){
        int freq = 30;
        long time = 0;
        boolean running = true;
        int count = 0;
        long t;
        while (running) {
            t = System.nanoTime();
            if (t - time > 1000000000.f / freq) {
                time = t;
                //Sending the value of the actual bit to the handler
                Message msg = handler.obtainMessage(0, datas[count], 0);
                handler.sendMessage(msg);

                count++;
            }

            if (count >= datas.length)
                running = false;
        }
    }
}
