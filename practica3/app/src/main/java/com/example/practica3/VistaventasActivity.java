package com.example.practica3;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class VistaventasActivity extends AppCompatActivity {
    ListView lstVentas;
    ArrayList<String> listaVentas=new ArrayList<>();
    Cursor ventasObtenidos;// declaracion de variable de forma global
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vistaventas);
        mapeado();
        consultarDatos();
    }

    public void mapeado(){
        lstVentas=(ListView) findViewById(R.id.lstVentas);
        bdd= new BaseDatos(VistaventasActivity.this);//Instanciando el objeto para llmar a los metodos de la BDD
    }

    //metodo para consultar metodos existentes SQLite y presentarlos en la vista
    public void consultarDatos(){
        listaVentas.clear(); //vaciando el listado de clientes
        ventasObtenidos=bdd.obtenerVenta();//consultando clientes y guardandolos en un cursor
        if (ventasObtenidos!=null){ // verificando que realmentehaya datos dentro de SQLite
            //proceso cuando si se encuentra clientes almacenados en la BDD
            do{
                String id=ventasObtenidos.getString(0).toString();
                String CLiente=ventasObtenidos.getString(1).toString();
                String Producto=ventasObtenidos.getString(2).toString();
                float precio = ventasObtenidos.getFloat(4);
                String precioString = String.valueOf(precio);
                //construir las filas para presentar datos en el listView
                listaVentas.add("Venta Nro: "+id+" Cliente: "+CLiente+" Producto: "+Producto+" Total: "+precioString);
                ArrayAdapter<String> adaptadorClientes=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaVentas);
                lstVentas.setAdapter(adaptadorClientes);
            }while (ventasObtenidos.moveToNext()); //validando si aun existe clientes dentro del cursor
        }else{
            Toast.makeText(getApplicationContext(),"No existe ventas registrados",Toast.LENGTH_LONG).show();
        }
    }
}