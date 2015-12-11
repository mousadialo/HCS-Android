package com.hcs.practice.practica7;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

import com.hcs.practice.R;
import com.hcs.practice.practica7.listas.ListaPersonalizadaActivity;
import com.hcs.practice.practica7.listas.ListaSimpleActivity;

/**
 * Created by fernando.ramirez on 10/12/2015.
 */
public class MainTabActivity extends android.app.TabActivity {

    private TabHost thTabH1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        thTabH1 = getTabHost();

        TabHost.TabSpec tabSpec1 = thTabH1.newTabSpec("lista_simple");
        tabSpec1.setIndicator("Lista Simple");
        Intent intentLS = new Intent(MainTabActivity.this, ListaSimpleActivity.class);
        tabSpec1.setContent(intentLS);

        TabHost.TabSpec tabSpec2 = thTabH1.newTabSpec("lista_perzonalizada");
        tabSpec2.setIndicator("Lista Personalizada");
        Intent intentLP = new Intent(MainTabActivity.this, ListaPersonalizadaActivity.class);
        tabSpec2.setContent(intentLP);

        thTabH1.addTab(tabSpec1);
        thTabH1.addTab(tabSpec2);
    }
}
