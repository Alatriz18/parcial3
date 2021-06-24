package com.example.practica3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VentaActivity extends AppCompatActivity { //implements AdapterView.OnItemSelectedListener
    //variables de entrada
    Spinner listaCli, listaPro;
    TextView clienteVentas, productoVentas, precioVentas;
    EditText cantidadVentas;
    BaseDatos Bdd;
    Cursor clientesObtenidos;// declaracion de variable de forma global
    Cursor productosObtenidos;// declaracion de variable de forma global
    String nombreProducto;
    float precioProducto;
    float precio;
    ArrayList<String> listac=new ArrayList<>();
    ArrayList<String> listap=new ArrayList<>();
    Producto producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);


        listaCli =(Spinner) findViewById(R.id.listaCli);
        listaPro =(Spinner) findViewById(R.id.listaPro);

        cantidadVentas =(EditText) findViewById(R.id.cantidadVentas);
        precioVentas =(TextView) findViewById(R.id.precioVentas);
        Bdd = new BaseDatos(VentaActivity.this);
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
                String id=productosObtenidos.getString(0);
                String nombre=productosObtenidos.getString(0);
                float precio = productosObtenidos.getFloat(2);
                String precioString = String.valueOf(precio);
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

    public void GuardarVentas(View view) {
        String nombreClienteSeleccionado = listaCli.getSelectedItem().toString();
        String nombreProductoSeleccionado = listaPro.getSelectedItem().toString();
        String cantidad = cantidadVentas.getText().toString();
        // con la funcion procedo a recorrer el string verifico los numeros que haya y si son consecutivos le formo en un solo numero
        List<Integer> numerosExtraidos = extraerNumeros(nombreProductoSeleccionado);
        String numerosExtraidosString = String.valueOf(numerosExtraidos.get(0));
        int id = convertirInt(numerosExtraidosString);

        //hago una busqueda del producto atraves del id
        producto = Bdd.verProductId(id);
        if (producto != null) {
            precio = producto.getPrecio();
            nombreProducto = producto.getNombre();
        }
        //Calculamos el total
        int cantidadConvertida = convertirInt(cantidad);
        float total = precio * cantidadConvertida;
        String precioConvertido = String.valueOf(total);

        //Operacion total
        //Toast.makeText(this,""+precioConvertido,Toast.LENGTH_SHORT).show();

        if (cantidad.isEmpty()) {
            Toast.makeText(VentaActivity.this, "El campo cantidad debe tener como minimo 1", Toast.LENGTH_LONG).show();
        } else {

            if (nombreClienteSeleccionado.equals("Seleccione")) {
                Toast.makeText(VentaActivity.this, "Debe elegir un CLiente", Toast.LENGTH_LONG).show();

            } else {
                if (nombreProductoSeleccionado.equals("Seleccione")) {
                    Toast.makeText(VentaActivity.this, "Debe elegir un Producto", Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder estructuraConfirmacion = new AlertDialog.Builder(VentaActivity.this)
                            .setTitle("Mensaje de Confirmación")
                            .setMessage("Producto: "+ nombreProducto+"  "+"Total: "+precioConvertido)
                            .setPositiveButton("Si, Comprar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Boolean checkinsertdata = Bdd.agregarVenta(nombreClienteSeleccionado,nombreProducto,cantidadConvertida,total);
                                    if (checkinsertdata == true) {
                                        Toast.makeText(VentaActivity.this, "Venta Realizada", Toast.LENGTH_SHORT).show();
                                        Intent compras = new Intent(VentaActivity.this,VistaventasActivity.class);
                                        startActivity(compras);
                                    } else {
                                        Toast.makeText(VentaActivity.this, "No se ha podido crear la venta", Toast.LENGTH_SHORT).show();
                                    }
                                    Toast.makeText(VentaActivity.this,"Compra realizada con exito..",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent= new Intent(VentaActivity.this,MenuActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }).setCancelable(true);
                    AlertDialog cuadroDialog = estructuraConfirmacion.create();
                    cuadroDialog.show();
                }
            }
        }


    }


    //FUNCION PARA CONVERTIR A FLOTANTES
    public float convertirFloat(String s) {
        float f = 0; //DECLARAMOS UNA VARIABLE TIPO FLOAT LA CUAL ALMACENARA EL VALOR CONVERTIDO
        try {
            //convertimos el valor a flotante
            f = Float.valueOf(s.trim()).floatValue();
        } catch (NumberFormatException nfe) {
            Log.d("ERROR","NO SE HA PODIDO CONVERTIR EL NUMERO");
        }
        return f;
    }

    //FUNCION PARA CONVERTIR A ENTEROS
    public int convertirInt(String s) {
        int i = 0; //declaramos una variable la cual almacena el tipo int convertido
        try {
            // el string convertimos a entero
            i = Integer.parseInt(s.trim());

        } catch (NumberFormatException nfe) {
            Log.d("ERROR","NO SE HA PODIDO CONVERTIR EL NUMERO");
        }
        return i;
    }

    List<Integer> extraerNumeros(String cadena) {
        List<Integer> todosLosNumeros = new ArrayList<Integer>();
        Matcher encuentrador = Pattern.compile("\\d+").matcher(cadena);
        while (encuentrador.find()) {
            todosLosNumeros.add(Integer.parseInt(encuentrador.group()));
        }
        return todosLosNumeros;
    }

    public void abrirVentasRealizadas(View view) {
        Intent intent = new Intent(VentaActivity.this,VistaventasActivity.class);
        startActivity(intent);
    }
}
