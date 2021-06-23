package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class EE_GestionarEstudiante_CambiarClave extends AppCompatActivity {
    String idEstudiante = "", claveAntigua = "";
    Cursor infoEstudiante;
    BaseDatos bdd;
    EditText txtActualizarConstrasena, txtActualizarContrasenaEstudiante, txtActualizarContrasenaConfirmar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_gestionar_estudiante_cambiar_clave);
        // Recuperar info del profesor
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idEstudiante = prefs.getString("idUsu", "");
        //
        txtActualizarConstrasena = findViewById(R.id.txtActualizarConstrasena);
        txtActualizarContrasenaEstudiante = findViewById(R.id.txtActualizarContrasenaEstudiante);
        txtActualizarContrasenaConfirmar = findViewById(R.id.txtActualizarContrasenaConfirmar);
        //
        bdd = new BaseDatos(getApplicationContext());
        // Antigua contraseña
        infoEstudiante = bdd.buscarEstudianteId(idEstudiante);
        claveAntigua = infoEstudiante.getString(5);
    }

    public void volver(View vista) {
        finish();
    }

    public void cambiarContrasena(View vista) {
        // Recoleccion de datos
        String claveAnterior = txtActualizarConstrasena.getText().toString(),
                nuevaClave = txtActualizarContrasenaEstudiante.getText().toString(),
                nuevaClaveConfirma = txtActualizarContrasenaConfirmar.getText().toString();
        int error = 0;
        //Toast.makeText(this, "Clave anterior: " + claveAnterior, Toast.LENGTH_SHORT).show();
        // Validadcion
        if (claveAnterior.isEmpty() || !claveAnterior.equals(claveAntigua)) {
            error++;
            txtActualizarConstrasena.setError("Contraseña Incorrecta");
            txtActualizarConstrasena.requestFocus();
        }
        if (nuevaClave.isEmpty() || !isPassLetterNumber(nuevaClave) || nuevaClave.length() < 8) {
            error++;
            txtActualizarContrasenaEstudiante.setError("La contraseña debe al menos contener 8 caracteres entre números y letras");
            txtActualizarContrasenaEstudiante.requestFocus();

        }
        if (!nuevaClaveConfirma.equals(nuevaClave)) {
            error++;
            txtActualizarContrasenaConfirmar.setError("Las contraseñas no coinciden");
            txtActualizarContrasenaConfirmar.requestFocus();
        }
        // Si no hay errores
        if (error == 0) {
            try {
                bdd.acualizarClave(idEstudiante, nuevaClave);
                Toast.makeText(this, "Contraseña Actualizada", Toast.LENGTH_SHORT).show();
                volver(null);
            } catch (Exception ex) {
                Toast.makeText(this, "Huston, tenemos un problema.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Metodos
    private boolean isPassLetterNumber(String pass) {
        return Pattern.matches(".*[a-zA-Z]+.*", pass) && Pattern.matches(".*[0-9]+.*", pass);

    }

}