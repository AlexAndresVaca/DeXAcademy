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

public class EP_GestionCurso extends AppCompatActivity {
    String idProfesor = "";
    Cursor misCursosProfesor;
    ListView lstBuscarCurso;
    ArrayList<String> listaCursosProfesor = new ArrayList<>();
    EditText txtBuscarCurso;
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_gestion_curso);
//      Mapear
        txtBuscarCurso = (EditText) findViewById(R.id.txtBuscarCurso);
        lstBuscarCurso = (ListView) findViewById(R.id.lstBuscarCurso);
        //Instanciar BDD
        bdd = new BaseDatos(getApplicationContext());
        // Recuperar info del profesor
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idProfesor = prefs.getString("idUsu", "");
        //
        obtenerMisCursosProfesor();
        listaEditable();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        obtenerMisCursosProfesor();
    }

    public void buscarMisCursosProfesor(View vista) {
        listaCursosProfesor.clear();
        String buscar = txtBuscarCurso.getText().toString();
        try {
//            Toast.makeText(this, idProfesor, Toast.LENGTH_SHORT).show();
            misCursosProfesor = bdd.buscarCursosDelProfesorPorNombre(idProfesor, buscar);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
        if (misCursosProfesor != null) {
            do {
                String nombreCurso = misCursosProfesor.getString(1);
                listaCursosProfesor.add(nombreCurso);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCursosProfesor);
                lstBuscarCurso.setAdapter(adapter);
            } while (misCursosProfesor.moveToNext());
        } else {
            Toast.makeText(this, "No se ha encontrado curso.", Toast.LENGTH_SHORT).show();
        }
    }

    private void obtenerMisCursosProfesor() {
        listaCursosProfesor.clear();
        try {
//            Toast.makeText(this, idProfesor, Toast.LENGTH_SHORT).show();
            misCursosProfesor = bdd.buscarCursosDelProfesor(idProfesor);
        } catch (Exception ex) {
            Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
        }
        if (misCursosProfesor != null) {
            do {
                String nombreCurso = misCursosProfesor.getString(1);
                listaCursosProfesor.add(nombreCurso);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaCursosProfesor);
                lstBuscarCurso.setAdapter(adapter);
            } while (misCursosProfesor.moveToNext());
        } else {
            Toast.makeText(this, "Aún no has registrado un curso. Empieza ahora!", Toast.LENGTH_SHORT).show();
        }
    }

    // Metodo Para ingresar al curso, o ver su informacion
    public void listaEditable() {
        lstBuscarCurso.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                misCursosProfesor.moveToPosition(position);
                Intent editarCursoProfesor = new Intent(getApplicationContext(), EP_GestionCurso_Listar_MostrarInformacion.class);
                editarCursoProfesor.putExtra("idCurso", misCursosProfesor.getString(0).toString());
                finish();
                startActivity(editarCursoProfesor);
//                Toast.makeText(EP_GestionCurso.this, "ID: " + misCursosProfesor.getString(0), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo para abrir la pantalla de Agregar nuevo curso
    public void abrirPantallaAgregarNuevoCurso(View vista) {
        Intent pantallaAgregarNuevoCurso = new Intent(getApplicationContext(), EP_GestionCurso_Crear.class);//Creando un Intent para invocar a N uevo curso Activity
        startActivity(pantallaAgregarNuevoCurso); //Iniciando la pantalla agregar nuevo curso
    }

    //Metodo para volver al la ventana de MenuProfesor
    public void volverMenuProfesor(View vista) {
        finish();//cerrando ventana de nueva venta
        //creando un objeto para manejar la ventana de ventas
        Intent ventanaMenuProfesor = new Intent(getApplicationContext(), EP_Menu.class);
        startActivity(ventanaMenuProfesor);//solicitando que se abra la ventana de gestion clientes

    }

}