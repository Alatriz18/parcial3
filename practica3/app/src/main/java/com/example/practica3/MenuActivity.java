package com.example.practica3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
Autor: Angel Jativa, Paul Montaleza
Creado: 16/5/2021
modificado: 09/06/2021
descripcion: Vetana de inicio de session y acceso a las pantalla de Registro y Recuperacion de Contraseña.
 */

public class MenuActivity extends AppCompatActivity {

    Button mLogoutBtn;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mLogoutBtn = findViewById(R.id.mLogoutBtn);

        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

        mLogoutBtn.setOnClickListener(new View.OnClickListener() { //SIver para cerrar la sesion de la aplicacion
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



    //PROCESO 2
    public void abrirPantallaCliente(View vista){
        Intent pantallaCliente=new Intent(getApplicationContext(),ClienteActivity.class); //Creando un intent para invocar a Cliente
        startActivity(pantallaCliente);//Iniciando la pantalla de Cliente
    }

    //PROCESO 2
    public void abrirPantallaProducto(View vista){
        Intent pantallaProducto=new Intent(getApplicationContext(),ProductoActivity.class); //Creando un intent para invocar a Producto
        startActivity(pantallaProducto);//Iniciando lapantalla de Producto
    }

    //PROCESO 2
    public void abrirPantallaVenta(View vista){
        Intent pantallaVenta=new Intent(getApplicationContext(),VentaActivity.class); //Creando un intent para invocar a Venta
        startActivity(pantallaVenta);//Iniciando lapantalla de venta
    }



}