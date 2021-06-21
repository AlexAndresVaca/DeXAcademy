package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class RegistroUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
    }
    //Metodo para volver al la ventana de Login
    public void volverVentanaLogin(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(ventanaLogin);//solicitando que se abra la ventana de gestion clientes

    }
    //PROCESO 1: Registrar usuario
    public void registrarUsuario(View vista)
    {

        this.volverVentanaLogin(vista);

    }
}