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

public class EA_RegistrarProfesor extends AppCompatActivity {
    EditText txtNombreRegistrarProfesor, txtApellidoRegistrarProfesor, txtEmailRegistrarProfesor, txtClaveRegistrarProfesor, txtClaveConfirmarRegistrarProfesor, txtTelefonoRegistrarProfesor;
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ea_registrar_profesor);
        bdd = new BaseDatos(getApplicationContext());
        txtNombreRegistrarProfesor = findViewById(R.id.txtNombreRegistrarProfesor);
        txtApellidoRegistrarProfesor = findViewById(R.id.txtApellidoRegistrarProfesor);
        txtEmailRegistrarProfesor = findViewById(R.id.txtEmailRegistrarProfesor);
        txtClaveRegistrarProfesor = findViewById(R.id.txtClaveRegistrarProfesor);
        txtClaveConfirmarRegistrarProfesor = findViewById(R.id.txtClaveConfirmarRegistrarProfesor);
        txtTelefonoRegistrarProfesor = findViewById(R.id.txtTelefonoRegistrarProfesor);
    }

    public void registrarProfesor(View vista) {
        String nombre = txtNombreRegistrarProfesor.getText().toString(),
                apellido = txtApellidoRegistrarProfesor.getText().toString(),
                correo = txtEmailRegistrarProfesor.getText().toString(),
                clave = txtClaveRegistrarProfesor.getText().toString(),
                claveConfirma = txtClaveConfirmarRegistrarProfesor.getText().toString(),
                telefono = txtTelefonoRegistrarProfesor.getText().toString();
        int error = 0;
        // Validaciones
        if (nombre.equals("") || !isWord(nombre)) {
            error++;
            txtNombreRegistrarProfesor.setError("Nombre inválido");
            txtNombreRegistrarProfesor.requestFocus();
        }
        if (apellido.equals("") || !isWord(apellido)) {
            error++;
            txtApellidoRegistrarProfesor.setError("Apellido inválido");
            txtApellidoRegistrarProfesor.requestFocus();
        }
        if (correo.equals("") || !isValidEmail(correo)) {
            error++;
            txtEmailRegistrarProfesor.setError("Correo inválido");
            txtEmailRegistrarProfesor.requestFocus();
        }
        if (clave.equals("") || !isPassLetterNumber(clave)) {
            error++;
            txtClaveRegistrarProfesor.setError("Contraseña inválido");
            txtClaveRegistrarProfesor.requestFocus();
        }
        if (telefono.equals("") || !isNumberPhone(telefono)) {
            error++;
            txtTelefonoRegistrarProfesor.setError("Número de teléfono inválido");
            txtTelefonoRegistrarProfesor.requestFocus();
        }
        if (!clave.equals(claveConfirma)) {
            error++;
            txtClaveConfirmarRegistrarProfesor.setError("Las contraseñas no coinciden");
            txtClaveConfirmarRegistrarProfesor.setText("");
            txtClaveConfirmarRegistrarProfesor.requestFocus();
        }
        //
        if (error == 0) {
            // Almacenamos el registro
            try {
                bdd.agregarUsuario(apellido, nombre, "profesor", correo, clave, telefono);
                volverListarProfesores(null);
                Toast.makeText(this, "Profesor registrado co", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Huston, tenemos un problema...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Metodo para volver al la ventana ADMINISTRADOR:Listar profesore
    public void volverListarProfesores(View vista) {
        finish();//cerrando ventana de nueva venta
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