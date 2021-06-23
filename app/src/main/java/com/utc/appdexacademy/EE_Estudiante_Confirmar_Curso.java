package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class EE_Estudiante_Confirmar_Curso extends AppCompatActivity {
    String aleatoria = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    String idCursoVer = "", idEstudiante = "";
    Cursor infoCursoVer;
    TextView txtNombreInformacionCursoEstudiante,
            txtDetalleInformacionCursoEstudiante,
            txtFechaInicioFechaInicioInformacionCursoEstudiante,
            txtFechaFinFechaInicioInformacionCursoEstudiante,
            txtHoraCurso;
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_estudiante_confirmar_curso);
        // datos del shared
        // Recupérar id del estudiante
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idEstudiante = prefs.getString("idUsu", "");
        // Mapear
        txtNombreInformacionCursoEstudiante = (TextView) findViewById(R.id.txtNombreInformacionCursoEstudiante);
        txtDetalleInformacionCursoEstudiante = (TextView) findViewById(R.id.txtDetalleInformacionCursoEstudiante);
        txtFechaInicioFechaInicioInformacionCursoEstudiante = (TextView) findViewById(R.id.txtFechaInicioFechaInicioInformacionCursoEstudiante);
        txtFechaFinFechaInicioInformacionCursoEstudiante = (TextView) findViewById(R.id.txtFechaFinFechaInicioInformacionCursoEstudiante);
        txtHoraCurso = (TextView) findViewById(R.id.txtHoraCurso);

//        Base de datos
        bdd = new BaseDatos(getApplicationContext());

        Bundle parametrosExtra = getIntent().getExtras(); //Capturando los parametros que se han pasado ha esta actividad
        if (parametrosExtra != null) {
            try {
                //Intente realizar estas lineas de codigo
                idCursoVer = parametrosExtra.getString("idCurso");
                try {
                    infoCursoVer = bdd.buscarCursoPorId(idCursoVer);
                    if (infoCursoVer != null) {
                        txtNombreInformacionCursoEstudiante.setText(infoCursoVer.getString(1));
                        txtDetalleInformacionCursoEstudiante.setText(infoCursoVer.getString(2));
                        txtFechaInicioFechaInicioInformacionCursoEstudiante.setText(infoCursoVer.getString(3));
                        txtFechaFinFechaInicioInformacionCursoEstudiante.setText(infoCursoVer.getString(4));
                        txtHoraCurso.setText(infoCursoVer.getString(5));
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud" + ex.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }

    public void volver(View vista) {
        finish();
    }

    public void solicitar(View vista) {
//        Proceso de matriculacion
        String idCurso = idCursoVer,
                idEstu = idEstudiante;
        String nombreDelCurso = txtNombreInformacionCursoEstudiante.getText().toString();
        String accessUnic = generateString(aleatoria, 10);
        try {
            bdd.agregarInscripcion(accessUnic, "en proceso", idCurso, idEstu);
            Intent cursoRegistradoExito = new Intent(getApplicationContext(), EE_Estudiante_Aceptado_Curso.class);
            finish();
            cursoRegistradoExito.putExtra("nombreCurso", nombreDelCurso);
            startActivity(cursoRegistradoExito);
        } catch (Exception ex) {
        }
    }

    // Merodo para generar un codigo unico del curso
    public static String generateString(String characters, int length) {
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
