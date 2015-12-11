package com.hcs.practice.practica7.listas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hcs.practice.R;

public class ListaPersonalizadaActivity extends AppCompatActivity {

    private ListView lvPerzonalizado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personalizada);

        lvPerzonalizado = (ListView)findViewById(R.id.lvPerzonalizado);

        String[] elem_nombres = getResources().getStringArray(R.array.elementos_lista);
        String[] elem_desc = getResources().getStringArray(R.array.elementos_descripcion);
        int[] elem_imgs = {R.drawable.ic_android, R.drawable.ic_ios, R.drawable.ic_windows,
                            R.drawable.ic_bb, R.drawable.ic_sym};

        final AdaptadorLista adaptadorLista = new AdaptadorLista(this, android.R.layout.simple_list_item_1, R.id.tvDescOS, elem_nombres);
        adaptadorLista.setContext(this);
        adaptadorLista.setNombres(elem_nombres);
        adaptadorLista.setDescripciones(elem_desc);
        adaptadorLista.setIdFotos(elem_imgs);

        lvPerzonalizado.setAdapter(adaptadorLista);
        lvPerzonalizado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListaPersonalizada", "Elemento presionado "+adaptadorLista.getItem(position));
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
