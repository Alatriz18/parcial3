package com.example.practica3;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class ProductoActivity extends AppCompatActivity {
    //Entrada
    EditText txtNombreRegistroProduct, txtPrecioRegistroProduct, txtStockRegistroProduct;
    RadioButton txtIvaRegistroProducto;
    BaseDatos Bdd;
    TextView txtCalendar;
    int error=0;
    int ivaP1=0;
    //Salida
    ListView lstProductos;
    ArrayList<String> listaProductos = new ArrayList<>();
    public Cursor productosObtenidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        txtNombreRegistroProduct=(EditText) findViewById(R.id.txtNombreRegistroProduct);
        txtPrecioRegistroProduct=(EditText) findViewById(R.id.txtPrecioRegistroProduct1);
        txtStockRegistroProduct=(EditText) findViewById(R.id.txtStockRegistroProduct1);
        txtIvaRegistroProducto=(RadioButton) findViewById(R.id.txtIvaRegistroProducto1);
        txtCalendar=(TextView) findViewById(R.id.txtCalendar1);
        lstProductos=(ListView) findViewById(R.id.lstProductos);


        Bdd = new BaseDatos(getApplicationContext());
        consultarDatos();
        lstProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               productosObtenidos.moveToPosition(position);
                String idProducto=productosObtenidos.getString( 0);
                String nombreProducto=productosObtenidos.getString( 1);
                String precioProducto=productosObtenidos.getString( 2);
                String cantidadProducto=productosObtenidos.getString( 3);
                String ivaProducto=productosObtenidos.getString( 4);
                String fechaProducto=productosObtenidos.getString( 5);
                //Toast.makeText(getApplicationContext(),idCliente+"-"+cedulaCliente+"-"+apellidoCliente+"-"+
                //nombreCliente+"-"+telefonoCliente+"-"+direccionCliente,Toast.LENGTH_LONG).show();
                Intent ventanaEditarProducto= new Intent(getApplicationContext(),EditarProducto.class);
                ventanaEditarProducto.putExtra("id",idProducto);
                ventanaEditarProducto.putExtra("nombre",nombreProducto);
                ventanaEditarProducto.putExtra("precio",precioProducto);
                ventanaEditarProducto.putExtra("cantidad",cantidadProducto);
                ventanaEditarProducto.putExtra("iva",ivaProducto);
                ventanaEditarProducto.putExtra("fecha",fechaProducto);
                startActivity(ventanaEditarProducto);
                finish();
            }
        });

    }

    public void LimpiarCampos(View vista){
        txtNombreRegistroProduct.setText("");
        txtPrecioRegistroProduct.setText("");
        txtStockRegistroProduct.setText("");
        txtIvaRegistroProducto.setText("");//CTRL + ALT + L---> PARA LIMPIAR EL CODIGO
        txtNombreRegistroProduct.requestFocus();
    }


    public void guardarProducto(View vista){
        //capturados los valores ingresados por el usuario en el formulario de Registro de Clientes
        String nombreP= txtNombreRegistroProduct.getText().toString();
        String precioP= txtPrecioRegistroProduct.getText().toString();
        String cantidadP= txtStockRegistroProduct.getText().toString();
        String ivaP = txtIvaRegistroProducto.getText().toString();
        String fechaP= txtCalendar.getText().toString();

        /*ivaP = String.valueOf(0);
        if (txtIvaRegistroProducto.isChecked()) {
            ivaP = String.valueOf(1);
        }*/


        if (!nombreP.equals("") && !precioP.equals("") && !cantidadP.equals("") && !fechaP.equals("")){
            if (Double.parseDouble(precioP) > 0) {
             if (Double.parseDouble(cantidadP) > 0) {

            Bdd.AgregarProducto(nombreP, precioP, cantidadP, ivaP, fechaP);
            //Limpiar(null);
            Toast.makeText(getApplicationContext(),"Productos Registrado Exitosamente",
                    Toast.LENGTH_LONG).show();
        }else{
                    txtStockRegistroProduct.setError("Stock Debe ser mayor a 0");
                    txtStockRegistroProduct.requestFocus();}
            }else{
                txtPrecioRegistroProduct.setError("precio Debe ser mayor a 0");
                txtPrecioRegistroProduct.requestFocus();}
        }else{
            Toast.makeText(getApplicationContext(),"Para guardar complete todos los campos del formulario",
                    Toast.LENGTH_LONG).show();
        }
        consultarDatos();

    }
    public void consultarDatos(){
        listaProductos.clear();
        productosObtenidos=Bdd.obtenerProductos();//consultando clientes y guardandolos en un cursor
        if (productosObtenidos!=null){
            do {
                String id=productosObtenidos.getString(0).toString();
                String nombreP=productosObtenidos.getString(1).toString();
                String precioP=productosObtenidos.getString(2).toString();
                listaProductos.add(id+": "+nombreP+" "+precioP);
                ArrayAdapter<String> adaptadorProductos= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaProductos);
                lstProductos.setAdapter(adaptadorProductos);
            }while (productosObtenidos.moveToNext());
        }else {
            Toast.makeText(getApplicationContext(),"No Existen clientes registrados",
                    Toast.LENGTH_LONG).show();
        }

    }


    public void abriCalendario(View view) {
        //declaramos una variable tipo calendar
        Calendar calendar = Calendar.getInstance();
        //declaramos las variables para el a?o, mes, dia para almacenarlos..
        int anio = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        //funcion que nos permite visualizar en el textview la fecha seleccionada en el calendario
        DatePickerDialog dpd = new DatePickerDialog(ProductoActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //variable que contiene la fecha concatenada
                String fecha = dayOfMonth + "/" + month + "/" + year;
                //asignamos a nuestro txt calendar nuestra fecha seleccionada en el calendario
                txtCalendar.setText(fecha);
            }
        }, anio, mes, dia);
        dpd.show();
    }

}


