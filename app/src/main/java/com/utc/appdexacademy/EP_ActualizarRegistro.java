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
    //Mtodo para abrir la pantalla de Profesor: Actualizar contraseña
    public void abrirPantallaActualizarContrasena(View vista)
    {
        Intent pantallaActualizarContrasena = new Intent(getApplicationContext(), EP_ActualizarRegistro_CambiarContrasena.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaActualizarContrasena); //Iniciando la pantalla Clientes
    }
    //Metodo para volver al la ventana de ventas
    public void volverMenuProfesor(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaMenuProfesor = new Intent(getApplicationContext(), EP_Menu.class);
        startActivity(ventanaMenuProfesor);//solicitando que se abra la ventana de gestion clientes

    }
}