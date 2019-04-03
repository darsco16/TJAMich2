package com.darsco.tjamich;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;


import java.util.ArrayList;

/**
 * Created by lanies on 22/01/18.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "TJAM.db";
    private static final int  VERSION_ACTUAL = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION_ACTUAL);
        context = context;
    }


    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
        if(!db.isReadOnly()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                db.setForeignKeyConstraintsEnabled(true);
            }else{
                db.execSQL("PRAGMA foreign_keys = ON;");
            }

        }

    }
    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;
        try{
            checkDB = SQLiteDatabase.openDatabase(DATABASE_NAME, null, SQLiteDatabase.OPEN_READONLY);
            checkDB.close();

        }catch(SQLiteException e){
            System.out.print("NO SE CREO LA BASE DE DATOS" + e);
        }
        return checkDB != null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //CREAR LAS TABLAS DE:
        db.execSQL("CREATE TABLE usuariotjam(id_usuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, usuario TEXT NOT NULL, contrasena TEXT NOT NULL)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "usuariotjam");

        onCreate(db);
    }

    //INICIO INSERT
    public void insertUsuario(String usuario, String contrasena){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("usuario", usuario);
        valores.put("contrasena", contrasena);
        db.insert("usuariotjam", null, valores);
    }


    public void abrir(){//permite abrir a bd
        this.getWritableDatabase();
    }

    public void cerrar(){//permite cerrar la bd
        this.close();
    }

    //Consultar usuario en la base
    public Cursor consultarUsuario(String u, String c) throws SQLException{
        Cursor cursor = null;
        cursor = this.getReadableDatabase().query("usuario", new String[]{"id_usuario",
                        "nombre","nombreUsuario","contrasena","correo"},
                "nombreUsuario like '"+u+"' and contrasena like '"+c+"'",
                null, null, null, null );
        return cursor;
    }

    public Cursor consultarContrasena(String u, String c) throws SQLException{
        SQLiteDatabase db = getReadableDatabase();
        Cursor res =  db.rawQuery( "select contrasena from usuariotjam where id_usuario='"+u+"' and contrasena='"+c+"'", null );
        return res;
    }
    //Obtener id de usuario
    public Cursor getId(String usu, String pass) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor res =  db.rawQuery( "select id_usuario from usuariotjam where nombre='"+usu+"' and contrasena='"+pass+"'", null );
        return res;
    }



    public ArrayList listaJoins(String idUs){
        ArrayList<String> lista = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "SELECT predio.id_predio, predio.nombre_predio, predio.direccion, predio.fecha, diagnostico.id_diagnostico, diagnostico.promedio, diagnostico.nivel"
                +" from predio"
                +" INNER JOIN diagnostico"
                +" ON predio.id_predio = diagnostico.id_predio"
                +" where predio.id_usuario = '"+idUs+"'";
        Cursor registros = database.rawQuery(sql, null);

        if(registros.moveToNext()){
            do{
                lista.add("Id Predio: "+registros.getString(0) + "\n"+ "Nombre del predio: "+registros.getString(1)
                        + "\n" + "Dirección: "+ registros.getString(2)+ "\n" + "Fecha: "+ registros.getString(3)
                        + "\n" + "Id diagnóstico: "+ registros.getString(4)+ "\n" + "Promedio: "+ registros.getString(5)+"\n" + "Nivel: "+  registros.getString(6));
            }while(registros.moveToNext());

        }
        return lista;
    }

    public int actualizarDatosUsuario(String id, String usuario, String contra, String correo){
        int filasAfectadas = 0;
        SQLiteDatabase db = getWritableDatabase();
        if(db !=null){
            ContentValues values = new ContentValues();
            values.put("nombreusuario", usuario);
            values.put("contrasena", contra);
            values.put("correo" , correo);
            filasAfectadas = (int)db.update("usuario", values, "id_usuario = '"+id+"'", null);
        }
        db.close();
        return  filasAfectadas;
    }


    public Cursor getData(String idUsusuario){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT predio.id_predio, predio.nombre_predio, predio.direccion, predio.fecha, diagnostico.id_diagnostico, diagnostico.promedio, diagnostico.nivel"
                +" from predio"
                +" INNER JOIN diagnostico"
                +" ON predio.id_predio = diagnostico.id_predio"
                +" where predio.id_usuario = '"+idUsusuario+"'";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getUsersData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT usuario.id_usuario, usuario.nombre, usuario.nombreusuario, usuario.correo, " +
                "predio.id_predio, predio.nombre_predio, predio.ubicacion, predio.fecha, " +
                "diagnostico.id_diagnostico, diagnostico.id_diagnostico, diagnostico.promedio, diagnostico.nivel"
                +" from usuario"
                +" INNER JOIN predio"
                +" ON usuario.id_usuario = predio.id_predio"
                +" INNER JOIN  diagnostico"
                +" ON predio.id_predio = diagnostico.id_predio";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor getGuardados(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM predio ORDER BY id_predio DESC LIMIT 1", null );


        return res;

    }
    public Cursor getGuardados2(String usuario){
        SQLiteDatabase db = getReadableDatabase();
        //Cursor res =  db.rawQuery( "SELECT * FROM predio ORDER BY id_predio DESC ", null );
        Cursor res =  db.rawQuery( "SELECT * FROM predio where id_usuario=(select id_usuario From usuario where nombreUsuario='"+usuario+"')", null );



        return res;

    }



    public Cursor prueba(String idUs){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT predio.id_predio, predio.nombre_predio, predio.direccion, predio.fecha, diagnostico.id_diagnostico, diagnostico.promedio, diagnostico.nivel"
                +" from predio"
                +" INNER JOIN diagnostico"
                +" ON predio.id_predio = diagnostico.id_predio"
                +" where predio.id_usuario = '"+idUs+"'";
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }


}