package com.darsco.tjamich;

import android.provider.BaseColumns;

// db.execSQL("CREATE TABLE usuario(id_usuario INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, nombre TEXT NOT NULL, nombreusuario TEXT NOT NULL, contrasena TEXT NOT NULL, correo TEXT NOT NULL)");

public class DB_sql {
    public static abstract class Login implements BaseColumns
    {
        public static final String TABLE_usuario = "usuariotjam";
        public static final String COLUMN_NAME_usuario = "usuario";
        public static final String COLUMN_NAME_contrasena = "contrasena";
    }

}
