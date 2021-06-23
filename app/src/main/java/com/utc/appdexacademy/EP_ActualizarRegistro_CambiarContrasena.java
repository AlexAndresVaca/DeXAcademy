package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class EP_ActualizarRegistro_CambiarContrasena extends AppCompatActivity {
    String idProfesor = "";
    EditText txtActualizarConstrasena, txtActualizarContrasenaProfesor, txtActualizarContrasenaConfirmar;
    BaseDatos bdd;
    Cursor infoProfesorClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_actualizar_registro_cambiar_contrasena);
        bdd = new BaseDatos(getApplicationContext());
        // Recuperar info del profesor
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idProfesor = prefs.getString("idUsu", "");
        //
        txtActualizarConstrasena = findViewById(R.id.txtActualizarConstrasena);
        txtActualizarContrasenaProfesor = findViewById(R.id.txtActualizarContrasenaProfesor);
        txtActualizarContrasenaConfirmar = findViewById(R.id.txtActualizarContrasenaConfirmar);

    }

    //Metodo para volver al la ventana de Profesor: Actualizar Registro
    public void volverActualizarRegistro(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaActualizarRegistro = new Intent(getApplicationContext(), EP_ActualizarRegistro.class);
        startActivity(ventanaActualizarRegistro);//solicitando que se abra la ventana de gestion clientes
    }

    public void actualizarContrasena(View vista) {
// Traer informacion del profesor
        try {
            // Revisar Esta funcion
            infoProfesorClave = bdd.buscarProfesoresId(idProfesor);

        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
        String claveAntigua = txtActualizarConstrasena.getText().toString(),
                claveNueva = txtActualizarContrasenaProfesor.getText().toString(),
                claveNuevaConfirma = txtActualizarContrasenaConfirmar.getText().toString();
        int error = 0;
        // Comprobar si la contraseña ingresada es igual a la de la base de datos
        if (!infoProfesorClave.getString(5).equals(claveAntigua)) {
            error++;
            txtActualizarConstrasena.setError("Contraseña Incorrecta");
            txtActualizarConstrasena.requestFocus();
        }
        if (claveNueva.equals("") || !isPassLetterNumber(claveNueva) || claveNueva.length() < 8) {
            error++;
            txtActualizarContrasenaProfesor.setError("Contraseña inválida, ingrese minimo 8 caracteres entre letras y numeros");
            txtActualizarContrasenaProfesor.requestFocus();
        }
        if (!claveNuevaConfirma.equals(claveNueva)) {
            error++;
            txtActualizarContrasenaConfirmar.setError("Las contraseñas no coinciden");
            txtActualizarContrasenaConfirmar.requestFocus();
        }
        if (error == 0) {
            // actualizar
            try {
                // Revisar Esta funcion
                bdd.acualizarClave(idProfesor, claveNueva);
                // Regresar
                volverActualizarRegistro(null);
                //
                Toast.makeText(this, "Contraseña Actualizada!", Toast.LENGTH_SHORT).show();
            } catch (Exception ex) {
                Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isPassLetterNumber(String pass) {
        return Pattern.matches(".*[a-zA-Z]+.*", pass) && Pattern.matches(".*[0-9]+.*", pass);

    }

}