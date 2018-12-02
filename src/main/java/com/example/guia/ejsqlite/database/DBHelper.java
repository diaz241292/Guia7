package com.example.guia.ejsqlite.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private String crearCategoria="create table categoria"+
            "("+
            "id_categoria INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre VARCHAR(250)"+
            ")";
    private String crearProducto="create table producto"+
            "("+
            "id_producto INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "nombre VARCHAR(250),"+
            "descripcion TEXT,"+
            "id_categoria INTEGER,"+
            "foreign key (id_categoria) references categoria (id_categoria)" +
            ")";

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //primero las tablas Maestro (sin foreign key)
        sqLiteDatabase.execSQL(crearCategoria);
        sqLiteDatabase.execSQL(crearProducto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //primero borrar las Maestro-Detalle
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS producto");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS categoria");
    }
}
