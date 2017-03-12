package fr.openium.blinkiumandroid.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Kevin on 10/03/2017.
 */

public class Blinking implements Runnable {

    private byte[] ssid;
    private byte[] password;
    private Handler handler;

    public Blinking(byte[] ssid, byte[] password, Handler handler){
        this.ssid = ssid;
        this.password = password;
        this.handler = handler;
    }

    /*private byte[] datas = { 1, 0, 1,  0, 0, 0, 0, 0, 0, 1, 1,  0, 0, 0, 1, /*header
            1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 1, 0, 1, 0, 1, 0};*/

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
        int freq = 60;
        long time = 0;
        boolean running = true;
        int count = 0;
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
}
