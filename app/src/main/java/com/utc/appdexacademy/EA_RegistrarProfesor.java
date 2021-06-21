package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EA_RegistrarProfesor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ea_registrar_profesor);
    }
    //Metodo para volver al la ventana ADMINISTRADOR:Listar profesore
    public void volverListarProfesores(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaListarProfesores = new Intent(getApplicationContext(), EA_ListarProfesores.class);
        startActivity(ventanaListarProfesores);//solicitando que se abra la ventana de gestion clientes

    }
}