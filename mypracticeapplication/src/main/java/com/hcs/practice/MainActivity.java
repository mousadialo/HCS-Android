package com.hcs.practice;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.hcs.practice.practica6.MainActivity6;
import com.hcs.practice.practica7.MainActivity7;
import com.hcs.practice.practica7.MainTabActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnPractica6, btnPractica7Xml, btnPractica7Tab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        btnPractica6 = (Button)findViewById(R.id.btnPractica6);
        btnPractica6.setOnClickListener(onClickListener);

        btnPractica7Xml = (Button)findViewById(R.id.btnPractica7Xml);
        btnPractica7Xml.setOnClickListener(onClickListener);

        btnPractica7Tab = (Button)findViewById(R.id.btnPractica7Tab);
        btnPractica7Tab.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick (View v) {
            Intent intent = null;
            if(v == btnPractica6) {
                intent = new Intent(MainActivity.this, MainActivity6.class);
            } else if (v == btnPractica7Xml) {
                intent = new Intent(MainActivity.this, MainActivity7.class);
            } else if (v == btnPractica7Tab) {
                intent = new Intent(MainActivity.this, MainTabActivity.class);
            }
            startActivity(intent);
            finish();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}