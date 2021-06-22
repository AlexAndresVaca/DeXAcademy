package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class EP_ActualizarRegistro_CambiarContrasena extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_actualizar_registro_cambiar_contrasena);
    }
    //Metodo para volver al la ventana de Profesor: Actualizar Registro
    public void volverActualizarRegistro(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaActualizarRegistro = new Intent(getApplicationContext(), EP_ActualizarRegistro.class);
        startActivity(ventanaActualizarRegistro);//solicitando que se abra la ventana de gestion clientes

    }
}