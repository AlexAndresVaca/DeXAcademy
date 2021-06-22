package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegistroUsuario extends AppCompatActivity {
    EditText txtNombreRegistro, txtApellidoRegistro, txtEmailRegistro, txtClaveRegistro, txtTelefonoRegistro;
    BaseDatos bdd;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
        // Mapear
        txtNombreRegistro = (EditText) findViewById(R.id.txtNombreRegistrarAlumno);
        txtApellidoRegistro = (EditText) findViewById(R.id.txtApellidoRegistrarAlumno);
        txtEmailRegistro = (EditText) findViewById(R.id.txtEmailRegistrarAlumno);
        txtClaveRegistro = (EditText) findViewById(R.id.txtClaveRegistrarAlumno);
        txtTelefonoRegistro = (EditText) findViewById(R.id.txtTelefonoRegistrarAlumno);
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
        int error = 0;
        // Validar datos
        if (nombre.isEmpty() || !isWord(nombre)) {
            error++;
            txtNombreRegistro.setError("Debes ingresar un nombre válido");
            txtNombreRegistro.requestFocus();
        }
        if (apellido.isEmpty() || !isWord(apellido)) {
            error++;
            txtApellidoRegistro.setError("Debes ingresar un apellido válido");
            txtApellidoRegistro.requestFocus();
        }
        if (email.isEmpty() || !isValidEmail(email)) {
            error++;
            txtEmailRegistro.setError("Debes ingresar un email válido");
            txtEmailRegistro.requestFocus();
        }
        if (clave.isEmpty() || !isPassLetterNumber(clave) || clave.length() < 8) {
            error++;
            txtClaveRegistro.setError("Su contraseña debe tener mínimo 8 caracteres entre letras y números");
            txtClaveRegistro.requestFocus();
        }
        if (numTelefono.isEmpty() || !isNumberPhone(numTelefono) || numTelefono.length() != 10) {
            error++;
            txtTelefonoRegistro.setError("Debe ingresar un número de telefono válido");
            txtTelefonoRegistro.requestFocus();
        }

        // Si no hay errores
        if (error == 0) {
            try {
                bdd.agregarUsuario(apellido, nombre, "estudiante", email, clave, numTelefono);
            } catch (Exception ex) {
                Toast.makeText(this, "Houston tenemos un problema...", Toast.LENGTH_SHORT).show();
            }
            this.volverVentanaLogin(vista);
            Toast.makeText(this, "Listo! Estas registrado.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Upss.. No se ha registrado tus datos", Toast.LENGTH_SHORT).show();
        }

    }

    //Metodo para volver al la ventana de Login
    public void volverVentanaLogin(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(ventanaLogin);//solicitando que se abra la ventana de gestion clientes

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

    // validar que la contraseña tenga letras y numeros
    private boolean isPassLetterNumber(String pass) {
        return Pattern.matches(".*[a-zA-Z]+.*", pass) && Pattern.matches(".*[0-9]+.*", pass);

    }

}