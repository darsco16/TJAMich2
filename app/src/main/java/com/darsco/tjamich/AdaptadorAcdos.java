package com.darsco.tjamich;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

import java.util.ArrayList;

public class AdaptadorAcdos extends BaseAdapter {
    private Context context;
    private ArrayList<Entidad> listItems;

    public AdaptadorAcdos(Context context, ArrayList<Entidad> listItems) {
        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Entidad Item = (Entidad) getItem(position);

        convertView = LayoutInflater.from(context).inflate(R.layout.xacdos, null);
        TextView txtFechaAcdo = (TextView) convertView.findViewById(R.id.txtFechaAcdo);
        TextView txtAcdo = (TextView) convertView.findViewById(R.id.txtAcdo);

        txtFechaAcdo.setText(Item.getTitulo());
        txtAcdo.setText(Item.getContenido());

        return convertView;
    }
}
