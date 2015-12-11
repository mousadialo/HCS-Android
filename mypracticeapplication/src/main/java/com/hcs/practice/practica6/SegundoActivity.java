package com.hcs.practice.practica6;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.hcs.practice.MainActivity;
import com.hcs.practice.R;

/**
 * Created by fernando.ramirez on 10/12/2015.
 */
public class SegundoActivity extends AppCompatActivity {

    private Button button1, button2, button3, button4, btnReturnToPracticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segundo);

        button1 = (Button)findViewById(R.id.btn1);
        button2 = (Button)findViewById(R.id.btn2);
        button3 = (Button)findViewById(R.id.btn3);
        button4 = (Button)findViewById(R.id.btn4);
        btnReturnToPracticas = (Button)findViewById(R.id.btnReturnToPracticas);

        button1.setOnClickListener(onClickListener);
        button2.setOnClickListener(onClickListener);
        button3.setOnClickListener(onClickListener);
        button4.setOnClickListener(onClickListener);
        btnReturnToPracticas.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick (View v) {
            if(v == button1) {
                Intent intent = new Intent(SegundoActivity.this, MainActivity6.class);
                startActivity(intent);
                finish();
            } else if (v == button2) {
                Intent intent = new Intent(SegundoActivity.this, MainActivity6.class);
                intent.putExtra("valor1", "Este es un valor");
                intent.putExtra("valor2", 2);
                startActivity(intent);
                finish();
            } else if (v == button3) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://omnius.com.mx"));
                startActivity(intent);
            } else if (v == button4) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:4492777082"));
                startActivity(intent);
            } else if (v == btnReturnToPracticas) {
                Intent intent = new Intent(SegundoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}
