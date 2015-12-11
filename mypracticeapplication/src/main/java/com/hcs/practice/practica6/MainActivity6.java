package com.hcs.practice.practica6;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.hcs.practice.R;

/**
 * Created by fernando.ramirez on 10/12/2015.
 */
public class MainActivity6 extends AppCompatActivity {

    Button miButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.layoutInCode();
        //this.layoutInXML();
    }

    private void layoutInCode(){
        LinearLayout miLinearLayout = new LinearLayout(this);
        miLinearLayout.setOrientation(LinearLayout.VERTICAL);

        miButton = new Button(this);
        miButton.setText("Button creado desde codigo");
        miButton.setOnClickListener(onClickListener);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        miButton.setLayoutParams(layoutParams);
        miLinearLayout.addView(miButton);

        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        miLinearLayout.setLayoutParams(layoutParams2);

        setContentView(miLinearLayout);
    }

    private void layoutInXML(){
        setContentView(R.layout.activity_main6);
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

    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick (View v) {
            if(v == miButton) {
                Intent intent = new Intent(MainActivity6.this, SegundoActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}

