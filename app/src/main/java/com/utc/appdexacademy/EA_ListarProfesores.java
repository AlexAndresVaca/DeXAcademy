package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EA_ListarProfesores extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ea_listar_profesores);
    }
    //Mtodo para abrir la pantalla de Agregar un nuevo profesor
    public void abrirPantallaAgregarProfesor(View vista)
    {
        Intent pantallaAgregarPorfesor = new Intent(getApplicationContext(), RegistroUsuario.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaAgregarPorfesor); //Iniciando la pantalla Clientes
    }
}