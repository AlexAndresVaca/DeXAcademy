package com.utc.appdexacademy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EP_Gestion_Curso_Listar__EditarInformacion extends AppCompatActivity {
    private DatePickerDialog datePickerDialogInicio, datePickerDialogFin;
    TextView txtIdCursoEditar;
    String idCursoVer;
    Cursor infoCursoVer;
    BaseDatos bdd;
    EditText txtNombreCursoEditar, txtDetalleCursoEditar, txtUrlCursoEditar, txtHorasCursoEditar;
    Button btn_FechaInicioCursoEditar, btn_FechaFinCursoEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ep_gestion_curso_listar_editar_informacion);
        // Mapear
        txtIdCursoEditar = (TextView) findViewById(R.id.txtIdCursoEditar);
        //
        txtNombreCursoEditar = (EditText) findViewById(R.id.txtNombreCursoEditar);
        txtDetalleCursoEditar = (EditText) findViewById(R.id.txtDetalleCursoEditar);
        txtHorasCursoEditar = (EditText) findViewById(R.id.txtHorasCursoEditar);
        txtUrlCursoEditar = (EditText) findViewById(R.id.txtUrlCursoEditar);
        //
        btn_FechaFinCursoEditar = (Button) findViewById(R.id.btn_FechaFinCursoEditar);
        btn_FechaInicioCursoEditar = (Button) findViewById(R.id.btn_FechaInicioCursoEditar);
        //
        bdd = new BaseDatos(getApplicationContext());
//
        initDatePicker();
        initDatePicker2();
        Bundle parametrosExtra = getIntent().getExtras(); //Capturando los parametros que se han pasado ha esta actividad
        if (parametrosExtra != null) {
            try {
                //Intente realizar estas lineas de codigo
                idCursoVer = parametrosExtra.getString("idCurso");
                try {
                    infoCursoVer = bdd.buscarCursoPorId(idCursoVer);
                    if (infoCursoVer != null) {
                        txtIdCursoEditar.setText(infoCursoVer.getString(0));
                        txtNombreCursoEditar.setText(infoCursoVer.getString(1));
                        txtDetalleCursoEditar.setText(infoCursoVer.getString(2));
                        txtHorasCursoEditar.setText(infoCursoVer.getString(5));
                        btn_FechaInicioCursoEditar.setText(infoCursoVer.getString(3));
                        btn_FechaFinCursoEditar.setText(infoCursoVer.getString(4));
                        txtUrlCursoEditar.setText(infoCursoVer.getString(6));

                    }
                } catch (Exception ex) {
                    Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud" + ex.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }

    //Metodos
    public void actualizarCurso(View vista) {
        String nombre = txtNombreCursoEditar.getText().toString(),
                detalle = txtDetalleCursoEditar.getText().toString(),
                horas = txtHorasCursoEditar.getText().toString(),
                f_ini = btn_FechaInicioCursoEditar.getText().toString(),
                f_fin = btn_FechaFinCursoEditar.getText().toString(),
                url = txtUrlCursoEditar.getText().toString();
        int error = 0;
        if (nombre.isEmpty()) {
            error++;
            txtNombreCursoEditar.setError("Campo obligatorio");
            txtNombreCursoEditar.requestFocus();
        }
        if (detalle.isEmpty()) {
            error++;
            txtDetalleCursoEditar.setError("Campo obligatorio");
            txtDetalleCursoEditar.requestFocus();
        }
        if (horas.isEmpty()) {
            error++;
            txtHorasCursoEditar.setError("Campo obligatorio");
            txtHorasCursoEditar.requestFocus();
        }
        if (error == 0) {

            try {
                bdd.editarCurso(idCursoVer, nombre, detalle, f_ini, f_fin, horas, url);
                Toast.makeText(this, "Curso actualizado!", Toast.LENGTH_SHORT).show();
                volver(null);
            } catch (Exception ex) {
                Toast.makeText(this, "Error: " + ex, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void volver(View vista) {
        Intent volver = new Intent(getApplicationContext(), EP_GestionCurso_Listar_MostrarInformacion.class);
        finish();
        volver.putExtra("idCurso", idCursoVer);
        startActivity(volver);
    }

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
                btn_FechaInicioCursoEditar.setText(date);
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
                btn_FechaFinCursoEditar.setText(date);
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