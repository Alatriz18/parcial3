package com.example.practica3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/*
Autor: Kevin e Isabel
Creado: 16/6/2021
modificado: 09/06/2021
descripcion: Vetana de inicio de session y acceso a las pantalla de Registro y Recuperacion de Contraseña.
 */
public class EditarClienteActivity extends AppCompatActivity {

    String id, cedula, apellido, nombre, telefono, direccion;//variables para capturar los valores que vinen como parametros
    TextView txtClienteEditar;
    EditText txtCedulaClienteEditar, txtApellidoClienteEditar, txtNombreClienteEditar, txtTelefonoClienteEditar, txtDireccionClienteEditar;
    BaseDatos bdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_cliente);
        //Mapeo de elementos XML a objetos Java
        txtClienteEditar=(TextView) findViewById(R.id.txtidProductoEditar);
        txtCedulaClienteEditar= findViewById(R.id.txtCedulaClienteEditar);
        txtApellidoClienteEditar= findViewById(R.id.txtApellidoClienteEditar);
        txtNombreClienteEditar= findViewById(R.id.txtNombreClienteEditar);
        txtTelefonoClienteEditar= findViewById(R.id.txtTelefonoClienteEditar);
        txtDireccionClienteEditar= findViewById(R.id.txtDireccionClienteEditar);
        Bundle parametrosExtra=getIntent().getExtras();//capturando los parametros que se han pasado a esta actividad
        if (parametrosExtra!=null){
            try{
                //Intente realizar estas lineas de codigo
                id=parametrosExtra.getString("id");
                cedula=parametrosExtra.getString("cedula");
                apellido=parametrosExtra.getString("apellido");
                nombre=parametrosExtra.getString("nombre");
                telefono=parametrosExtra.getString("telefono");
                direccion=parametrosExtra.getString("direccion");
            }catch (Exception ex){
                Toast.makeText(getApplicationContext(), "Error al procesar la solicitud",Toast.LENGTH_SHORT).show();
            }
        }
        txtClienteEditar.setText(id);
        txtCedulaClienteEditar.setText(cedula);
        txtApellidoClienteEditar.setText(apellido);
        txtNombreClienteEditar.setText(nombre);
        txtTelefonoClienteEditar.setText(telefono);
        txtDireccionClienteEditar.setText(direccion);

        bdd = new BaseDatos(getApplicationContext());//Instanciando el objeto a traves del
    }

    //Metodo para volver
    public void volver(View vista){
        finish();//cerrar la ventana
        //creando un objeto para manejar la ventana de gestion de clientes
        Intent ventanaGestionClientes= new Intent(getApplicationContext(),ClienteActivity.class);
        startActivity(ventanaGestionClientes);
    }

    public void eliminarCliente(View vista){
        AlertDialog.Builder estructuraConfirmacion= new AlertDialog.Builder(this).setTitle("CONFIRMACIÓN")
                .setMessage("Esta seguro de Eliminar el cliente seleccionado?")
                .setPositiveButton("Si, Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bdd.eliminarCliente(id);//Procesando la eliminacion con base del id del cliente
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

    public void actualizar(View vista){

        String cedula= txtCedulaClienteEditar.getText().toString();
        String apellido= txtApellidoClienteEditar.getText().toString();
        String nombre= txtNombreClienteEditar.getText().toString();
        String telefono= txtTelefonoClienteEditar.getText().toString();
        String direccion= txtDireccionClienteEditar.getText().toString();

        if (!cedula.equals("")&& !apellido.equals("") && !nombre.equals("") && !telefono.equals("") && !direccion.equals("")){
            bdd.actualizarCliente(cedula,apellido,nombre,telefono,direccion, id);//Procesando la actualizacion en la bdd
            Toast.makeText(getApplicationContext(),"Se Actualizaron los datos Correctamente", Toast.LENGTH_SHORT).show();
        }else {
            //Presentando un mensaje cuando un campo este vacio
            Toast.makeText(getApplicationContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show();
        }

    }
}