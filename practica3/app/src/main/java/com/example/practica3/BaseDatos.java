package com.example.practica3;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
Autor: Isabel Marquinez, Kevin Santana
Creado: 16/5/2021
modificado: 18/05/2021
descripcion: Para gestionar la base de datos.
 */

public class BaseDatos extends SQLiteOpenHelper {

    private static final String nombreBdd="bdd_usuarios_parqueadero"; //define el nombre de la base de datos
    private static final int versionBdd=4; //definiendo la version de la base de dato
    private static final String tablaUsuario="create table usuario(id_usu integer primary key autoincrement, " +
            "apellido_usu text, nombre_usu text, email_usu text, password_usu text, celular_usu text, direccion_usu text, fecha_usu text)";//definiendo la estructura de la tabla usuarios

    //Definiendo la estructura de la tabla cliente
    private static final String tablaCliente="create table cliente(id_cli integer primary key autoincrement," +
            "cedula_cli text, apellido_cli text, nombre_cli text, telefono_cli text, direccion_cli text)";

    //Definiendo la estructura de la tabla cliente
    private static final String tablaProducto= "create table Producto(id_product integer primary key autoincrement, " +
            "nombre_product text, precio_product DECIMAL(2,0), cantidad_product INTEGER, iva_product INTEGER, fechaRegistro_product text)";

    private static final String tablaVenta= "create table venta(id_ven integer primary key autoincrement, " +
            "clientesele_ven text, productosele_ven text,cantidad_ven int, preciototal_ven float, fechaventa_ven text)";

    //constructor
    public BaseDatos(Context contexto){
        super(contexto,nombreBdd,null,versionBdd);
    }


    //proceso 1: Metodo que ejecuta automaticamente al construir la clase BaseDatos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tablaUsuario);//ejecutando el query DDL para crear la tabla con sus atributos
        db.execSQL(tablaCliente);//ejecutando el query DDL para crear la tabla con sus atributos
        db.execSQL(tablaProducto);//ejecutando el query DDL para crear la tabla con sus atributos
        db.execSQL(tablaVenta);//query para tabla Venta
    }

    //proceso 2: Metodo que se ejecuta automaticamente cuando se detectan cambios en la version de nuestra Bdd
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");//Eliminacion de la version anterior de la tabla usuario
        db.execSQL(tablaUsuario);//Ejecucion del codigo para crear la tabla usuario con su nueva estructura
        db.execSQL("DROP TABLE IF EXISTS cliente"); //Eliminacion de la version anterior de la tabla cliente
        db.execSQL(tablaCliente);//Ejecucion del codigo para crear la tabla cliente con su nueva estructura
        db.execSQL("DROP TABLE IF EXISTS producto"); //Eliminacion de la version anterior de la tabla producto
        db.execSQL(tablaProducto);//Ejecucion del codigo para crear la tabla producto con su nueva estructura
        db.execSQL("DROP TABLE IF EXISTS venta");///Eliminacion de la version anterior de la tabla venta
        db.execSQL(tablaVenta);//Ejecucion del codigo para crear la tabla venta

    }

    //Proceso 3: Metodo para insertar datos dentro de la tabla usuario
    public boolean agregarUsuario(String apellido, String nombre, String email, String password, String celular, String direccion, String fecha){
        SQLiteDatabase miBdd=getWritableDatabase(); //llamando a la base de datos en el objeto miBdd
        if (miBdd!=null) { //Validando que la base de datos exista (no sea nula)
            miBdd.execSQL("insert into usuario(apellido_usu, nombre_usu, email_usu, password_usu, celular_usu, direccion_usu, fecha_usu )" +
                "values('"+apellido+"','"+nombre+"','"+email+"','"+password+"','"+celular+"','"+direccion+"','"+fecha+"')"); //ejecutando la setencia de insercion de SQL
            miBdd.close();
            return true;
        }
        return false; //retorno cuando no exista la base de datos
    }

    //crud
    public Cursor obtenerUsuarioPorEmailPassword(String email, String password){
        SQLiteDatabase miBdd=getWritableDatabase();//llamado a la base de dato
        Cursor usuario=miBdd.rawQuery("select * from usuario where " +
                "email_usu='"+email+"' and password_usu='"+password+"';",null);
        if (usuario.moveToFirst()){
            return usuario;
        }else {
            return null;
        }

    }

    public boolean AgregarCliente(String cedula, String apellido, String nombre, String telefono, String direccion){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){
            miBdd.execSQL("insert into cliente(cedula_cli, apellido_cli, nombre_cli,telefono_cli, direccion_cli) " +
                    "values  ('"+cedula+"','"+apellido+"','"+nombre+"','"+telefono+"','"+direccion+"');");
            miBdd.close();
            return true;
        }
        return false;
    }

    public Cursor obtenerClientes(){
        SQLiteDatabase miBdd= getWritableDatabase();//objeto para manejar la base de datos
        //consultando los clientes en la base de datos y guardandolos en un cursor
        Cursor clientes=miBdd.rawQuery("select * from cliente; ", null);

        if (clientes.moveToFirst()){
            miBdd.close();
            return clientes; //retornar el cursor que contiene el listado de cliente
        }else {
            return null;
        }
    }


    //Metodo para actualizar un registro de cliente
    public boolean actualizarCliente(String cedula, String apellido, String nombre, String telefono, String direccion, String id){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){//validando que la base de datos en realidad existe
            //Proceso de actualizacion
            miBdd.execSQL("update cliente set cedula_cli='"+cedula+"', " +
                    "apellido_cli='"+apellido+"', nombre_cli='"+nombre+"', " +
                    "telefono_cli='"+telefono+"',direccion_cli='"+direccion+"' where id_cli="+id);
            miBdd.close();
            return true;
        }
        return false;
    }


    //metodo para eliminar un registro de clientes
    public boolean eliminarCliente(String id){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){
            miBdd.execSQL("delete from cliente where id_cli="+id);
            miBdd.close();
            return true;
        }
        return false;
    }


    public boolean AgregarProducto(String nombre, String precio, String cantidad, String iva, String fecha){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){
            miBdd.execSQL("insert into producto(nombre_product, precio_product, cantidad_product, iva_product, fechaRegistro_product) " +
                    "values  ('"+nombre+"','"+precio+"','"+cantidad+"','"+iva+"','"+fecha+"');");
            miBdd.close();
            return true;
        }
        return false;
    }

    public Cursor obtenerProductos(){
        SQLiteDatabase miBdd= getWritableDatabase();//objeto para manejar la base de datos
        //consultando los clientes en la base de datos y guardandolos en un cursor
        Cursor productos=miBdd.rawQuery("select * from producto; ", null);

        if (productos.moveToFirst()){
            miBdd.close();
            return productos; //retornar el cursor que contiene el listado de cliente
        }else {
            return null;
        }
    }
    public boolean actualizarProductos(String nombre, String precio, String cantidad, String iva, String fecha, String id){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){//validando que la base de datos en realidad existe
            //Proceso de actualizacion
            miBdd.execSQL("update producto set nombre_product='"+nombre+"', " +
                    "precio_product='"+precio+"', cantidad_product='"+cantidad+"', " +
                    "iva_product='"+iva+"',fechaRegistro_product='"+fecha+"' where id_product="+id);
            miBdd.close();
            return true;
        }
        return false;
    }


    //metodo para eliminar un registro de clientes
    public boolean eliminarProducto(String id){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){
            miBdd.execSQL("delete from producto where id_product="+id);
            miBdd.close();
            return true;
        }
        return false;
    }
    public Producto verProductId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        //ArrayList<Clientes> listaClientes = new ArrayList<>();
        Producto product = null;
        Cursor cursorProduct;

        cursorProduct = db.rawQuery("SELECT * FROM Producto WHERE id_product =" + id + " LIMIT 1", null);
        if (cursorProduct.moveToFirst()) {

            product = new Producto();
            product.setId(cursorProduct.getInt(0));
            product.setNombre(cursorProduct.getString(1));
            product.setPrecio((cursorProduct.getFloat(2)));
            product.setIva(cursorProduct.getString(3));
            product.setStock(cursorProduct.getInt(4));
            product.setFechaCaducidad(cursorProduct.getString(5));
        }
        cursorProduct.close();
        return product;

    }

    //metodo Agregar ventas
    public boolean agregarVenta(String clientesele, String productosele,int cantidadven, float preciototal){
        SQLiteDatabase miBdd=getWritableDatabase();
        if (miBdd!=null){
            miBdd.execSQL("insert into venta(clientesele_ven, productosele_ven,cantidad_ven, preciototal_ven, fechaventa_ven) " +
                    "values  ('"+clientesele+"','"+productosele+"','"+preciototal+"','"+cantidadven+"','"+getDate()+"');");
            miBdd.close();
            return true;
        }
        return false;
    }

    //Metodo consultar/obtener venta
    public Cursor obtenerVenta(){
        SQLiteDatabase miBdd= getWritableDatabase();//objeto para manejar la base de datos
        //consultando las ventas en la base de datos y guardandolos en un cursor
        Cursor ventas=miBdd.rawQuery("select * from venta; ", null);
        if (ventas.moveToFirst()){
            miBdd.close();
            return ventas; //retornar el cursor que contiene el listado de cliente
        }else {
            return null;
        }
    }
    private String getDate() {             // se ver??a as??: miercoles 26/09/2018 05:30 p.m.
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd/MM/yyyy hh:mm  a", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
