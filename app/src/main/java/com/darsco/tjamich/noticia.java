package com.darsco.tjamich;

//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.image.SmartImageView;

public class noticia extends AppCompatActivity {

    private Entidad Item;
    private TextView tvTitulo, tvNota;
    private SmartImageView tvFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticia);

        Item = (Entidad) getIntent().getSerializableExtra("objetoData");

        TextView tvTitulo = (TextView) findViewById(R.id.txtTitulo);
        SmartImageView tvFoto = (SmartImageView) findViewById(R.id.imgFoto);
        TextView tvNota = (TextView) findViewById(R.id.txtNoticia);

        tvTitulo.setText(Item.getTitulo());
        tvFoto.setImageUrl(Item.getImgFoto());
        tvNota.setText(Item.getContenido());
    }
}
