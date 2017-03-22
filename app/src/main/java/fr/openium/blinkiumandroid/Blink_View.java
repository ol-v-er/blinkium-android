package fr.openium.blinkiumandroid;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.openium.blinkiumandroid.utils.Blink_State;
import fr.openium.blinkiumandroid.utils.Blinking;
import fr.openium.blinkiumandroid.utils.ConvertUtils;


/**
 * Created by Kevin on 20/03/2017.
 */

public class Blink_View extends LinearLayout {

    //private boolean finished = true;
    private TextView countdown;
    private Thread blink;

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

    /*public Blink_View(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }*/

    public void init(){
        blink = new Thread();
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

    public boolean isFinished(){ return blink.getState() == Thread.State.TERMINATED; }

    public void go(String str1, String str2){
        Blinking blinking = new Blinking(ConvertUtils.encode(str1), ConvertUtils.encode(str2) ,handler);
        blink = new Thread(blinking);
        blink.start();
    }


    public void updateView(Message msg){
        countdown.setVisibility(INVISIBLE);
        if(msg.what == Blinking.BLINKING_DATA){
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
        }
    }

    final private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            updateView(msg);
        }
    };
}
