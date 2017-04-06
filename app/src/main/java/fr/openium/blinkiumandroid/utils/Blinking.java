package fr.openium.blinkiumandroid.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * Created by Kevin on 10/03/2017.
 */

public class Blinking implements Runnable {

    public static final int BLINKING_DATA = 0;
    public static final int COUNTDOWN_DATA = 1;
    public static final int FINISHED_DATA = 2;

    private Blink_State[] ssid;
    private Blink_State[] password;
    private Handler handler;

    public Blinking(byte[] ssid, byte[] password, Handler handler){
        //this.ssid = ConvertUtils.byteArrayToBlinkStateArray(ssid);
        this.password = ConvertUtils.byteArrayToBlinkStateArray(password);
        this.handler = handler;

        //this.ssid = ConvertUtils.byteArrayToBlinkStateArray(ConvertUtils.encode("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"));


    }

    @Override
    public void run() {
        //To give the time to put the phone on top of the light detector
        for(int i = 3; i >= 0; i-- ){
            Message msg = handler.obtainMessage(COUNTDOWN_DATA, i, 0);
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

        Message msg = handler.obtainMessage(FINISHED_DATA, 0);
        handler.sendMessage(msg);

    }

    private void sendDatas(Blink_State[] datas){
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
                Message msg = handler.obtainMessage(BLINKING_DATA, datas[count]);
                handler.sendMessage(msg);

                count++;
            }

            if (count >= datas.length)
                running = false;
        }
    }
}
