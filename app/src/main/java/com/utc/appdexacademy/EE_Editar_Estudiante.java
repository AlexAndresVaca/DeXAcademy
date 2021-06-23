package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class EE_Editar_Estudiante extends AppCompatActivity {
    String idEstudiante, apellido, nombre, correo, telefono;
    Cursor infoEstudiante;
    TextView txtIdEstudianteEditar;
    EditText txtNombreEstudianteEditar, txtApellidoEstudianteEditar, txtEmailEstudianteEditar, txtTelefonoEstudianteEditar;
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_editar_estudiante);
        // Mapear
        txtIdEstudianteEditar = (TextView) findViewById(R.id.txtIdEstudianteEditar);

        txtNombreEstudianteEditar = (EditText) findViewById(R.id.txtNombreEstudianteEditar);
        txtApellidoEstudianteEditar = (EditText) findViewById(R.id.txtApellidoEstudianteEditar);
        txtEmailEstudianteEditar = (EditText) findViewById(R.id.txtEmailEstudianteEditar);
        txtTelefonoEstudianteEditar = (EditText) findViewById(R.id.txtTelefonoEstudianteEditar);
        //Base de datos
        bdd = new BaseDatos(getApplicationContext());
        // Recuperar info del profesor
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idEstudiante = prefs.getString("idUsu", "");
        txtIdEstudianteEditar.setText(idEstudiante);
        //
        try {
            obtenerDatosEstudiante();
        } catch (Exception ex) {
            Toast.makeText(this, "Huston, tenemos un problema...", Toast.LENGTH_SHORT).show();
        }
    }

    public void obtenerDatosEstudiante() {
        // Cargar datos del estudiante
        infoEstudiante = bdd.buscarEstudianteId(idEstudiante);
        apellido = infoEstudiante.getString(1).toString();
        nombre = infoEstudiante.getString(2).toString();
        correo = infoEstudiante.getString(4).toString();
        telefono = infoEstudiante.getString(6).toString();
//        Toast.makeText(this, "Apellido: " + apellido, Toast.LENGTH_SHORT).show();
        // Colocar la informacion
        txtNombreEstudianteEditar.setText(nombre);
        txtApellidoEstudianteEditar.setText(apellido);
        txtEmailEstudianteEditar.setText(correo);
        txtTelefonoEstudianteEditar.setText(telefono);
    }

    public void abrirVentanaCambiarClave(View vista) {
        Intent cambiarContrasena = new Intent(getApplicationContext(), EE_GestionarEstudiante_CambiarClave.class);
        startActivity(cambiarContrasena);
    }

    public void actualizarDatosEstudiante(View vista) {
        String nombre = txtNombreEstudianteEditar.getText().toString(),
                apellido = txtApellidoEstudianteEditar.getText().toString(),
                correo = txtEmailEstudianteEditar.getText().toString(),
                telefono = txtTelefonoEstudianteEditar.getText().toString();
        int error = 0;
        //
        if (nombre.equals("") || !isWord(nombre)) {
            error++;
            txtNombreEstudianteEditar.setError("Nombre inválido");
            txtNombreEstudianteEditar.requestFocus();
        }
        if (apellido.equals("") || !isWord(apellido)) {
            error++;
            txtApellidoEstudianteEditar.setError("Apellido inválido");
            txtApellidoEstudianteEditar.requestFocus();
        }
        if (correo.equals("") || !isValidEmail(correo)) {
            error++;
            txtEmailEstudianteEditar.setError("Correo inválido");
            txtEmailEstudianteEditar.requestFocus();
        }
        if (telefono.equals("") || !isNumberPhone(telefono)) {
            error++;
            txtTelefonoEstudianteEditar.setError("Número de teléfono inválido");
            txtTelefonoEstudianteEditar.requestFocus();
        }

        //
        if (error == 0) {
            // Almacenamos el registro
            try {

                bdd.actualizarUsuario(idEstudiante, apellido, nombre, correo, telefono);
                volver(null);
                Toast.makeText(this, "Datos actualizados", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Huston, tenemos un problema...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Metodos de validacion
    private boolean isWord(String word) {
        return Pattern.matches(".*[ a-zA-Z-ñÑáéíóúÁÉÍÓÚ].*", word);
    }

    public boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    // Validar que solo contenga texto y espacios
    private boolean isNumberPhone(String number) {
        return Pattern.matches("^09.*[0-9]$", number);
    }

    public void volver(View vista) {
        finish();
    }
}