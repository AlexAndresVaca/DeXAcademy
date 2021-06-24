package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EE_GestionCursos_VerInformacion extends AppCompatActivity {
    String idCursoVer = "";
    Cursor infoCursoVer;
    BaseDatos bdd;
    TextView txtNombreInformacionCursoEstudiante,
            txtDetalleInformacionCursoEstudiante,
            txtFechaInicioInformacionCursoEstudiante,
            txtFechaFinInformacionCursoEstudiante,
            txtHoraCurso,
            txtUrlInformacionCurso,
            mensajeCertificado;
    Button btnVerCertificado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_gestion_cursos_ver_informacion);
        // Mapear
        txtNombreInformacionCursoEstudiante = (TextView) findViewById(R.id.txtNombreInformacionCursoEstudiante);
        txtDetalleInformacionCursoEstudiante = (TextView) findViewById(R.id.txtDetalleInformacionCursoEstudiante);
        txtFechaInicioInformacionCursoEstudiante = (TextView) findViewById(R.id.txtFechaInicioInformacionCursoEstudiante);
        txtFechaFinInformacionCursoEstudiante = (TextView) findViewById(R.id.txtFechaFinInformacionCursoEstudiante);
        txtHoraCurso = (TextView) findViewById(R.id.txtHoraCurso);
        txtUrlInformacionCurso = (TextView) findViewById(R.id.txtUrlInformacionCurso);
        mensajeCertificado = (TextView) findViewById(R.id.mensajeCertificado);
        //
        btnVerCertificado = (Button) findViewById(R.id.btnVerCertificado);
        //Base de datos
        bdd = new BaseDatos(getApplicationContext());
        //
        Bundle parametrosExtra = getIntent().getExtras(); //Capturando los parametros que se han pasado ha esta actividad
        if (parametrosExtra != null) {
            try {
                //Intente realizar estas lineas de codigo
                idCursoVer = parametrosExtra.getString("idInscripcion");
                try {
                    infoCursoVer = bdd.buscarInscripcionCurso(idCursoVer);
                    if (infoCursoVer != null) {
                        txtNombreInformacionCursoEstudiante.setText(infoCursoVer.getString(6));
                        txtDetalleInformacionCursoEstudiante.setText(infoCursoVer.getString(7));
                        txtHoraCurso.setText(infoCursoVer.getString(10));
                        txtFechaInicioInformacionCursoEstudiante.setText(infoCursoVer.getString(8));
                        txtFechaFinInformacionCursoEstudiante.setText(infoCursoVer.getString(9));
                        txtUrlInformacionCurso.setText(infoCursoVer.getString(11));
                        if (infoCursoVer.getString(2).equals("en proceso")) {
                            btnVerCertificado.setVisibility(View.GONE);
                        } else {
                            mensajeCertificado.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud" + ex.toString(), Toast.LENGTH_LONG).show();

            }
        }

    }

    public void verCerficado(View vista) {
        Intent verCertificado = new Intent(getApplicationContext(), EE_Estudiante_Cursos_Finalizados_Certificado.class);
        verCertificado.putExtra("idInscripcion", idCursoVer);
        finish();
        startActivity(verCertificado);
    }

    public void volver(View vista) {
        finish();
    }
}