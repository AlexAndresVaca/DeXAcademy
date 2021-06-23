package com.utc.appdexacademy;

/*
 * Autores: Vanesa Quishpe, Angel Tapia, Alex Vaca
 * Creado: 18/06/2021
 * Editado: 18/06/2021
 * Descripción: Crear el login
 *
 * */


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.ref.Reference;

public class BaseDatos extends SQLiteOpenHelper {
    //definiendo el nombre de la bdd
    private static final String nombreBdd = "bdd_dex_academy";
    // definiendo la version de la bdd
    private static final int versionBdd = 1;
    // T A B L A S
    //definiendo la estructura de la tabla usuario
    private static final String tablaUsuario = "CREATE TABLE usuario(" +
            "id_usu integer PRIMARY KEY AUTOINCREMENT ," +
            "apellido_usu text ," +
            "nombre_usu text ," +
            "tipo_usu text ," +
            "correo_usu text ," +
            "clave_usu text ," +
            "telefono_usu text)";

    //definiendo la estructura de la tabla inscripcion
    private static final String tablaInscripcion = "CREATE TABLE inscripcion(" +
            "id_ins integer PRIMARY KEY AUTOINCREMENT ," +
            "codigo_unico_ins text ," +
            "estado_aprobacion_ins text ," +
            "estado_progreso_ins text ," +
            "fk_id_curso integer, " +
            "fk_id_alu integer, " +
            "FOREIGN KEY (id_curso) REFERENCES curso (id_curso), " +
            "FOREIGN KEY (id_alu) REFERENCES curso (id_alu)" +
            ")";
    // SEMILLAS
    private static final String usuAdm = "INSERT INTO usuario (apellido_usu, nombre_usu, tipo_usu, correo_usu, clave_usu, telefono_usu) " +
            "VALUES ('admin','super','adm','adm@dex.com','admin1234','0999999999');";

    //CONSTRUCTOR
    public BaseDatos(Context contexto) {
        super(contexto, nombreBdd, null, versionBdd);
    }

    ////PROCESO 1: Metodo que ejecuta automaticamente al consstruir la clase BaseDatos
    @Override
    public void onCreate(SQLiteDatabase db) {
        //ejecutando el query ddl para crear la tabla usuario con sus atributos
        db.execSQL(tablaUsuario);
        db.execSQL(usuAdm);
        //ejecutando el query ddl para crear la tabla usuario con sus atributos
        // db.execSQL(tablaInscripcion);

    }

    //PROCESO 2: Metodo que se ejecuta automaticamente cuando se detectan cambios en la versino con sus atributos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminacion de la version anterior de la tabla inscripcion
        db.execSQL("DROP TABLE IF EXISTS inscripcion");
        // Eliminacion de la version anterior de la tabla usuario
        db.execSQL("DROP TABLE IF EXISTS usuario");
        //Ejecucion del codigo para crear la tabla usuario con su nueva estructura
        db.execSQL(tablaUsuario);
        //Ejecucion del codigo para crear la tabla inscripcion con su nueva estructura
        db.execSQL(tablaInscripcion);
    }

    // G E S T I O N    D E    U S U A R I O S
    /*
     * C => Create (agregarUsuario)
     * R => Read
     * U => Update
     * D => Delete
     * */
    // C R E A T E D
    ///PROCESO 3: Metodo para insertar datos, retorna tru cuando inserta y false cuando hay error
    public boolean agregarUsuario(String apellido, String nombre, String tipo, String correo, String clave, String telefono) {
        SQLiteDatabase miBDD = getWritableDatabase(); //Llamando a la base de datos en el objeto miBDD
        if (miBDD != null)//validando que la base de datos exista no sea nula
        {
            String sql = "INSERT INTO usuario (apellido_usu, nombre_usu, tipo_usu, correo_usu, clave_usu, telefono_usu) " +
                    "VALUES ('" + apellido + "','" + nombre + "','" + tipo + "','" + correo + "','" + clave + "','" + telefono + "');";
            miBDD.execSQL(sql);
            miBDD.close();
            return true;
        }
        return false; //retorno cuando no exista la bdd
    }


    // R E A D
    public Cursor listarUsuarios() {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from usuario ";
        Cursor usuario = miBDD.rawQuery(sql, null);
        if (usuario.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return usuario; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }

    }

    // S E A R C H
    // Buscar usuarios
    public Cursor buscarUsuarios(String busqueda) {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from usuario ";
        Cursor usuario = miBDD.rawQuery(sql, null);
        if (usuario.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return usuario; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }

    }

    public Cursor listarProfesores() {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from usuario " +
                "WHERE tipo_usu = 'profesor' " +
                "ORDER BY apellido_usu ASC ;";
        Cursor profesores = miBDD.rawQuery(sql, null);
        if (profesores.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return profesores; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }
    }

    public Cursor buscarProfesoresNombre(String buscador) {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from usuario " +
                "WHERE tipo_usu = 'profesor' " +
                "AND nombre_usu LIKE '%" + buscador + "%' " +
                "OR apellido_usu LIKE '%" + buscador + "%' " +
                "ORDER BY apellido_usu ASC ;";
        Cursor profesores = miBDD.rawQuery(sql, null);
        if (profesores.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return profesores; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }
    }

    public Cursor iniciarSesionUsuario(String correo, String clave) {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from usuario " +
                "WHERE correo_usu = '" + correo + "' AND clave_usu = '" + clave + "'";
        Cursor usuario = miBDD.rawQuery(sql, null);
        if (usuario.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return usuario; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }

    }

    //PROCESO 4: metodo para consultar por email y password (Login)
    public Cursor obtenerUsuarioporEmailPassword(String email, String password) {
        SQLiteDatabase miBDD = getWritableDatabase(); //Llamando a la base de datos
        Cursor usuario = miBDD.rawQuery("select * from usuario where " +
                "email_usu='" + email + "' and password_usu='" + password + "';", null); //Realizando la consulta y almacenando los resultados en el objeto usuario
        if (usuario.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return usuario; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }

    }

    // U P D A T E
    ///Metodo para actualizar la informacion personal del usuario
    public boolean acualizarCliente(String id, String apellido, String nombre, String correo, String telefono) {
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la base de datos
        String sql = "UPDATE usuario SET " +
                "apellido_usu ='" + apellido + "', " +
                "nombre_usu ='" + nombre + "', " +
                "correo_usu ='" + correo + "', " +
                "telefono_usu ='" + telefono + "' " +
                "WHERE id_usu=" + id;
        if (miBdd != null) {
            miBdd.execSQL(sql);
            miBdd.close(); //cerrando la conexion con la bdd
            return true; //regresando verdadero ya que el proceso d actualizacion fue exitosa
        }
        return false; //retorna falso cuando no existe la bdd
    }

    // Actualizar Clave
    public boolean acualizarClave(String id, String nuevaClave) {
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la base de datos
        String sql = "UPDATE usuario SET " +
                "clave_usu ='" + nuevaClave + "' " +
                "WHERE id_usu=" + id;
        if (miBdd != null) {
            miBdd.execSQL(sql);
            miBdd.close(); //cerrando la conexion con la bdd
            return true; //regresando verdadero ya que el proceso d actualizacion fue exitosa
        }
        return false; //retorna falso cuando no existe la bdd
    }

    // D E L E T E
    //Metodo para eliminar un registro de clientes
    // Usar en caso emergente!!!
    public boolean eliminarCliente(String id) {
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la bdd
        String sql = "DELETE FROM usuario WHERE id_usu=" + id;
        if (miBdd != null) //validando que la bdd exista
        {
            miBdd.execSQL(sql); //ejecutar el query de eliminacion
            miBdd.close(); //cerrando la conexion con la bdd
            return true; //regresando verdadero ya que el proceso d actualizacion fue exitosa
        }
        return false; //retorna falso cuando no existe la bdd
    }


    // G E S T I O N    D E    I N S C R I P C I Ó N
    /*
     * C => Create (agregarInscripcion)
     * R => Read
     * U => Update
     * D => Delete
     * */
    // C R E A T E D
    ///PROCESO 1: Metodo para insertar datos, retorna true cuando inserta y false cuando hay error
    public boolean agregarInscripcion(String codigo, String estado_aprobacion, String estado_progreso, String curso, String alumno) {
        SQLiteDatabase miBDD = getWritableDatabase(); //Llamando a la base de datos en el objeto miBDD
        if (miBDD != null)//validando que la base de datos exista no sea nula
        {
            String sql = "INSERT INTO inscripcion (codigo_unico_ins, estado_aprobacion_ins, estado_progreso_ins, fk_id_cur , fk_id_alu) " +
                    "VALUES ('" + codigo + "','" + estado_aprobacion + "','" + estado_progreso + "','" + curso + "','" + alumno + "')";
            miBDD.execSQL(sql);
            miBDD.close();
            return true;
        }
        return false; //retorno cuando no exista la bdd
    }

    // R E A D
    public Cursor listarInscripcion() {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from inscripcion";
        Cursor inscripcion = miBDD.rawQuery(sql, null);
        if (inscripcion.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return inscripcion; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }

    }

    // U P D A T E
    ///Metodo para actualizar la informacion personal del inscripcion
    public boolean acualizarInscripcion(String id, String codigo, String estado_aprobacion, String estado_progreso) {
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la base de datos
        String sql = "UPDATE inscripcion SET " +
                "codigo_unico_ins ='" + codigo + "', " +
                "estado_aprobacion_ins ='" + estado_aprobacion + "', " +
                "estado_progreso ='" + estado_progreso + "', " +
                "WHERE id_ins=" + id;
        if (miBdd != null) {
            miBdd.execSQL(sql);
            miBdd.close(); //cerrando la conexion con la bdd
            return true; //regresando verdadero ya que el proceso d actualizacion fue exitosa
        }
        return false; //retorna falso cuando no existe la bdd
    }

    // D E L E T E
    //Metodo para eliminar un registro de inscripcion
    // Usar en caso emergente!!!
    public boolean eliminarInscripcion(String id) {
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la bdd
        String sql = "DELETE FROM inscripcion WHERE id_ins=" + id;
        if (miBdd != null) //validando que la bdd exista
        {
            miBdd.execSQL(sql); //ejecutar el query de eliminacion
            miBdd.close(); //cerrando la conexion con la bdd
            return true; //regresando verdadero ya que el proceso d actualizacion fue exitosa
        }
        return false; //retorna falso cuando no existe la bdd
    }


}

