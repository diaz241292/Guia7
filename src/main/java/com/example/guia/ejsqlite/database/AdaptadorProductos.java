package com.example.guia.ejsqlite.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.guia.ejsqlite.R;

import java.util.List;

public class AdaptadorProductos extends ArrayAdapter<Producto> {

    public AdaptadorProductos(Context context, List<Producto> objects) {
        super(context, 0, objects);
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obteniendo el dato
        Producto contacto = getItem(position);
        // inicializando el layout correspondiente al item (Categoria)
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
        }
        TextView lblNombre_pro = (TextView) convertView.findViewById(R.id.lblNombre_prod);
        TextView lblId_pro = (TextView) convertView.findViewById(R.id.lblId_prod);
        // mostrar los datos
        lblNombre_pro.setText(contacto.getNombre());
        lblId_pro.setText(contacto.getId_producto());
        // Return la convertView ya con los datos
        return convertView;
    }
}
