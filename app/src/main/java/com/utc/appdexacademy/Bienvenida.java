package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent ventantaInicio = new Intent(getApplicationContext(), Login.class);
                startActivity(ventantaInicio);
                finish();
            }
        }, 4000);
    }
}