package com.darsco.tjamich;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class contacto_tjam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto_tjam);

        Button btn = (Button) findViewById(R.id.btnFace);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String facebookId = "fb://page/419576068060297";
                String urlPage = "http://www.facebook.com/tjamich";

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookId )));
                } catch (Exception e) {
                    Toast.makeText(contacto_tjam.this, "Aplicación no instalada.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPage)));
                }
            }
        });

        Button btn2 = (Button) findViewById(R.id.btnTwit);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Uri uri = Uri.parse("https://twitter.com/tjamich?lang=es");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);*/
                String twitterId = "twitter://user?user_id=277137588";
                String urlPageT = "https://twitter.com/tjamich?lang=es";
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(twitterId )));
                    } catch (Exception e)
                    {
                        Toast.makeText(contacto_tjam.this, "Aplicación no instalada.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(urlPageT)));
                    }
            }
        });

        Button btn3 = (Button) findViewById(R.id.btnYout);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.youtube.com/user/TJAMICH");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button btn4 = (Button) findViewById(R.id.btnSendmail);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "contacto@tjamich.gob.mx", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Contacto AppTJAM");
                contacto_tjam.this.startActivity(Intent.createChooser(emailIntent, null));
            }
        });
    }
}
