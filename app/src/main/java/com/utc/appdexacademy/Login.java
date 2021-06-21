package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/*
 * Autores: Vanesa Quishpe, Angel Tapia, Alex Vaca
 * Creado: 18/06/2021
 * Editado: 18/06/2021
 * Descripción: Crear el login
 * Una linea mas
 * */
public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    //Metodo para abrir la pantalla de Agregar Usuario
    public void abrirPantallaRegistrarUsuario(View vista)
    {
        Intent pantallaRegistrarUsuario = new Intent(getApplicationContext(), RegistroUsuario.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaRegistrarUsuario); //Iniciando la pantalla Clientes
    }
    //TEMPORAL MENU PROFESOR
    public void abrirMenuProfesor(View vista)
    {
        Intent pantallaMenuProfesoro = new Intent(getApplicationContext(), EP_Menu.class);//Creando un Intent para invocar a Cliente Activity
        startActivity(pantallaMenuProfesoro); //Iniciando la pantalla Clientes
    }
}//comentario