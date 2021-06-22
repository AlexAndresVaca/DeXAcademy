package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        //Recuperar el SharedPreferences
        //instancia de clase y objeto por medio del metodo, nombre del archivo de preferencia
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        //Recuperar los datos a traves de la clave
        //almacenar en un string y accedo al metodo getString y recuperar los datos de la clave estado sesion en caso de que no exista nada si esta vacio necesito mandar un default
        String recuperar = prefs.getString("estadoSesion", "");
        String recuperarTipo = prefs.getString("tipoUsu", "");


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent ventantaInicio = null;
                if (recuperar.isEmpty()) {
                    ventantaInicio = new Intent(getApplicationContext(), Login.class);
                } else {
                    if (recuperarTipo.equals("adm")) {
                        ventantaInicio = new Intent(getApplicationContext(), EA_ListarProfesores.class);
                    }
                    if (recuperarTipo.equals("profesor")) {
                        ventantaInicio = new Intent(getApplicationContext(), EP_Menu.class);
                    }
                    if (recuperarTipo.equals("estudiante")) {
                        ventantaInicio = new Intent(getApplicationContext(), EE_Gestionar_Estudiante.class);
                    }
                }
                startActivity(ventantaInicio);
                finish();
            }
        }, 4000);
    }
}