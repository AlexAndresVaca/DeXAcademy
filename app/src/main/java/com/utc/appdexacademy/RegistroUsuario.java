package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegistroUsuario extends AppCompatActivity {
    EditText txtNombreRegistro, txtApellidoRegistro, txtEmailRegistro, txtClaveRegistro, txtTelefonoRegistro;
    BaseDatos bdd;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        // Mapear
        txtNombreRegistro = (EditText) findViewById(R.id.txtNombreEstudianteEditar);
        txtApellidoRegistro = (EditText) findViewById(R.id.txtApellidoRegistro);
        txtEmailRegistro = (EditText) findViewById(R.id.txtEmailRegistro);
        txtClaveRegistro = (EditText) findViewById(R.id.txtClaveRegistro);
        txtTelefonoRegistro = (EditText) findViewById(R.id.txtTelefonoRegistro);
        // Llamar base de datos
        bdd = new BaseDatos(getApplicationContext());
    }


    //PROCESO 1: Registrar usuario
    public void registrarUsuario(View vista) {
        String nombre = txtNombreRegistro.getText().toString(),
                apellido = txtApellidoRegistro.getText().toString(),
                email = txtEmailRegistro.getText().toString(),
                clave = txtClaveRegistro.getText().toString(),
                numTelefono = txtTelefonoRegistro.getText().toString();

        this.volverVentanaLogin(vista);

    }

    //Metodo para volver al la ventana de Login
    public void volverVentanaLogin(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(ventanaLogin);//solicitando que se abra la ventana de gestion clientes

    }
}