package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EE_Estudiante_Cursos_Finalizados_Certificado extends AppCompatActivity {
    String idInscripcion = "";
    BaseDatos bdd;
    Cursor infoCursoVer;
    TextView txtNombreEstudiante,
            txtNombreCurso,
            txtDuracionCurso,
            txtNombreProfesor,
            txtCodCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_estudiante_cursos_finalizados_certificado);
        bdd = new BaseDatos(getApplicationContext());
        //Mapear
        txtNombreEstudiante = (TextView) findViewById(R.id.txtNombreEstudiante);
        txtNombreCurso = (TextView) findViewById(R.id.txtNombreCurso);
        txtDuracionCurso = (TextView) findViewById(R.id.txtDuracionCurso);
        txtNombreProfesor = (TextView) findViewById(R.id.txtNombreProfesor);
        txtCodCurso = (TextView) findViewById(R.id.txtCodCurso);

        //
        Bundle parametrosExtra = getIntent().getExtras(); //Capturando los parametros que se han pasado ha esta actividad
        if (parametrosExtra != null) {
            try {
                //Intente realizar estas lineas de codigo
                idInscripcion = parametrosExtra.getString("idInscripcion");
                try {
                    infoCursoVer = bdd.buscarInscripcionId(idInscripcion);
                    if (infoCursoVer != null) {
                        //Ingresar datos
                        txtNombreEstudiante.setText(infoCursoVer.getString(22) + " " + infoCursoVer.getString(21));
                        txtNombreCurso.setText(infoCursoVer.getString(8));
                        txtDuracionCurso.setText(infoCursoVer.getString(12) + " horas");
                        txtNombreProfesor.setText(infoCursoVer.getString(2) + " " + infoCursoVer.getString(1));
                        txtCodCurso.setText(infoCursoVer.getString(16));

                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud" + ex.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }

    public void volverMenu(View vista) {
        finish();
    }
}