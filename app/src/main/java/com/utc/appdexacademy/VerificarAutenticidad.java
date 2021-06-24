package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//GMF2FUNWNX
public class VerificarAutenticidad extends AppCompatActivity {
    EditText txtVerificarCertificado;
    TextView txtRespuesta;
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar_autenticidad);
        // Mapear
        txtVerificarCertificado = (EditText) findViewById(R.id.txtVerificarCertificado);
        txtRespuesta = (TextView) findViewById(R.id.txtRespuesta);
        // Base de datos
        bdd = new BaseDatos(getApplicationContext());
    }

    public void verificarCertificado(View vista) {
        String codigo = txtVerificarCertificado.getText().toString();
        try {
            Cursor respuesta = bdd.buscarCertificado(codigo);
            // Comprobar Si ya finalizó el curso
            if (respuesta != null) {
                txtRespuesta.setText("Certificado 100% Original de DeX Academy.");
            } else {
                txtRespuesta.setText("No se encontró el certificado resigtrado en DeX Academy.");
            }
            txtVerificarCertificado.setText("");
        } catch (Exception ex) {
            Toast.makeText(this, "Huston, tenemos un problema...", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para volver al la ventana de Login
    public void volverLogin(View vista) {
        finish();
        Intent ventanaLogin = new Intent(getApplicationContext(), Login.class);
        startActivity(ventanaLogin);

    }
}