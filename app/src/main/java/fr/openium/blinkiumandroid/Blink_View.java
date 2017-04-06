package fr.openium.blinkiumandroid;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
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
        this.addView(countdown);
    }

    public void go(ArrayList<String> d){
        datas = ConvertUtils.byteArrayToBlinkStateArray(ConvertUtils.encode(d));
        /*datas = ConvertUtils.byteArrayToBlinkStateArray(ConvertUtils.encode("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"));*/
        index = 0;
        countdown_value = 3;
        countdown_flag = true;
    }


    private ArrayList<Long> mData = new ArrayList<Long>();
    private ArrayList<Long> mDataOnDraw = new ArrayList<Long>();
    private long mPrecedentTime = 0;
    private long mPrecedentTimeOnDraw = 0;

    private void logEvent(ArrayList<Long> data) {
        Date d= new Date();
        long time = d.getTime();
        if (mPrecedentTime != 0){
            data.add(time - mPrecedentTime);
        }
        mPrecedentTime = time;


    }

    private void logEventOnDraw(ArrayList<Long> data) {
        Date d= new Date();
        long time = d.getTime();
        if (mPrecedentTimeOnDraw != 0){
            data.add(time - mPrecedentTimeOnDraw);
        }
        mPrecedentTimeOnDraw = time;
    }

    private void computeEventTime(String from,ArrayList<Long> data, long mPrecedentTime) {
        long average = 0;
        long max = 0;
        long min = 250;
        for (long l : data){
            average+=l;
            if (l > max){
                max = l;
            }
            if (l<min){
                min = l;
            }
        }
        Log.d("blink"+from, ""+1.0f*average/data.size());
        Log.d("blink"+from, "max="+max+ " min="+min);
        Log.d("blink"+from, Arrays.toString(data.toArray()));
    }

    public void updateView(Message msg){
        countdown.setVisibility(INVISIBLE);
        if(msg.what == Blinking.BLINKING_DATA){
            logEvent(mData);
            if (msg.obj == Blink_State.BLACK) {
                this.setBackgroundColor(Color.BLACK);
            } else if (msg.obj == Blink_State.WHITE){
                this.setBackgroundColor(Color.WHITE);
            }
        } else if (msg.what == Blinking.COUNTDOWN_DATA){
            countdown.setVisibility(VISIBLE);
            if(msg.arg1 == 0){
                countdown.setText("GO");
            }else {
                countdown.setText(Integer.toString(msg.arg1));
            }
        } else if (msg.what == Blinking.FINISHED_DATA){
            computeEventTime("handler",mData,mPrecedentTime);
            computeEventTime("onDraw",mDataOnDraw,mPrecedentTimeOnDraw);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(countdown_flag) {
            long t = System.nanoTime();
            if(t - lastTime > 1000000000) {
                if(countdown_value == 0){
                    countdown_flag = false;
                    blinking_flag = true;
                }
                countdown.setText(String.valueOf(countdown_value));
                countdown_value = countdown_value - 1;
                lastTime = t;
            }
        }

        if(blinking_flag) {
                countdown.setText("");
                switch (datas[index]) {
                    case BLACK:
                        canvas.drawRGB(0, 0, 0);
                        break;
                    case WHITE:
                        canvas.drawRGB(255, 255, 255);
                        break;
                }

                logEventOnDraw(mDataOnDraw);

                index = index + 1;

                if (index == datas.length) {
                    blinking_flag = false;
                    computeEventTime("onDraw",mDataOnDraw,mPrecedentTimeOnDraw);
                }

        }

        invalidate();
    }

    final private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            updateView(msg);
        }
    };
}
