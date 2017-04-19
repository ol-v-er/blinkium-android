package fr.openium.blinkiumandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Kevin on 20/01/2017.
 */

public class Blink_Activity extends AppCompatActivity {

    final public static String EXTRA_LOGIN = "user_login";
    final public static String EXTRA_PASSWORD = "user_password";
    private Blink_View blink_view;

    private String login;
    private String password;

    public static final Intent getIntent(Context context, String login, String password){
        Intent intent = new Intent(context, Blink_Activity.class);
        intent.putExtra(EXTRA_LOGIN, login);
        intent.putExtra(EXTRA_PASSWORD, password);
        return intent;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinking);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Log.v("Permission",""+Settings.System.canWrite(this));
            if (Settings.System.canWrite(this)) {
                Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 100);
                blink_view = (Blink_View) findViewById(R.id.blink_view);

                Intent intent = getIntent();
                if (intent != null) {
                    login = intent.getStringExtra(EXTRA_LOGIN);
                    password = intent.getStringExtra(EXTRA_PASSWORD);
                }

                ArrayList<String> d = new ArrayList<String>();
                d.add(login);
                d.add(password);

                blink_view.go(d);
            }
            else{
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                /*final Context c = this;
                final AlertDialog.Builder builder = new AlertDialog.Builder(c);
                builder.setCancelable(true);
                final AlertDialog alert = builder.create();
                builder.setMessage("Please give the permission to change brightness. \n Thanks ")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                intent.setData(Uri.parse("package:" + c.getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                c.startActivity(intent);
                                alert.dismiss();
                            }
                        });
                alert.show();*/
            }
        }



        /*RelativeLayout view = (RelativeLayout) findViewById(R.id.activity_blinking);
        view.setBackgroundColor(Color.BLACK);*/

       /* blink_view = (Blink_View) findViewById(R.id.blink_view);
        //blink_view.set

        Intent intent = getIntent();
        if (intent != null) {
            login = intent.getStringExtra(EXTRA_LOGIN);
            password = intent.getStringExtra(EXTRA_PASSWORD);
        }

        ArrayList<String> d = new ArrayList<String>();
        d.add(login);
        d.add(password);

        blink_view.go(d);*/
    }




}
