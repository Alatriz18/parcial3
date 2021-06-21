package com.example.practica3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
/*
Autor: Isabel Marquinez
Creado: 16/5/2021
modificado: 09/06/2021
descripcion: Vetana de inicio de session y acceso a las pantalla de Registro y Recuperacion de Contraseña.
 */

public class ClienteActivity extends AppCompatActivity {

    //Entrada
    EditText txtCedulaCliente, txtApellidoCliente, txtNombreCliente, txtTelefonoCliente, txtDireccionCliente;
    BaseDatos Bdd;
    //Salida

    ListView IstClientes;
    ArrayList<String> listaClientes= new ArrayList<>();
    public Cursor clienteObtenidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        txtCedulaCliente=(EditText) findViewById(R.id.txtCedulaCliente);
        txtApellidoCliente=(EditText) findViewById(R.id.txtApellidoCliente);
        txtNombreCliente=(EditText) findViewById(R.id.txtNombreCliente);
        txtTelefonoCliente=(EditText) findViewById(R.id.txtTelefonoCliente);
        txtDireccionCliente=(EditText) findViewById(R.id.txtDireccionCliente);
        IstClientes=(ListView) findViewById(R.id.IstClientes);
        Bdd=new BaseDatos(getApplicationContext()); //Instanciando el objeto para llamar a los metodos de la Base de Datos
        consultarDatos(); //llamando al metodo para cargar los datos de clientes ene l list view
        IstClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //programacion de acciones cuando se da clic de un elemento de la lista cliente
                clienteObtenidos.moveToPosition(position);
                String idCliente=clienteObtenidos.getString( 0);
                String cedulaCliente=clienteObtenidos.getString( 1);
                String apellidoCliente=clienteObtenidos.getString( 2);
                String nombreCliente=clienteObtenidos.getString( 3);
                String telefonoCliente=clienteObtenidos.getString( 4);
                String direccionCliente=clienteObtenidos.getString( 5);
                //Toast.makeText(getApplicationContext(),idCliente+"-"+cedulaCliente+"-"+apellidoCliente+"-"+
                        //nombreCliente+"-"+telefonoCliente+"-"+direccionCliente,Toast.LENGTH_LONG).show();
                Intent ventanaEditarCliente= new Intent(getApplicationContext(),EditarClienteActivity.class);
                ventanaEditarCliente.putExtra("id",idCliente);
                ventanaEditarCliente.putExtra("cedula",cedulaCliente);
                ventanaEditarCliente.putExtra("apellido",apellidoCliente);
                ventanaEditarCliente.putExtra("nombre",nombreCliente);
                ventanaEditarCliente.putExtra("telefono",telefonoCliente);
                ventanaEditarCliente.putExtra("direccion",direccionCliente);
                startActivity(ventanaEditarCliente);
                finish();
            }
        });

    }

    public void LimpiarCampos(View vista){
        txtCedulaCliente.setText("");
        txtApellidoCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefonoCliente.setText("");
        txtDireccionCliente.setText("");//CTRL + ALT + L---> PARA LIMPIAR EL CODIGO
        txtCedulaCliente.requestFocus();
    }

    public void guardarCliente(View vista){
        //capturados los valores ingresados por el usuario en el formulario de Registro de Clientes
        String cedula= txtCedulaCliente.getText().toString();
        String apellido= txtApellidoCliente.getText().toString();
        String nombre= txtNombreCliente.getText().toString();
        String telefono= txtTelefonoCliente.getText().toString();
        String direccion= txtDireccionCliente.getText().toString();

        if (!cedula.equals("") && !apellido.equals("") && !nombre.equals("") && !telefono.equals("") && !direccion.equals("")){

            Bdd.AgregarCliente(cedula,apellido,nombre,telefono,direccion);
            LimpiarCampos(null);
            Toast.makeText(getApplicationContext(),"Cliente Registrdo Exitosamente",
                    Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"Para guardar complete todos los campos del formulario",
                    Toast.LENGTH_LONG).show();
        }
        consultarDatos();

    }

    //metodo para consultar los clientes existenetes en sqlite y presentarlos en una lista
    public void consultarDatos(){
        listaClientes.clear();
        clienteObtenidos=Bdd.obtenerClientes();//consultando clientes y guardandolos en un cursor
        if (clienteObtenidos!=null){
            do {
                String id=clienteObtenidos.getString(0).toString();
                String apellido=clienteObtenidos.getString(2).toString();
                String nombre=clienteObtenidos.getString(3).toString();
                listaClientes.add(id+": "+apellido+" "+nombre);
                ArrayAdapter<String> adaptadorClientes= new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,listaClientes);
                IstClientes.setAdapter(adaptadorClientes);
            }while (clienteObtenidos.moveToNext());
        }else {
            Toast.makeText(getApplicationContext(),"No Existen clientes registrados",
                    Toast.LENGTH_LONG).show();
        }
        //clienteObtenidos.close();
    }
}