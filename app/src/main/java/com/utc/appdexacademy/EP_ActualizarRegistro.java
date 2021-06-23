package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EP_ActualizarRegistro extends AppCompatActivity {
    String idProfesor = "";
    TextView txtIDProfesor;
    EditText txtNombreProfesorEditar, txtApellidoProfesorEditar, txtEmailProfesorEditar, txtTelefonoProfesorEditar;
    Cursor infoProfesor;
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_actualizar_registro);
        bdd = new BaseDatos(getApplicationContext());
        //
        txtIDProfesor = (TextView) findViewById(R.id.txtIDProfesor);
        //
        txtNombreProfesorEditar = (EditText) findViewById(R.id.txtNombreProfesorEditar);
        txtApellidoProfesorEditar = (EditText) findViewById(R.id.txtApellidoProfesorEditar);
        txtEmailProfesorEditar = (EditText) findViewById(R.id.txtEmailProfesorEditar);
        txtTelefonoProfesorEditar = (EditText) findViewById(R.id.txtTelefonoProfesorEditar);

        // Recuperar info del profesor
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idProfesor = prefs.getString("idUsu", "");
        txtIDProfesor.setText(idProfesor);
        obtenerDatosProfesor();

    }

    private void obtenerDatosProfesor() {
        // Prepara datos
        String nombre = "",
                apellido = "",
                correo = "",
                telefono = "";
        // Recuperar datos del profesor
        infoProfesor = bdd.buscarProfesoresId(idProfesor);
        if (infoProfesor != null) {
            nombre = infoProfesor.getString(2);
            apellido = infoProfesor.getString(1);
            correo = infoProfesor.getString(4);
            telefono = infoProfesor.getString(6);
        }
        // Colocar en las entradas de texto
        txtNombreProfesorEditar.setText(nombre);
        txtApellidoProfesorEditar.setText(apellido);
        txtEmailProfesorEditar.setText(correo);
        txtTelefonoProfesorEditar.setText(telefono);
    }

    //Mtodo para abrir la pantalla de Profesor: Actualizar contraseña
    public void abrirPantallaActualizarContrasena(View vista) {
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