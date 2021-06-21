package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EP_ActualizarRegistro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_actualizar_registro);
    }
    //Metodo para volver al la ventana de ventas
    public void volverMenuProfesor(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaMenuProfesor = new Intent(getApplicationContext(), EP_Menu.class);
        startActivity(ventanaMenuProfesor);//solicitando que se abra la ventana de gestion clientes

    }
}