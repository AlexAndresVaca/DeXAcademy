package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EP_GestionCurso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_gestion_curso);
    }
    //Metodo para volver al la ventana de MenuProfesor
    public void volverMenuProfesor(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaMenuProfesor = new Intent(getApplicationContext(), EP_Menu.class);
        startActivity(ventanaMenuProfesor);//solicitando que se abra la ventana de gestion clientes

    }
    //Metodo para abrir la pantalla de Agregar nuevo curso
    public void abrirPantallaAgregarNuevoCurso(View vista)
    {
        Intent pantallaAgregarNuevoCurso = new Intent(getApplicationContext(), EP_GestionCurso_Crear.class);//Creando un Intent para invocar a N uevo curso Activity
        startActivity(pantallaAgregarNuevoCurso); //Iniciando la pantalla agregar nuevo curso
    }

}