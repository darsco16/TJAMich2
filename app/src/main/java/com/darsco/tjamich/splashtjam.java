package com.darsco.tjamich;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class splashtjam extends Activity {

    private final int DURACION_SPLASH = 2800;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashtjam);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                Intent intent = new Intent(splashtjam.this, MainActivity.class);
                startActivity(intent);
                finish();
            };
        }, DURACION_SPLASH);

        int rotacion = getWindowManager().getDefaultDisplay().getRotation();
        if (rotacion == Surface.ROTATION_0 || rotacion == Surface.ROTATION_180) {
            LinearLayout ll = (LinearLayout)findViewById(R.id.fondoRelative);
            ll.setBackgroundResource(R.drawable.fondov);
        } else {
            LinearLayout ll = (LinearLayout)findViewById(R.id.fondoRelative);
            ll.setBackgroundResource(R.drawable.fondoh);
        }
    }
}

