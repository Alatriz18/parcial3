package com.example.practica3;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

/*
Autor: Isabel Marquinez, Kevin Santana
Creado: 16/5/2021
modificado: 13/05/2021
descripcion: Vetana de cancelacion de registro.
 */
public class RegistroActivity extends AppCompatActivity {

    //Entrada
    EditText txtApellidoRegistro, txtNombreRegistro, txtEmailRegistro, txtPasswordRegistro, txtPasswordConfirmadaRegistro, txtTelefonoRegistro, txtDireccionRegistro;
    TextView txtFechaRegistro;
    BaseDatos miBdd;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtApellidoRegistro = (EditText) findViewById(R.id.txtApellidoRegistro);
        txtNombreRegistro = (EditText) findViewById(R.id.txtNombreRegistro);
        txtEmailRegistro = (EditText) findViewById(R.id.txtEmailRegistro);
        txtPasswordRegistro = (EditText) findViewById(R.id.txtPasswordRegistro);
        txtPasswordConfirmadaRegistro = (EditText) findViewById(R.id.txtPasswordConfirmadaRegistro);
        txtTelefonoRegistro = (EditText) findViewById(R.id.txtTelefonoRegistro);
        txtDireccionRegistro = (EditText) findViewById(R.id.txtDireccionRegistro);
        txtFechaRegistro=(TextView)findViewById(R.id.txtFechaRegistro);

        miBdd = new BaseDatos(getApplicationContext());
    }

    //proceso2
        public void cerrarPantallaRegistro(View vista) {
        finish();//cerrando la pantalla de registro
        }

    //proceso 3
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void registrarUsuario(View vista) {
        Date date = new Date();
        String apellido = txtApellidoRegistro.getText().toString();
        String nombre = txtNombreRegistro.getText().toString();
        String email = txtEmailRegistro.getText().toString();
        String password = txtPasswordRegistro.getText().toString();
        String passwordConfirmada = txtPasswordConfirmadaRegistro.getText().toString();
        String celular = txtTelefonoRegistro.getText().toString();
        String direccion = txtDireccionRegistro.getText().toString();
        SimpleDateFormat fechaC=new SimpleDateFormat("d,MMM 'del', yyy hh:mm");
        String sFecha=fechaC.format(date);
        txtFechaRegistro.setText(sFecha);

        if (txtApellidoRegistro.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campo Apellido Vacio", Toast.LENGTH_LONG).show();
        } else {
            if (txtNombreRegistro.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Campo Nombre Vacio", Toast.LENGTH_LONG).show();
            } else {
                if (txtEmailRegistro.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Campo Email Vacio", Toast.LENGTH_LONG).show();
                } else {
                    if (txtPasswordRegistro.getText().toString().isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Campo Password Vacio", Toast.LENGTH_LONG).show();
                    } else {
                        if (txtPasswordConfirmadaRegistro.getText().toString().isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Campo Password Confirmación Vacio", Toast.LENGTH_LONG).show();
                        } else {
                            if (txtTelefonoRegistro.getText().toString().isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Campo Celular Vacio", Toast.LENGTH_LONG).show();
                            } else {
                                if (txtDireccionRegistro.getText().toString().isEmpty()) {
                                    Toast.makeText(getApplicationContext(), "Campp Dirección Vacio", Toast.LENGTH_LONG).show();
                                } else {
                                        if (password.length()<6){
                                            Toast.makeText(getApplicationContext(), "Contraseña debe tener 6 Digitos", Toast.LENGTH_LONG).show();
                                            }else {
                                            if (password.equals(passwordConfirmada)) {
                                                miBdd.agregarUsuario(apellido, nombre, email, password, celular, direccion,sFecha);
                                                Toast.makeText(getApplicationContext(), "Usuario almacenado exitosamente", Toast.LENGTH_LONG).show();
                                                finish();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "La contraseña ingresada no coincide", Toast.LENGTH_LONG).show(); //mesaje de alerta por password no coincidan
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }