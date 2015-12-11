package com.hcs.practice.practica7.listas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hcs.practice.R;

/**
 * Created by fernando.ramirez on 11/12/2015.
 */
public class AdaptadorLista extends ArrayAdapter<String> {

    private Context context;
    private String[] nombres;
    private String[] descripciones;
    private int[] idFotos;

    public AdaptadorLista(Context context, int resource, int tvResId, String[] strings) {
        super(context, resource, tvResId, strings);
        this.setContext(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setNombres(String[] nombres) {
        this.nombres = nombres;
    }

    public void setDescripciones(String[] descripciones) {
        this.descripciones = descripciones;
    }

    public void setIdFotos(int[] idFotos) {
        this.idFotos = idFotos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.lista_perzonalizada, parent, false);

        TextView tvNomOS = (TextView) view.findViewById(R.id.tvNombreOS);
        TextView tvDescOS = (TextView) view.findViewById(R.id.tvDescOS);
        ImageView ivOS = (ImageView) view.findViewById(R.id.ivLista);

        tvNomOS.setText(nombres[position]);
        tvDescOS.setText(descripciones[position]);
        ivOS.setImageResource(idFotos[position]);

        return view;
    }
}