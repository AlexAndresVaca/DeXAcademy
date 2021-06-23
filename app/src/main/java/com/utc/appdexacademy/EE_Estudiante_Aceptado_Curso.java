package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EE_Estudiante_Aceptado_Curso extends AppCompatActivity {
    String nombreCurso = "", mensaje = "";
    TextView mensajeConfirmacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_estudiante_aceptado_curso);
        // Mapeo
        mensajeConfirmacion = (TextView) findViewById(R.id.mensajeConfirmacion);
        // Recepcion de datos
        mensaje = mensajeConfirmacion.getText().toString();
        Bundle parametrosExtra = getIntent().getExtras(); //Capturando los parametros que se han pasado ha esta actividad
        if (parametrosExtra != null) {
            try {
                //Intente realizar estas lineas de codigo
                nombreCurso = parametrosExtra.getString("nombreCurso");
                mensajeConfirmacion.setText(mensaje + " " + nombreCurso);

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud" + ex.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }

    public void volver(View vista) {
        finish();
    }
}