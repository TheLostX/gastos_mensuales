package com.lost.appgastosmensuales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Admindb admin;
    private SQLiteDatabase base;
    public static final String nombreBaseDatos = "PagoCuentas3.5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        admin = new Admindb(this, nombreBaseDatos, null, 1);
        base = admin.getWritableDatabase();

        base.delete("Meses","", null);
        rellenoMeses(base);

        base.delete("Cuentas", "", null);
        rellenoCuentas(base);

        base.close();
    }


    private void rellenoMeses(SQLiteDatabase base){

        ContentValues values = new ContentValues();

        values.put("nombre", "Enero");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Febrero");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Marzo");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Abril");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Mayo");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Junio");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Julio");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Agosto");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Septiembre");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Octubre");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Noviembre");

        base.insert("Meses", null, values);
        values.clear();

        values.put("nombre", "Diciembre");

        base.insert("Meses", null, values);
        values.clear();

    }

    private void rellenoCuentas(SQLiteDatabase base){

        ContentValues values = new ContentValues();

        values.put("nombre", "Cuenta de Luz");
        base.insert("Cuentas", null, values);
        values.clear();

        values.put("nombre", "Cuenta de Agua");
        base.insert("Cuentas", null, values);
        values.clear();

        values.put("nombre", "Cuenta de Gas");
        base.insert("Cuentas", null, values);
        values.clear();

        values.put("nombre", "Cuenta de Internet");
        base.insert("Cuentas", null, values);
        values.clear();
    }

    public void ingresarCuenta(View view){
        Intent intent = new Intent(this, IngresarCuenta.class);
        startActivity(intent);
    }

    public void verCuenta(View view){

        admin = new Admindb(this, nombreBaseDatos, null, 1);
        base = admin.getReadableDatabase();

        Cursor fila = base.rawQuery("select * from PagoCuentas", null);

        if(fila.moveToFirst()){
            Intent intent = new Intent(this, MostrarCuenta.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No existen registros", Toast.LENGTH_LONG).show();
        }



    }

    public void compararCuenta(View view){
        admin = new Admindb(this, nombreBaseDatos, null, 1);
        base = admin.getReadableDatabase();

        Cursor fila = base.rawQuery("select * from PagoCuentas", null);

        if(fila.moveToFirst()){
            Intent intent = new Intent(this, CompararCuentas.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "No existen registros", Toast.LENGTH_LONG).show();
        }

    }
}