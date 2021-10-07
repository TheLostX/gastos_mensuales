package com.lost.appgastosmensuales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CompararCuentas extends AppCompatActivity {

    private Spinner spAgno, spAgnos2, spMes, spMes2;
    private TextView tv1;

    private Admindb admin;
    private SQLiteDatabase base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparar_cuentas);

        spAgno = findViewById(R.id.spAgnos);
        spMes = findViewById(R.id.spMeses);

        spAgnos2 = findViewById(R.id.spAgnos2);
        spMes2 = findViewById(R.id.spMeses2);

        tv1 = findViewById(R.id.textView7);

        mostrarAgnos();
        mostrarMeses();
    }

    private void mostrarAgnos(){

        admin = new Admindb(this, MainActivity.nombreBaseDatos, null,1);
        base = admin.getReadableDatabase();

        ArrayList<String> arreglo = new ArrayList<>();

        Cursor fila = base.rawQuery("select agno from PagoCuentas group by agno order by agno", null );

        while(fila.moveToNext()){
            arreglo.add(fila.getString(0));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arreglo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAgno.setAdapter(adapter);
        spAgnos2.setAdapter(adapter);
    }

    private void mostrarMeses(){

        admin = new Admindb(this, MainActivity.nombreBaseDatos, null,1);
        base = admin.getReadableDatabase();

        ArrayList<String> arreglo = new ArrayList<>();

        Cursor fila = base.rawQuery("select m.nombre " +
                                        "from Meses as m " +
                                        "inner join PagoCuentas as pc " +
                                        "on m.rowid = pc.mes " +
                                        "group by m.nombre", null );

        while(fila.moveToNext()){
            arreglo.add(fila.getString(0));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arreglo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spMes.setAdapter(adapter);
        spMes2.setAdapter(adapter);

    }

    public void mostrarDiferencia(View view){

        admin = new Admindb(this, MainActivity.nombreBaseDatos, null,1);
        base = admin.getReadableDatabase();

        String agno = spAgno.getSelectedItem().toString();
        String mes = spMes.getSelectedItem().toString();

        String agno2 = spAgnos2.getSelectedItem().toString();
        String mes2 = spMes2.getSelectedItem().toString();

        int suma = 0;
        int suma2 = 0;
        int resta = 0;

        Cursor fila = base.rawQuery("select m.nombre, pc.agno, pc.precio, pc.cuenta, c.nombre " +
                "from PagoCuentas as pc "+
                "inner join Cuentas as c "+
                "ON pc.cuenta = c.rowid "+
                "and agno = " + agno +
                " inner join Meses as m "+
                "On pc.mes = m.rowid", null);

        Cursor fila2 = base.rawQuery("select m.nombre, pc.agno, pc.precio, pc.cuenta, c.nombre " +
                "from PagoCuentas as pc "+
                "inner join Cuentas as c "+
                "ON pc.cuenta = c.rowid "+
                "and agno = " + agno2 +
                " inner join Meses as m "+
                "On pc.mes = m.rowid", null);


        while(fila.moveToNext()){

            if(mes.equals(fila.getString(0))){

                suma += fila.getInt(2);
            }
        }
        while(fila2.moveToNext()){

            if(mes2.equals(fila2.getString(0))){
                suma2+= fila2.getInt(2);
            }
        }

        if(suma>=suma2){

            resta = suma - suma2;
            tv1.setText("Gastaste: " + resta + " pesos mas en el primer mes" );


        }
        else{
            resta = suma2 - suma;
            tv1.setText("Gastaste: " + resta + " pesos mas en el segundo mes" );


        }

    }

    public void volver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}