package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class EP_GestionCurso_Listar_MostrarInformacion extends AppCompatActivity {
    String idCursoVer;
    Cursor infoCursoVer;
    BaseDatos bdd;
    TextView txtNombreInformacionCurso, txtDetalleInformacionCurso, txtHorasInformacionCurso, txtFechaInicioInformacionCurso, txtFechaFinInformacionCurso, txtUrlInformacionCurso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_gestion_curso_listar_mostrar_informacion);
        bdd = new BaseDatos(getApplicationContext());
//        Mapear
        txtNombreInformacionCurso = (TextView) findViewById(R.id.txtNombreInformacionCurso);
        txtDetalleInformacionCurso = (TextView) findViewById(R.id.txtDetalleInformacionCurso);
        txtHorasInformacionCurso = (TextView) findViewById(R.id.txtHorasInformacionCurso);
        txtFechaInicioInformacionCurso = (TextView) findViewById(R.id.txtFechaInicioInformacionCurso);
        txtFechaFinInformacionCurso = (TextView) findViewById(R.id.txtFechaFinInformacionCurso);
        txtUrlInformacionCurso = (TextView) findViewById(R.id.txtUrlInformacionCurso);


        Bundle parametrosExtra = getIntent().getExtras(); //Capturando los parametros que se han pasado ha esta actividad
        if (parametrosExtra != null) {
            try {
                //Intente realizar estas lineas de codigo
                idCursoVer = parametrosExtra.getString("idCurso");
                try {
                    infoCursoVer = bdd.buscarCursoPorId(idCursoVer);
                    if (infoCursoVer != null) {
                        txtNombreInformacionCurso.setText(infoCursoVer.getString(1));
                        txtDetalleInformacionCurso.setText(infoCursoVer.getString(2));
                        txtHorasInformacionCurso.setText(infoCursoVer.getString(5));
                        txtFechaInicioInformacionCurso.setText(infoCursoVer.getString(3));
                        txtFechaFinInformacionCurso.setText(infoCursoVer.getString(4));
                        if (!infoCursoVer.getString(6).isEmpty()) {
                            txtUrlInformacionCurso.setText(infoCursoVer.getString(6));
                        } else {
                            txtUrlInformacionCurso.setText("Sin enlace");
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

    public void abrirEditarCurso(View vista) {
        Intent editarCursosProfesor = new Intent(getApplicationContext(), EP_Gestion_Curso_Listar__EditarInformacion.class);
        finish();
        editarCursosProfesor.putExtra("idCurso", idCursoVer);
        startActivity(editarCursosProfesor);
    }

    public void cerrar(View vista) {
        Intent volverListarCursos = new Intent(getApplicationContext(), EP_GestionCurso.class);
        finish();
        startActivity(volverListarCursos);
    }
}