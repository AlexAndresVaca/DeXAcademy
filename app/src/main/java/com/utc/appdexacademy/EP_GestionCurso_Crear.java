package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class EP_GestionCurso_Crear extends AppCompatActivity {
    private DatePickerDialog datePickerDialogInicio, datePickerDialogFin;
    EditText txtNombreCursoNuevo, txtDetalleCursoNuevo, txtHorasCursoNuevo, txtUrlCursoNuevo;
    Button btn_FechaInicioCursoNuevo, btn_FechaFinCursoNuevo;
    BaseDatos bdd;
    String idProfesor = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_gestion_curso_crear);
        // Mapear
        txtNombreCursoNuevo = (EditText) findViewById(R.id.txtNombreCursoNuevo);
        txtDetalleCursoNuevo = (EditText) findViewById(R.id.txtDetalleCursoNuevo);
        txtHorasCursoNuevo = (EditText) findViewById(R.id.txtHorasCursoNuevo);
        txtUrlCursoNuevo = (EditText) findViewById(R.id.txtUrlCursoNuevo);

        //Calendario
        initDatePicker();
        initDatePicker2();
        btn_FechaInicioCursoNuevo = (Button) findViewById(R.id.btn_FechaInicioCursoNuevo);
        btn_FechaInicioCursoNuevo.setText(getTodaysDate());
        btn_FechaFinCursoNuevo = (Button) findViewById(R.id.btn_FechaFinCursoNuevo);
        btn_FechaFinCursoNuevo.setText(getTodaysDate());

        bdd = new BaseDatos(getApplicationContext());
        //

        // Recuperar info del profesor
        SharedPreferences prefs = getSharedPreferences("inicioSesion", Context.MODE_PRIVATE);
        idProfesor = prefs.getString("idUsu", "");
    }

    public void registrarNuevoCurso(View vista) {
        String nombre = txtNombreCursoNuevo.getText().toString(),
                detalle = txtDetalleCursoNuevo.getText().toString(),
                duracion = txtHorasCursoNuevo.getText().toString(),
                f_inicio = btn_FechaInicioCursoNuevo.getText().toString(),
                f_fin = btn_FechaFinCursoNuevo.getText().toString(),
                url = txtUrlCursoNuevo.getText().toString();
        int error = 0;
        if (nombre.isEmpty()) {
            error++;
            txtNombreCursoNuevo.setError("Campo obligatorio");
            txtNombreCursoNuevo.requestFocus();
        }
        if (detalle.isEmpty()) {
            error++;
            txtDetalleCursoNuevo.setError("Campo obligatorio");
            txtDetalleCursoNuevo.requestFocus();
        }
        if (duracion.isEmpty()) {
            error++;
            txtHorasCursoNuevo.setError("Campo obligatorio");
            txtHorasCursoNuevo.requestFocus();
        }
        if (error == 0) {

            try {
                bdd.agregarCurso(nombre, detalle, f_inicio, f_fin, duracion, url, idProfesor);
                Toast.makeText(this, "Curso registrado!", Toast.LENGTH_SHORT).show();
                volverGestionCurso(null);
            } catch (Exception ex) {
                Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
            }
        }
    }


    //Metodo para volver al la ventana de Gestion de Cursos
    public void volverGestionCurso(View vista) {
        finish();//cerrando ventana de nueva venta
    }

    // Metodos
    //Funcion: Obtener la fecha de hoy
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    //Funcion: Selector de fecha de inicio
    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btn_FechaInicioCursoNuevo.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialogInicio = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialogInicio.getDatePicker().setMinDate(cal.getTimeInMillis());
    }

    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                btn_FechaFinCursoNuevo.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialogFin = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialogFin.getDatePicker().setMinDate(cal.getTimeInMillis());
    }

    //Hacer de la fecha una cadena y presentarla
    private String makeDateString(int day, int month, int year) {
        return day + "-" + month + "-" + year;
    }

    //Metodo: seleccionar fecha de caducidad del producto

    public void OpenDatePickerInicio(View vista) {
        datePickerDialogInicio.show();
    }

    public void OpenDatePickerFin(View vista) {
        datePickerDialogFin.show();
    }
}