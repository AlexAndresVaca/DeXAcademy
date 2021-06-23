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

    private static final String tablaCursos = "CREATE TABLE curso(" +
            "id_cur INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nombre_cur TEXT, " +
            "detalle_cur TEXT, " +
            "f_ini_cur DATE, " +
            "f_fin_cur DATE, " +
            "num_horas INTEGER, " +
            "url_cur INTEGER, " +
            "fk_id_usu INTEGER, " +
            "FOREIGN KEY (fk_id_usu) REFERENCES usuario (id_usu)" +
            ");";
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

    private static final String usuProf = "INSERT INTO usuario (apellido_usu, nombre_usu, tipo_usu, correo_usu, clave_usu, telefono_usu) " +
            "VALUES ('flores','hugo','profesor','hugo@gmail.com','hugo1234','0999999999');";

    private static final String usuAlu = "INSERT INTO usuario (apellido_usu, nombre_usu, tipo_usu, correo_usu, clave_usu, telefono_usu) " +
            "VALUES ('cabas','luis','estudiante','luis@gmail.com','luis1234','0999999999');";

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
        db.execSQL(usuProf);
        db.execSQL(usuAlu);
        db.execSQL(tablaCursos);
        //ejecutando el query ddl para crear la tabla usuario con sus atributos
        // db.execSQL(tablaInscripcion);

    }

    //PROCESO 2: Metodo que se ejecuta automaticamente cuando se detectan cambios en la versino con sus atributos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminacion de la version anterior de la tabla inscripcion
        db.execSQL("DROP TABLE IF EXISTS inscripcion");
        // Eliminacion de la version anterior de la tabla inscripcion
        db.execSQL("DROP TABLE IF EXISTS curso");
        // Eliminacion de la version anterior de la tabla usuario
        db.execSQL("DROP TABLE IF EXISTS usuario");
        //Ejecucion del codigo para crear la tabla usuario con su nueva estructura
        db.execSQL(tablaUsuario);
        db.execSQL(tablaCursos);
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
    // S E A R C H
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

    public Cursor buscarProfesoresId(String idProfesor) {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from usuario " +
                "WHERE tipo_usu = 'profesor' " +
                "AND id_usu = '" + idProfesor + "' " +
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

    public Cursor buscarEstudianteId(String idEstudiante) {
        SQLiteDatabase miBDD = getReadableDatabase(); //Llamando a la base de datos
        String sql = "select * from usuario " +
                "WHERE tipo_usu = 'estudiante' " +
                "AND id_usu = '" + idEstudiante + "' " +
                "ORDER BY apellido_usu ASC ;";
        Cursor profesores = miBDD.rawQuery(sql, null);
        if (profesores.moveToFirst()) {//verificando que el objeto usuario tenga resultados
            return profesores; //retornamos datos encontrados
        } else {
            //Nose encuentra el usuario ..Porque no eexiste el email y congtrase{a
            return null;
        }
    }

    public boolean actualizarUsuario(String id, String apellido, String nombre, String correo, String telefono) {
        SQLiteDatabase miBDD = getWritableDatabase();
        if (miBDD != null) {
            String sql = "UPDATE usuario SET " +
                    "apellido_usu = '" + apellido + "', " +
                    "nombre_usu = '" + nombre + "', " +
                    "correo_usu = '" + correo + "', " +
                    "telefono_usu = '" + telefono + "' " +
                    "WHERE id_usu = " + id + ";";
            miBDD.execSQL(sql);
            miBDD.close();
            return true;
        }
        return false;
    }

    //PROCESO 4: metodo para consultar por email y password (Login)
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


    // U P D A T E
    ///Metodo para actualizar la informacion personal del usuario
    public boolean acualizarEstudiante(String id, String apellido, String nombre, String correo, String telefono) {
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

    // G E S T I O N    D E    C U R S O
    /*
     * C => Create (agregarCurso)
     * R => Read
     * U => Update
     * D => Delete
     * */
    public boolean agregarCurso(String nombre, String detalle, String f_ini, String f_fin, String duracion, String url, String idProfesor) {
        SQLiteDatabase miBDD = getWritableDatabase(); //Llamando a la base de datos en el objeto miBDD
        if (miBDD != null)//validando que la base de datos exista no sea nula
        {
            String sql = "INSERT INTO curso (nombre_cur, detalle_cur, f_ini_cur, f_fin_cur, num_horas,url_cur,fk_id_usu) " +
                    "VALUES ('" + nombre + "','" + detalle + "','" + f_ini + "','" + f_fin + "','" + duracion + "','" + url + "','" + idProfesor + "');";
            miBDD.execSQL(sql);
            miBDD.close();
            return true;
        }
        return false; //retorno cuando no exista la bdd
    }

    public Cursor buscarCursoPorId(String id) {
        SQLiteDatabase miBDD = getReadableDatabase();
        String sql = "SELECT * FROM curso " +
                "WHERE id_cur = '" + id + "' " +
                "ORDER BY nombre_cur ASC;";
        if (miBDD != null) {
            Cursor cursos = miBDD.rawQuery(sql, null);
            if (cursos.moveToFirst()) {
                return cursos;
            }
        }
        return null;
    }

    public Cursor buscarCursosDelProfesor(String idProfesor) {
        SQLiteDatabase miBDD = getReadableDatabase();
        String sql = "SELECT * FROM curso " +
                "WHERE fk_id_usu = '" + idProfesor + "' " +
                "ORDER BY nombre_cur ASC;";
        if (miBDD != null) {
            Cursor cursos = miBDD.rawQuery(sql, null);
            if (cursos.moveToFirst()) {
                return cursos;
            }
        }
        return null;
    }

    public Cursor buscarCursosDelProfesorPorNombre(String idProfesor, String buscar) {
        SQLiteDatabase miBDD = getReadableDatabase();
        String sql = "SELECT * FROM curso " +
                "WHERE fk_id_usu = '" + idProfesor + "'" +
                "AND nombre_cur LIKE '%" + buscar + "%'" +
                "ORDER BY nombre_cur ASC;";
        if (miBDD != null) {
            Cursor cursos = miBDD.rawQuery(sql, null);
            if (cursos.moveToFirst()) {
                return cursos;
            }
        }
        return null;
    }

    public boolean editarCurso(String idCursoVer, String nombre, String detalle, String f_ini, String f_fin, String horas, String url) {
        SQLiteDatabase miBdd = getWritableDatabase(); //objeto para manejar la base de datos
        String sql = "UPDATE curso SET " +
                "nombre_cur ='" + nombre + "', " +
                "detalle_cur ='" + detalle + "', " +
                "f_ini_cur ='" + f_ini + "', " +
                "f_fin_cur ='" + f_fin + "', " +
                "num_horas ='" + horas + "', " +
                "url_cur ='" + url + "' " +
                "WHERE id_cur='" + idCursoVer + "';";
        if (miBdd != null) {
            miBdd.execSQL(sql);
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

