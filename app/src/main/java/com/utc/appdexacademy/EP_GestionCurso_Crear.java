package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EP_GestionCurso_Crear extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_gestion_curso_crear);
    }
    //Metodo para volver al la ventana de Gestion de Cursos
    public void volverGestionCurso(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaGestionCurso = new Intent(getApplicationContext(), EP_GestionCurso.class);
        startActivity(ventanaGestionCurso);//solicitando que se abra la ventana de gestion clientes

    }


}