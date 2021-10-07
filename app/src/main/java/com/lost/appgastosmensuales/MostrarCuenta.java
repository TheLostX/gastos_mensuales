package com.lost.appgastosmensuales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MostrarCuenta extends AppCompatActivity {

    private Admindb admin;
    private SQLiteDatabase base;
    private Spinner spAgno, spMes;

    private ListView lv1;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cuenta);

        spAgno = findViewById(R.id.spAgno);
        spMes = findViewById(R.id.spMes2);

        lv1 = findViewById(R.id.lista);
        tv1 = findViewById(R.id.textView);

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
    }

    public void mostrarElementos(View view){

        admin = new Admindb(this, MainActivity.nombreBaseDatos, null,1);
        base = admin.getReadableDatabase();

        String agno = spAgno.getSelectedItem().toString();
        String mes = spMes.getSelectedItem().toString();

        int suma = 0;

        Cursor fila = base.rawQuery("select m.nombre, pc.agno, pc.precio, pc.cuenta, c.nombre " +
                "from PagoCuentas as pc "+
                "inner join Cuentas as c "+
                "ON pc.cuenta = c.rowid "+
                "and agno = " + agno +
                " inner join Meses as m "+
                "On pc.mes = m.rowid", null);

        ArrayList<String> arreglo = new ArrayList<>();

        while(fila.moveToNext()){

            if(mes.equals(fila.getString(0))){

                arreglo.add(fila.getString(4) + " | " + fila.getString(0) + " | " + fila.getInt(2));
                suma += fila.getInt(2);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_charles, arreglo);

        lv1.setAdapter(adapter);


        tv1.setText("El total del mes es: " + suma);


    }

    public void volver(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}