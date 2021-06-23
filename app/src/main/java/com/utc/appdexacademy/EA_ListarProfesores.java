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

public class EA_ListarProfesores extends AppCompatActivity {
    BaseDatos bdd;
    EditText txtBuscarProfesor;
    ListView lstProfesores;
    ArrayList<String> listaProfesores = new ArrayList<>();
    Cursor datosProfesores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ea_listar_profesores);
        bdd = new BaseDatos(getApplicationContext());
        txtBuscarProfesor = (EditText) findViewById(R.id.txtBuscarProfesor);
        lstProfesores = (ListView) findViewById(R.id.lstProfesores);
        obtenerDatosProfesor();
    }

    // Volver a cargar datos despues del registro
    @Override
    protected void onRestart() {
        super.onRestart();
        obtenerDatosProfesor();
    }

    private void obtenerDatosProfesor() {
        listaProfesores.clear();
        datosProfesores = bdd.listarProfesores();
        if (datosProfesores != null) {
            do {
                String id = datosProfesores.getString(0),
                        nombre = datosProfesores.getString(2),
                        apellido = datosProfesores.getString(1);
                listaProfesores.add(apellido + " " + nombre);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProfesores);
                lstProfesores.setAdapter(adapter);
            } while (datosProfesores.moveToNext());
        } else {
            Toast.makeText(this, "Sin registros aun", Toast.LENGTH_SHORT).show();
        }
    }

    //Mtodo para abrir la pantalla de Agregar un nuevo profesor
    public void abrirPantallaAgregarProfesor(View vista) {
        Intent pantallaAgregarPorfesor = new Intent(getApplicationContext(), EA_RegistrarProfesor.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaAgregarPorfesor); //Iniciando la pantalla Clientes
    }

    public void buscarProfesor(View vista) {
        String buscador = txtBuscarProfesor.getText().toString();
        if (!buscador.equals("")) {
            listaProfesores.clear();
            datosProfesores = bdd.buscarProfesoresNombre(buscador);
            if (datosProfesores != null) {
                do {
                    String id = datosProfesores.getString(0),
                            nombre = datosProfesores.getString(2),
                            apellido = datosProfesores.getString(1);
                    listaProfesores.add(apellido + " " + nombre);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaProfesores);
                    lstProfesores.setAdapter(adapter);
                } while (datosProfesores.moveToNext());
            } else {
                Toast.makeText(this, "No se encontraron registros", Toast.LENGTH_SHORT).show();
            }
        } else {
            obtenerDatosProfesor();
        }
    }

    public void cerrarSesion(View vista) {
        // Borrar datos del sharepreferences
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("estadoSesion", "");
        editor.putString("tipoUsu", "");
        editor.commit();
        // Ir al login
        finish();
        Intent pantallaMenu = new Intent(getApplicationContext(), Login.class);
        startActivity(pantallaMenu);
    }
}