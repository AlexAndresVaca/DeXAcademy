package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EE_Estudiante_Cursos_en_Proceso extends AppCompatActivity {
    Cursor enProceso;
    EditText buscador;
    ListView lstDelEstudiante;
    ArrayList<String> listaCursos = new ArrayList<>();
    BaseDatos bdd;
    String idEstudiante = "";

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_estudiante_cursos_en_proceso);
        // Mapear
        buscador = (EditText) findViewById(R.id.txtBurcarCursosProceso);
        //
        lstDelEstudiante = (ListView) findViewById(R.id.lstCursosProceso);
        // Recuperar info del profesor
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idEstudiante = prefs.getString("idUsu", "");
        bdd = new BaseDatos(getApplicationContext());
        cargarListaCursos();
        // Funcion OnClic
        funcionOnClic();
    }

    public void funcionOnClic() {
        lstDelEstudiante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                //
                enProceso.moveToPosition(position);
//                Toast.makeText(EE_Estudiante_Cursos_en_Proceso.this, "ID: " + enProceso.getString(0), Toast.LENGTH_SHORT).show();
                Intent verInscripcion = new Intent(getApplicationContext(), EE_GestionCursos_VerInformacion.class);
                verInscripcion.putExtra("idInscripcion", enProceso.getString(0));
                startActivity(verInscripcion);
            }
        });
    }

    public void cargarListaCursos() {
        listaCursos.clear();
        try {
            enProceso = bdd.buscarCursosDelEstudiante(idEstudiante);
            if (enProceso != null) {
                do {
                    listaCursos.add(enProceso.getString(6) + " - " + enProceso.getString(2));
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCursos);
                    lstDelEstudiante.setAdapter(adapter);
                } while (enProceso.moveToNext());
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Huston, tenemos un problema..." + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarCursoDelEstudiante(View vista) {
        String buscarCurso = buscador.getText().toString();
        listaCursos.clear();
        try {
            enProceso = bdd.buscarCursosDelEstudiantePorNombre(idEstudiante, buscarCurso);
            if (enProceso != null) {
                do {
                    listaCursos.add(enProceso.getString(6) + " - " + enProceso.getString(2));
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCursos);
                    lstDelEstudiante.setAdapter(adapter);
                } while (enProceso.moveToNext());
            } else {
                Toast.makeText(this, "No se encontro ningun curso con ese nombre", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Huston, tenemos un problema..." + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public void volver(View vista) {
        finish();

    }
}