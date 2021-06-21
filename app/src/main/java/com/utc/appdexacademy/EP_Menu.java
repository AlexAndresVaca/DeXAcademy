package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EP_Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_menu);
    }
    //Metodo para abrir la pantalla de Actualizar los datos del profesor
    public void abrirPantallaActualizarProfesor(View vista)
    {
        Intent pantallaActualizarProfesor = new Intent(getApplicationContext(), EP_ActualizarRegistro.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaActualizarProfesor); //Iniciando la pantalla Clientes
    }
    //Metodo para abrir la pantalla de Gestion Cursos Profesor
    public void abrirPantallaGestionCurso(View vista)
    {
        Intent pantallaGestionCuso = new Intent(getApplicationContext(), EP_GestionCurso.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaGestionCuso); //Iniciando la pantalla Clientes
    }
    //Metodo para volver al la ventana de menu profesor
    public void volverMenuProfesor(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaMenuProfesor = new Intent(getApplicationContext(), EP_Menu.class);
        startActivity(ventanaMenuProfesor);//solicitando que se abra la ventana de gestion clientes

    }
}