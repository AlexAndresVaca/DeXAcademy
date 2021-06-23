package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class EE_Gestionar_Estudiante extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_gestionar_estudiante);
    }

    public void verPerfilEstudiante(View vista) {
        Intent irVerPerfilEstudiante = new Intent(getApplicationContext(), EE_Editar_Estudiante.class);
        startActivity(irVerPerfilEstudiante);
    }

    public void verCursosEstudiante(View vista) {
        Intent irVerCursosEstudiante = new Intent(getApplicationContext(), EE_Gestinar_Estudiante_MisCursos.class);
        startActivity(irVerCursosEstudiante);
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