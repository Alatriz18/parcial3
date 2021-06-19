package com.example.practica3;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class EditarProducto extends AppCompatActivity {
    String id, nombre, precio, cantidad,iva, fecha;
    TextView txtidProductoEditar, txtCalendar1;
    EditText txtNombreRegistroProduct1, txtPrecioRegistroProduct1, txtStockRegistroProduct1, txtIvaRegistroProducto1;
    BaseDatos Bdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_producto);
        //Mapeo de elementos XML a objetos Java
        txtidProductoEditar=(TextView) findViewById(R.id.txtidProductoEditar);
        txtNombreRegistroProduct1= findViewById(R.id.txtNombreRegistroProduct1);
        txtPrecioRegistroProduct1= findViewById(R.id.txtPrecioRegistroProduct1);
        txtStockRegistroProduct1= findViewById(R.id.txtStockRegistroProduct1);
        txtIvaRegistroProducto1= findViewById(R.id.txtIvaRegistroProducto1);
        txtCalendar1= findViewById(R.id.txtCalendar1);
        Bundle parametrosExtra=getIntent().getExtras();//capturando los parametros que se han pasado a esta actividad
        if (parametrosExtra!=null){
            try{
                //Intente realizar estas lineas de codigo
                id=parametrosExtra.getString("id");
                nombre=parametrosExtra.getString("nombre");
                precio=parametrosExtra.getString("precio");
                cantidad=parametrosExtra.getString("cantidad");
                iva=parametrosExtra.getString("iva");
                fecha=parametrosExtra.getString("fecha");
            }catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud",Toast.LENGTH_SHORT).show();
            }
        }
        txtidProductoEditar.setText(id);
        txtNombreRegistroProduct1.setText(nombre);
        txtPrecioRegistroProduct1.setText(precio);
        txtStockRegistroProduct1.setText(cantidad);
        txtIvaRegistroProducto1.setText(iva);
        txtCalendar1.setText(fecha);

        Bdd = new BaseDatos(getApplicationContext());//Instanciando el objeto a traves del
    }

    //Metodo para volver
    public void volver(View vista){
        finish();//cerrar la ventana
        //creando un objeto para manejar la ventana de gestion de clientes
        Intent ventanaGestionProductos= new Intent(getApplicationContext(),ProductoActivity.class);
        startActivity(ventanaGestionProductos);
    }

    public void eliminarProducto(View vista){
        AlertDialog.Builder estructuraConfirmacion= new AlertDialog.Builder(this).setTitle("CONFIRMACIÓN")
                .setMessage("Esta seguro de Eliminar el producto seleccionado?")
                .setPositiveButton("Si, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bdd.eliminarProducto(id);//Procesando la eliminacion con base del id del producto
                        Toast.makeText(getApplicationContext(),"Eliminacion Exitosa", Toast.LENGTH_SHORT).show();
                        volver(null);//invoncando el metodo volver
                    }
                })
                .setNegativeButton("No, Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Eliminacion Cancelada", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        AlertDialog cuadroDialogo=estructuraConfirmacion.create();
        cuadroDialogo.show();
    }

    public void actualizarProducto(View vista){

        String nombre= txtNombreRegistroProduct1.getText().toString();
        String precio= txtPrecioRegistroProduct1.getText().toString();
        String cantidad= txtStockRegistroProduct1.getText().toString();
        String iva= txtIvaRegistroProducto1.getText().toString();
        String fecha= txtCalendar1.getText().toString();

        if (!nombre.equals("")&& !precio.equals("") && !cantidad.equals("") && !iva.equals("") && !fecha.equals("")){
            Bdd.actualizarProductos(nombre,precio,cantidad,iva,fecha, id);//Procesando la actualizacion en la bdd
            Toast.makeText(getApplicationContext(),"Se Actualizaron los datos Correctamente", Toast.LENGTH_SHORT).show();
        }else {
            //Presentando un mensaje cuando un campo este vacio
            Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
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
        DatePickerDialog dpd = new DatePickerDialog(EditarProducto.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //variable que contiene la fecha concatenada
                String fecha = dayOfMonth + "/" + month + "/" + year;
                //asignamos a nuestro txt calendar nuestra fecha seleccionada en el calendario
                txtCalendar1.setText(fecha);
            }
        }, anio, mes, dia);
        dpd.show();
    }
}