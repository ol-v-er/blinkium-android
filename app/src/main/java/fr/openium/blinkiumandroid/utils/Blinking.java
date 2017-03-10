package fr.openium.blinkiumandroid.utils;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Kevin on 10/03/2017.
 */

public class Blinking implements Runnable {

    private byte[] datas;
    private Handler handler;

    public Blinking(byte[] datas, Handler handler){
        this.datas = datas;
        this.handler = handler;
    }

    /*private byte[] datas = { 1, 0, 1,  0, 0, 0, 0, 0, 0, 1, 1,  0, 0, 0, 1, /*header
            1, 1, 1, 1, 1, 1, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 0, 1, 0, 1, 0, 1, 0};*/

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
}
