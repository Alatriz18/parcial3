package com.example.practica3;

public class ClientDatos {
    Integer id_cli;
    String cedula_cli;
    String apellido_cli;
    String nombre_cli;
    String telefono_cli;
    String direccion_cli;

    public ClientDatos(Integer id_cli, String cedula_cli, String apellido_cli, String nombre_cli, String telefono_cli, String direccion_cli) {
        this.id_cli = id_cli;
        this.cedula_cli = cedula_cli;
        this.apellido_cli = apellido_cli;
        this.nombre_cli = nombre_cli;
        this.telefono_cli = telefono_cli;
        this.direccion_cli = direccion_cli;
    }

    public Integer getId_cli() {
        return id_cli;
    }

    public void setId_cli(Integer id_cli) {
        this.id_cli = id_cli;
    }

    public String getCedula_cli() {
        return cedula_cli;
    }

    public void setCedula_cli(String cedula_cli) {
        this.cedula_cli = cedula_cli;
    }

    public String getApellido_cli() {
        return apellido_cli;
    }

    public void setApellido_cli(String apellido_cli) {
        this.apellido_cli = apellido_cli;
    }

    public String getNombre_cli() {
        return nombre_cli;
    }

    public void setNombre_cli(String nombre_cli) {
        this.nombre_cli = nombre_cli;
    }

    public String getTelefono_cli() {
        return telefono_cli;
    }

    public void setTelefono_cli(String telefono_cli) {
        this.telefono_cli = telefono_cli;
    }

    public String getDireccion_cli() {
        return direccion_cli;
    }

    public void setDireccion_cli(String direccion_cli) {
        this.direccion_cli = direccion_cli;
    }

    @Override
    public String toString() {
        return "ClientDatos{" +
                "apellido_cli='" + apellido_cli + '\'' +
                ", nombre_cli='" + nombre_cli + '\'' +
                '}';
    }
}
