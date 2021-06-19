package com.example.practica3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/*
Autor: Angel Jativa, Paul Montaleza
Creado: 16/5/2021
modificado: 2/06/2021
descripcion: Vetana de inicio de session y acceso a las pantalla de Registro y Recuperacion de Contraseña.
 */
public class MainActivity extends AppCompatActivity {

    //Entrada
    EditText  txtEmailLogin, txtPasswordLogin;
    BaseDatos bdd;
    CheckBox mRmember;
    Button mBtn;
    SharedPreferences sharedPreferences;
    boolean isRemembered = false;


    //proceso 1
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEmailLogin=(EditText) findViewById(R.id.txtEmailLogin);
        txtPasswordLogin=(EditText) findViewById(R.id.txtPasswordLogin);
        mRmember = findViewById(R.id.mRemember);
        mBtn = findViewById(R.id.mBtn);
        bdd=new BaseDatos(getApplicationContext());//instanciando la base de datos dentro del objeto bdd

        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

        isRemembered = sharedPreferences.getBoolean("CHECKBOX", false);

        if (isRemembered){
            Intent ventanaMenu= new Intent(getApplicationContext(),MenuActivity.class);
            startActivity(ventanaMenu);
            finish();
        }

        //Proceso 2
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=txtEmailLogin.getText().toString();
                String password=txtPasswordLogin.getText().toString();
                boolean checked = mRmember.isChecked();// checkbox para recordar el usuario y mantener la sesion
                //Consultando el usuario en la base de datos:
                Cursor usuarioEncontrado=bdd.obtenerUsuarioPorEmailPassword(email,password);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (usuarioEncontrado!=null){
                    //para el caso de que se  encuentren los datos correctos
                    String emailBdd=usuarioEncontrado.getString(3).toString();
                    String nombreBdd=usuarioEncontrado.getString(2).toString();
                    editor.putBoolean("CHECKBOX", checked);
                    editor.apply();
                    Toast.makeText(getApplicationContext(),"Bienvenido al Sistema"+nombreBdd,Toast.LENGTH_LONG).show();
                    Intent ventanaMenu= new Intent(getApplicationContext(),MenuActivity.class);
                    startActivity(ventanaMenu);
                    finish();

                }else {
                    //Si no seencuentra el password o email se muestra un mensaje de advertensia
                    Toast.makeText(getApplicationContext(),"Email o Contraseña Incorrectos",Toast.LENGTH_LONG).show();
                    txtPasswordLogin.setText("");//se limpia la memoria del campo

                }
            }
        });

    }

    //PROCESO 3
    public void abrirPantallaRegistro(View vista){
        Intent pantallaRegistro=new Intent(getApplicationContext(),RegistroActivity.class); //Creando un intent para invocar a Registrar
        startActivity(pantallaRegistro);//Iniciando lapantalla de registro
    }



    //PROCESO 3
   /* public void iniciarSesion(View vista){
        //logica de negocio para capturar los valores ingresados por el usuario
        String email=txtEmailLogin.getText().toString();
        String password=txtPasswordLogin.getText().toString();
        //Consultando el usuario en la base de datos:
        Cursor usuarioEncontrado=bdd.obtenerUsuarioPorEmailPassword(email,password);
        if (usuarioEncontrado!=null){
            //para el caso de que se  encuentren los datos correctos
            String emailBdd=usuarioEncontrado.getString(3).toString();
            String nombreBdd=usuarioEncontrado.getString(2).toString();
            Toast.makeText(getApplicationContext(),"Bienvenido al Sistema"+nombreBdd,Toast.LENGTH_LONG).show();
            finish();
            Intent ventanaMenu= new Intent(getApplicationContext(),MenuActivity.class);
            startActivity(ventanaMenu);

        }else {
            //Si no seencuentra el password o email se muestra un mensaje de advertensia
            Toast.makeText(getApplicationContext(),"Email o Contraseña Incorrectos",Toast.LENGTH_LONG).show();
            txtPasswordLogin.setText("");//se limpia la memoria del campo

        }
    }*/
}