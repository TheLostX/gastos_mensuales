package com.lost.appgastosmensuales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class IngresarCuenta extends AppCompatActivity {

    private Admindb admin;
    private SQLiteDatabase base;

    private Spinner spinner;
    private Spinner spinner2;

    private EditText agno, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar_cuenta);

        agno = findViewById(R.id.editAgno);
        precio = findViewById(R.id.editPrecio);

        spinner = findViewById(R.id.spMes);
        obtenerMes();

        spinner2 = findViewById(R.id.spCuenta);
        obtenerCuenta();
    }

    private void obtenerMes(){

        admin = new Admindb(this, MainActivity.nombreBaseDatos, null, 1);
        base = admin.getReadableDatabase();

        Cursor cursor = base.rawQuery("select rowid, nombre from Meses", null);

        ArrayList<String> arreglo = new ArrayList<>();

        while(cursor.moveToNext()){

            arreglo.add(cursor.getInt(0) + " - " + cursor.getString(1));

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arreglo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }

    private void obtenerCuenta(){

        admin = new Admindb(this, MainActivity.nombreBaseDatos, null, 1);
        base = admin.getReadableDatabase();

        Cursor cursor = base.rawQuery("select rowid, nombre from Cuentas", null);

        ArrayList<String>arreglo = new ArrayList<>();

        while(cursor.moveToNext()){

            arreglo.add(cursor.getInt(0)+ " - " + cursor.getString(1));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arreglo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);

    }

    public void ingresarCuenta(View view) {


        admin = new Admindb(this, MainActivity.nombreBaseDatos, null, 1);
        base = admin.getWritableDatabase();

        ContentValues values = new ContentValues();

        long selected = spinner.getSelectedItemId() + 1;
        long selectedCta = spinner2.getSelectedItemId() + 1;

        if (agno.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe ingresar un año valido", Toast.LENGTH_LONG).show();
        }
        else if(precio.getText().toString().isEmpty()){
            Toast.makeText(this, "Debe ingresar un precio valido", Toast.LENGTH_LONG).show();
        }
        else {
            values.put("mes", selected);
            values.put("agno", agno.getText().toString());
            values.put("precio", precio.getText().toString());
            values.put("cuenta", selectedCta);

            base.insert("PagoCuentas", null, values);
            base.close();

            Toast.makeText(this, "Cuenta ingresada correctamente", Toast.LENGTH_LONG).show();

            spinner.setSelection(0);
            spinner2.setSelection(0);
            agno.setText(null);
            precio.setText(null);
        }


    }

    public void volver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}