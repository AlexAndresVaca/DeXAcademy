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

public class EE_Gestinar_Estudiante_MisCursos extends AppCompatActivity {
    String idEstudiante = "";
    EditText txtBuscarCursoEstudiante;
    Cursor cursosEstudiante;
    ListView lstCursosEstudiante;
    ArrayList<String> listaCursosEstudiante = new ArrayList<>();
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ee_gestinar_estudiante_mis_cursos);
        // Recupérar id del estudiante
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idEstudiante = prefs.getString("idUsu", "");
        // Mapear
        txtBuscarCursoEstudiante = (EditText) findViewById(R.id.txtBuscarCursoEstudiante);
        // Base de datos
        bdd = new BaseDatos(getApplicationContext());
        //
        lstCursosEstudiante = (ListView) findViewById(R.id.lstCursosEstudiante);
        // Cargar ListView
        obtenerDatosCursoEstudiante();
        listaSeleccionable();
    }

    private void obtenerDatosCursoEstudiante() {
        listaCursosEstudiante.clear();
        try {
            cursosEstudiante = bdd.buscarCursosParaEstudiante(idEstudiante);
            // Ingresar el nombre del curso a ListView
            if (cursosEstudiante != null) {
                do {
                    String nombreCurso = cursosEstudiante.getString(1);
                    listaCursosEstudiante.add(nombreCurso);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCursosEstudiante);
                    lstCursosEstudiante.setAdapter(adapter);
                } while (cursosEstudiante.moveToNext());
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarCurso(View vista) {
        String buscar = txtBuscarCursoEstudiante.getText().toString();
        listaCursosEstudiante.clear();

        try {
            cursosEstudiante = bdd.buscarCursosParaAlumnoPorNombre(buscar);
            // Ingresar el nombre del curso a ListView
            if (cursosEstudiante != null) {
                do {
                    String nombreCurso = cursosEstudiante.getString(1);
                    listaCursosEstudiante.add(nombreCurso);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCursosEstudiante);
                    lstCursosEstudiante.setAdapter(adapter);
                } while (cursosEstudiante.moveToNext());
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }

    }

    // Proceso para hacer clic
    public void listaSeleccionable() {
        lstCursosEstudiante.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                cursosEstudiante.moveToPosition(position);
                Intent inscribirseCursoVentana = new Intent(getApplicationContext(), EE_Estudiante_Confirmar_Curso.class);
                inscribirseCursoVentana.putExtra("idCurso", cursosEstudiante.getString(0));
                // Validacion de que no este registrado
                if (bdd.verificarSiNoEstaYaInscrito(cursosEstudiante.getString(0), idEstudiante)) {
                    Toast.makeText(EE_Gestinar_Estudiante_MisCursos.this, "Ya estas registrado en este curso!", Toast.LENGTH_SHORT).show();
                } else {
                    startActivity(inscribirseCursoVentana);
                }
//                Toast.makeText(EE_Gestinar_Estudiante_MisCursos.this, "ID: " + cursosEstudiante.getString(1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void irCursosEnProceso(View vista) {
        Intent verCursosEnProceso = new Intent(getApplicationContext(), EE_Estudiante_Cursos_en_Proceso.class);
        startActivity(verCursosEnProceso);
    }

    public void volver(View vista) {
        finish();
    }
}