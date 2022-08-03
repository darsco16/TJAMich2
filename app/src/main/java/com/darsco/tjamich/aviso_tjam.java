package com.darsco.tjamich;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class aviso_tjam extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso_tjam);

        Button btn = (Button) findViewById(R.id.btnRegresar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), acerca_de.class);
                startActivityForResult(intent, 0);
            }
        });
        //String str_text = "<a href=https://tjamich.gob.mx/Transparencia-Informacion-Oficio >Transparencia</a>";
        String str_text = "<a href=https://consultapublicamx.plataformadetransparencia.org.mx/vut-web/faces/view/consultaPublica.xhtml?idEntidad=MTY=&idSujetoObligado=MzUzMQ==#inicio >Transparencia</a>";
        TextView link;
        link = (TextView) findViewById(R.id.txtAviso);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        link.setText(Html.fromHtml(str_text));
    }
}
