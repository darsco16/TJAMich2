package com.darsco.tjamich;

import android.content.Intent;
/*import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;*/
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class acerca_de extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_acerca_de);

        Button btn = (Button) findViewById(R.id.btnAviso);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), aviso_tjam.class);
                startActivityForResult(intent, 0);
            }
        });
        int rotacion = getWindowManager().getDefaultDisplay().getRotation();
        if (rotacion == Surface.ROTATION_0 || rotacion == Surface.ROTATION_180) {
            LinearLayout ll = (LinearLayout)findViewById(R.id.fondoLinear);
            ll.setBackgroundResource(R.drawable.fondov);
        } else {
            LinearLayout ll = (LinearLayout)findViewById(R.id.fondoLinear);
            ll.setBackgroundResource(R.drawable.fondoh);
        }
    }

}
