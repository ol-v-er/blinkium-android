package fr.openium.blinkiumandroid;

import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import fr.openium.blinkiumandroid.utils.Blink_State;
import fr.openium.blinkiumandroid.utils.Blinking;
import fr.openium.blinkiumandroid.utils.ConvertUtils;


/**
 * Created by Kevin on 20/03/2017.
 */

public class Blink_View extends LinearLayout {

    private TextView countdown;
    private Blink_State[] datas;
    private int index = 0;
    private int countdown_value;
    private boolean countdown_flag = false, blinking_flag = false;
    private long lastTime = 0;
    private boolean test = true;
    private float savedBrightness;

    public Blink_View(Context context) {
        super(context);
        init();
    }

    public Blink_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Blink_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        this.setBackgroundColor(Color.BLACK);
        countdown = new TextView(getContext());
        countdown.setText("");
        countdown.setTextColor(Color.RED);
        countdown.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        countdown.setTextSize(80);
        countdown.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        countdown.setGravity(Gravity.CENTER_VERTICAL);
        this.setOrientation(LinearLayout.VERTICAL);
        this.setKeepScreenOn(true);
        this.addView(countdown);
    }

    public void go(ArrayList<String> d){
        setMaxBrightness();
        ArrayList<String> a = new ArrayList<String>();
        datas = ConvertUtils.byteArrayToBlinkStateArray(ConvertUtils.encode(d));
        index = 0;
        countdown_value = 3;
        countdown_flag = true;
    }

    private void setMaxBrightness() {
        savedBrightness = getCurrentBrightness();
        setBrightness(1.F);
    }

    private void restoreBrightness(){
       setBrightness(savedBrightness);
    }


    private void setBrightness(float brightness){
        Window window = ((Activity)getContext()).getWindow();
        WindowManager.LayoutParams layout  = window.getAttributes();
        layout.screenBrightness = brightness;
        window.setAttributes(layout);
    }

    private float getCurrentBrightness(){
        Window window = ((Activity)getContext()).getWindow();
        WindowManager.LayoutParams layout  = window.getAttributes();
        return layout.screenBrightness;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(countdown_flag) {
            updateCountdown();
        }

        if(blinking_flag) {
            updateBlinking(canvas);
        }
        invalidate();
    }

    private void onFinishSentData() {
        blinking_flag = false;
        restoreBrightness();
        Intent intent = new Intent(this.getContext(), MainActivity.class);
        this.getContext().startActivity(intent);
    }

    private void updateCountdown(){
        long t = System.nanoTime();
        if(t - lastTime > 1000000000) {
            countdown.setText(String.valueOf(countdown_value));
            if(countdown_value == -1){
                countdown_flag = false;
                blinking_flag = true;
                countdown.setText("");
            }
            countdown_value = countdown_value - 1;
            lastTime = t;
        }
    }

    private void updateBlinking(Canvas canvas){
        switch (datas[index]) {
            case BLACK:
                canvas.drawRGB(0, 0, 0);
                break;
            case WHITE:
                canvas.drawRGB(255, 255, 255);
                break;
        }

        index = index + 1;

        if (index == datas.length) {
            onFinishSentData();
        }
    }


}
