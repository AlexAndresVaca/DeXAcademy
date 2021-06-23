package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/*
 * Autores: Vanesa Quishpe, Angel Tapia, Alex Vaca
 * Creado: 18/06/2021
 * Editado: 18/06/2021
 * Descripción: Crear el login
 * Una linea mas
 * */
public class Login extends AppCompatActivity {
    EditText txteEmailLogin, txtPasswordLogin;
    BaseDatos bdd;
    CheckBox estadoSesion;
    Cursor infoUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Mapear
        txteEmailLogin = (EditText) findViewById(R.id.txteEmailLogin);
        txtPasswordLogin = (EditText) findViewById(R.id.txtPasswordLogin);
        estadoSesion = (CheckBox) findViewById(R.id.estadoSesion);
        //Llamar BDD
        bdd = new BaseDatos(getApplicationContext());
    }

    public void iniciarSesion(View vista) {
        String correo = txteEmailLogin.getText().toString(),
                clave = txtPasswordLogin.getText().toString();
        int error = 0;
        if (!correo.isEmpty() && !clave.isEmpty()) {
            infoUser = bdd.iniciarSesionUsuario(correo, clave);
            if (infoUser != null) {
                // Comprobar si es un estudiante o profesor
                String idUsu = infoUser.getString(0).toString();
                String tipoUsu = infoUser.getString(3).toString();
//                Si se desea mantener la sesion abierta
                if (estadoSesion.isChecked()) {
                    // Guardar en un sharePreference
                    //GUARDAR EN UN SHARED PREFERENCES
                    //instancia de clase y objeto por medio del metodo, nombre del archivo de preferencia
                    SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
                    //A traves del la clase SharedPreferences y del metodo de clase editor se instancia un objeto editor donde se editan las preferencias
                    SharedPreferences.Editor editor = prefs.edit();
                    //A traves del objeto editor establece la clave llamada estado sesion con un valor de 1
                    editor.putString("estadoSesion", "1");
                    editor.putString("tipoUsu", tipoUsu);
                    editor.putString("idUsu", idUsu);
                    editor.commit(); //Guardando el SharedPreferences
                }

                if (tipoUsu.equals("adm")) {
                    // Presentar menu administrador
//                    Toast.makeText(this, tipoUsu, Toast.LENGTH_SHORT).show();
                    abrirMenuAdmin();
                }
                if (tipoUsu.equals("profesor")) {
//                    Toast.makeText(this, tipoUsu, Toast.LENGTH_SHORT).show();
                    // Presentar menu profesor
                    abrirMenuProfesor();
                }
                if (tipoUsu.equals("estudiante")) {
//                    Toast.makeText(this, tipoUsu, Toast.LENGTH_SHORT).show();
                    // Presentar menu estudiante
                    abrirMenuAlumno();
                }

            } else {
                txtPasswordLogin.setText("");
                Toast.makeText(this, "Correo o contraseña erroneos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Metodo para abrir la pantalla de Agregar Usuario
    public void abrirPantallaRegistrarUsuario(View vista) {
        Intent pantallaRegistrarUsuario = new Intent(getApplicationContext(), RegistroUsuario.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaRegistrarUsuario); //Iniciando la pantalla Clientes
    }

    //Metodo para abrir la pantalla de Recuperar contraseña alumno
    public void abrirPantallaRecuperarContrasena(View vista) {
        Intent pantallaRecuperarContrasena = new Intent(getApplicationContext(), Registro_RecuperarContrasena.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaRecuperarContrasena); //Iniciando la pantalla Clientes
    }
    // Metodos

    //TEMPORAL MENU PROFESOR
    public void abrirMenuAdmin() {
        finish();
        Intent pantallaMenuAdmin = new Intent(getApplicationContext(), EA_ListarProfesores.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaMenuAdmin); //Iniciando la pantalla Clientes
    }

    public void abrirMenuProfesor() {
        finish();
        Intent pantallaMenuProfesor = new Intent(getApplicationContext(), EP_Menu.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaMenuProfesor); //Iniciando la pantalla Clientes
    }

    public void abrirMenuAlumno() {
        finish();
        Intent pantallaMenuAlumno = new Intent(getApplicationContext(), EE_Gestionar_Estudiante.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaMenuAlumno); //Iniciando la pantalla Clientes
    }
}//comentario