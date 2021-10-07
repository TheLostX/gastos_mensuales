package com.lost.appgastosmensuales;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Admindb extends SQLiteOpenHelper {

    public Admindb(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table Meses(" +
                "nombre varchar)");

        db.execSQL("create table Cuentas(" +
                "nombre varchar)");

        db.execSQL("create table PagoCuentas(" +
                "mes int," +
                "agno int," +
                "precio int," +
                "cuenta int," +
                "foreign key (cuenta) references Cuentas(rowid)," +
                "foreign key (mes) references Meses(rowid))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
