package com.hcs.practice.practica7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.hcs.practice.MainActivity;
import com.hcs.practice.R;

/**
 * Created by fernando.ramirez on 10/12/2015.
 */
public class MainActivity7 extends AppCompatActivity implements View.OnClickListener {

    Button btnReturnToPracticas, btnPresioname;
    Spinner spColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        btnReturnToPracticas = (Button) findViewById(R.id.btnReturnToPracticas);
        btnReturnToPracticas.setOnClickListener(this);

        btnPresioname = (Button) findViewById(R.id.btnPresioname);
        //Implementacion por interfaz
        btnPresioname.setOnClickListener(this);
        //Implementacion por clase anonima
        btnPresioname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //Implementacion por clase interna
        btnPresioname.setOnClickListener(eventButton);

        spColor = (Spinner) findViewById(R.id.spColor);
    }

    @Override
    public void onClick(View v) {
        if(v==btnReturnToPracticas)    {
            Intent intent = new Intent(MainActivity7.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (v == btnPresioname) {
            finish();
        }
    }

    private View.OnClickListener eventButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
