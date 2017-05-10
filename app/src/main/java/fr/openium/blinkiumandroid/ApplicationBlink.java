package fr.openium.blinkiumandroid;

import android.app.Application;
import android.content.Context;

import com.codemonkeylabs.fpslibrary.FrameDataCallback;
import com.codemonkeylabs.fpslibrary.TinyDancer;

/**
 * Created by ogoutet on 10/05/2017.
 */

public class ApplicationBlink extends Application {

    @Override
    public void onCreate() {
    super.onCreate();
        Context context = getApplicationContext();
        TinyDancer.create()
                .show(context);

        //alternatively
        TinyDancer.create()
                .redFlagPercentage(.1f) // set red indicator for 10%....different from default
                .startingXPosition(200)
                .startingYPosition(600)
                .show(context);

        //you can add a callback to get frame times and the calculated
        //number of dropped frames within that window
        TinyDancer.create()
                .addFrameDataCallback(new FrameDataCallback() {
                    @Override
                    public void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames) {
                        //collect your stats here
                    }
                })
                .show(context);
    }
}
