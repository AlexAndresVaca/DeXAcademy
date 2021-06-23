package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class EP_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_menu);

    }

    //Metodo para abrir la pantalla de Actualizar los datos del profesor
    public void abrirPantallaActualizarProfesor(View vista) {
        Intent pantallaActualizarProfesor = new Intent(getApplicationContext(), EP_ActualizarRegistro.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaActualizarProfesor); //Iniciando la pantalla Clientes
    }

    //Metodo para abrir la pantalla de Gestion Cursos Profesor
    public void abrirPantallaGestionCurso(View vista) {
        Intent pantallaGestionCuso = new Intent(getApplicationContext(), EP_GestionCurso.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaGestionCuso); //Iniciando la pantalla Clientes
    }

    public void cerrarSesion(View vista) {
        // Borrar datos del sharepreferences
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("estadoSesion", "");
        editor.putString("tipoUsu", "");
        editor.putString("idUsu", "");
        editor.commit();
        // Ir al login
        finish();
        Intent pantallaMenu = new Intent(getApplicationContext(), Login.class);
        startActivity(pantallaMenu);
    }
}