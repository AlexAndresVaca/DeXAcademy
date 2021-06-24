package com.utc.appdexacademy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EP_GestionCurso_Listar_InformacionAlumno extends AppCompatActivity {
    String idCurso = "", idInscripcion = "";
    BaseDatos bdd;
    ListView lstEstudiantesCurso;
    ArrayList<String> listaEstudiantes = new ArrayList<>();
    Cursor infomacionEstudiantes;
    EditText buscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_gestion_curso_listar_informacion_alumno);
        // Mapear
        lstEstudiantesCurso = (ListView) findViewById(R.id.lstAlumnosInscritos);
        buscador = (EditText) findViewById(R.id.txtBuscarAlumnoInscrito);
        // Base de datos
        bdd = new BaseDatos(getApplicationContext());
        //
        Bundle parametrosExtra = getIntent().getExtras(); //Capturando los parametros que se han pasado ha esta actividad
        if (parametrosExtra != null) {
            try {
                //Intente realizar estas lineas de codigo
                idCurso = parametrosExtra.getString("idCurso");
                obtenerDatos();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud" + ex.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }

    public void obtenerDatos() {
        try {
            infomacionEstudiantes = bdd.listarEstudiantesEnCurso(idCurso);
            if (infomacionEstudiantes != null) {
                setClicLista();
                // Llenar ListView
                listaEstudiantes.clear();
                do {
                    String nombre = infomacionEstudiantes.getString(15),
                            apellido = infomacionEstudiantes.getString(14),
                            estado = infomacionEstudiantes.getString(10);
                    listaEstudiantes.add(apellido + " " + nombre + " | Curso: " + estado);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEstudiantes);
                    lstEstudiantesCurso.setAdapter(adapter);
                } while (infomacionEstudiantes.moveToNext());

            } else {
                listaEstudiantes.clear();
                listaEstudiantes.add("Sin alumnos");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEstudiantes);
                lstEstudiantesCurso.setAdapter(adapter);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public void buscarEstudianteCurso(View vista) {
        String buscar = buscador.getText().toString();
        try {
            infomacionEstudiantes = bdd.listarEstudiantesEnCursoNombreEstudiante(idCurso, buscar);
            if (infomacionEstudiantes != null) {
                setClicLista();
                // Llenar ListView
                listaEstudiantes.clear();
                do {
                    String nombre = infomacionEstudiantes.getString(15),
                            apellido = infomacionEstudiantes.getString(14),
                            estado = infomacionEstudiantes.getString(10);
                    listaEstudiantes.add(apellido + " " + nombre + " | Curso: " + estado);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEstudiantes);
                    lstEstudiantesCurso.setAdapter(adapter);
                } while (infomacionEstudiantes.moveToNext());
            } else {
                listaEstudiantes.clear();
                listaEstudiantes.add("No se encontraron registros");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaEstudiantes);
                lstEstudiantesCurso.setAdapter(adapter);
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
    }

    public void volver(View vista) {
        finish();
    }

    // Metodo para hacer clic en un item de la lista
    public void setClicLista() {
        lstEstudiantesCurso.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                infomacionEstudiantes.moveToPosition(position);
                idInscripcion = infomacionEstudiantes.getString(8);
                if (infomacionEstudiantes.getString(10).equals("finalizado")) {
                    Toast.makeText(EP_GestionCurso_Listar_InformacionAlumno.this, "Ya ha sido generado un certificado previamente.", Toast.LENGTH_SHORT).show();
                } else {
                    darCertificado();
                }
                return false;
            }
        });
    }

    public void darCertificado() {
        // Recuperar datos de la vista
        AlertDialog.Builder estructuraConfirmacion = new AlertDialog.Builder(this)
                .setTitle("Entrega de certificado")
                .setMessage("Esta seguro de que deseas generar el certificado de finalización del curso?")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Otorgar Curso
                        try {
                            bdd.darCertificado(idInscripcion);
                            Toast.makeText(EP_GestionCurso_Listar_InformacionAlumno.this, "Certficado Otorgado!", Toast.LENGTH_SHORT).show();
                        } catch (Exception ex) {

                            Toast.makeText(EP_GestionCurso_Listar_InformacionAlumno.this, "Error: " + ex, Toast.LENGTH_SHORT).show();
                        }
                        buscarEstudianteCurso(null);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), "Se canceló la acción", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        // Mostrar Cuadro de dialogo
        AlertDialog cuadroDialogo = estructuraConfirmacion.create();
        cuadroDialogo.show();
    }
}