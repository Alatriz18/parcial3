package com.example.practica3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VentaActivity extends AppCompatActivity { //implements AdapterView.OnItemSelectedListener
    //variables de entrada
    Spinner listaCli, listaPro;
    TextView clienteVentas, productoVentas, precioVentas;
    EditText cantidadVentas;
    BaseDatos Bdd;
    Cursor clientesObtenidos;// declaracion de variable de forma global
    Cursor productosObtenidos;// declaracion de variable de forma global

    ArrayList<String> listac=new ArrayList<>();
    ArrayList<String> listap=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);

        Bdd = new BaseDatos(VentaActivity.this);
        listaCli =(Spinner) findViewById(R.id.listaCli);
        listaPro =(Spinner) findViewById(R.id.listaPro);

        clienteVentas =(TextView) findViewById(R.id.clienteVentas);
        productoVentas =(TextView) findViewById(R.id.productoVentas);
        cantidadVentas =(EditText) findViewById(R.id.cantidadVentas);
        precioVentas =(TextView) findViewById(R.id.precioVentas);

        obtenerDatosClientes();
        obternerDatosProductos();

    }

    public void obtenerDatosClientes() {
        //accedo a la base de datos
        listac.clear(); //vaciando el listado de clientes
        listac.add("Seleccione cliente: ");
        clientesObtenidos=Bdd.obtenerClientes();//consultando clientes y guardandolos en un cursor

        if (clientesObtenidos!=null){ // verificando que realmente haya datos dentro de SQLite
            //proceso cuando si se encuentra clientes almacenados en la BDD
            do{
                String id=clientesObtenidos.getString(0).toString();
                String cedula=clientesObtenidos.getString(1).toString();
                String nombre=clientesObtenidos.getString(2).toString();
                String apellido = clientesObtenidos.getString(3);
                //construir las filas para presentar datos en el spinner
                listac.add(""+id+".- " +nombre.toUpperCase()+" "+apellido.toUpperCase());// lista de clientes
                ArrayAdapter<CharSequence> adapterSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listac);
                listaCli.setAdapter(adapterSpinner);

            }while (clientesObtenidos.moveToNext()); //validando si aun existe clientes dentro del cursor
        }
    }

    //metodo para obtener productos en Spinner
    private void obternerDatosProductos() {
        //accedo a la base de datos
        listap.clear(); //vaciando el listado de clientes
        listap.add("Seleccione");
        productosObtenidos=Bdd.obtenerProductos();//consultando clientes y guardandolos en un cursor

        if (productosObtenidos!=null){ // verificando que realmente haya datos dentro de SQLite
            //proceso cuando si se encuentra clientes almacenados en la BDD
            do{
                String id=productosObtenidos.getString(0).toString();
                String nombre=productosObtenidos.getString(1).toString();
                String precio=productosObtenidos.getString(2).toString();
                //String apellido =productosObtenidos.getString(3);
                //construir las filas para presentar datos en el spinner
                listap.add(""+id+".- " +nombre.toUpperCase());// lista de productos
                ArrayAdapter<CharSequence> adapterSpinner = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listap);
                listaPro.setAdapter(adapterSpinner);

            }while (productosObtenidos.moveToNext()); //validando si aun existe clientes dentro del cursor
        }

    }

    /*listaCli.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            Spinner listaCli=(Spinner) findViewById(R.id.listaCli); String valueinString = listaCli.getSelectedItem().toString();
                            //""Spinner spinner=(Spinner) findViewById(R.id.spinnername); String valueinString = spinner.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
*/
}