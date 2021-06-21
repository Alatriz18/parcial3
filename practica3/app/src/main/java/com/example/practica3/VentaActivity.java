package com.example.practica3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class VentaActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //variables de entrada
    Spinner listaCli, listaPro;
    ArrayList <String>listaClient1;
    ArrayList<ClientDatos>listaClient2;
    TextView clienteVentas, productoVentas, precioVentas;
    BaseDatos Bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        Bdd = new BaseDatos(getApplicationContext());
        listaCli =(Spinner) findViewById(R.id.listaCli);
        listaPro =(Spinner) findViewById(R.id.listaPro);


        clienteVentas =(TextView) findViewById(R.id.clienteVentas);
        productoVentas =(TextView) findViewById(R.id.productoVentas);
        precioVentas =(TextView) findViewById(R.id.precioVentas);

        consultarListaClient1();
    }

    private void consultarListaClient1() {
        SQLiteDatabase db=Bdd.getReadableDatabase();

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}