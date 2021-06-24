package com.example.practica3;

public class Producto {
    int id,stock;
    String nombre,iva, fechaCaducidad, fechaCreacion;
    Float precio;


    //CONSTRUTOR
    public Producto(){

    }

    public Producto(int stock, String nombre, String iva, String fechaCaducidad, String fechaCreacion, Float precio) {
        this.stock = stock;
        this.nombre = nombre;
        this.iva = iva;
        this.fechaCaducidad = fechaCaducidad;
        this.fechaCreacion = fechaCreacion;
        this.precio = precio;
    }

    ///GETTERT AND SETTER


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(String fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }
}


