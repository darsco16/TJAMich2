package com.darsco.tjamich;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register_local extends AppCompatActivity {

    EditText usuario,nombre,contrasena,correo;
    DBHelper helper = new DBHelper(this);
    Button guardarLocal, BLista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_local);
        usuario = (EditText) findViewById(R.id.etUsuario);
        contrasena = (EditText) findViewById(R.id.etContrasena);
        nombre= (EditText) findViewById(R.id.etNombre);
        correo = (EditText) findViewById(R.id.etEmail);
        guardarLocal = (Button) findViewById(R.id.btnRegisterLocal);
        BLista=(Button)findViewById(R.id.btnLista);

        guardarLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registroLocal();
            }
        });
        BLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intento = new Intent(getApplicationContext(),lista_local.class);
                startActivity(intento);
            }
        });
    }
    public void registroLocal() {

        String user = usuario.getText().toString();
        final String contra = contrasena.getText().toString();
        final String nom= nombre.getText().toString();
        final String email = correo.getText().toString();
        View focusView = null;
        boolean cancel = false;
        if (!TextUtils.isEmpty(email) && !isPasswordValid(email)) {
            contrasena.setError(getString(R.string.error_invalid_password));
            focusView = contrasena;
            cancel = true;
        }
        if(user.equals("") || nom.equals("") || contra.equals("") || email.equals("")){
            Toast.makeText(register_local.this, "Llene todos los campos", Toast.LENGTH_LONG).show();
        }else if (!isEmailValid(email)) {
            correo.setError(getString(R.string.error_invalid_email));
            focusView = correo;
            cancel = true;
        }else{
            helper.abrir();
            if(!user.isEmpty()){
                Cursor cursor = helper.consultarUsuario(user, email);
                if (cursor.moveToFirst()){
                    Toast.makeText(this, "El usuario o correo ya existe", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), register_local.class);
                    startActivity(i);
                }else{
                    int id = 0;
                    helper.abrir();
                    helper.insertUsuario(nom, user, contra, email);
                    helper.cerrar();

                    Toast.makeText(register_local.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }
            helper.cerrar();
            /*try {
                helper.abrir();
                Cursor cursor = helper.alreadyExist(user);
                if (cursor.getCount() > 0) {
                    Toast.makeText(this, "Ya existe este usuario", Toast.LENGTH_SHORT).show();
                } else {*/
            /*int exis;
            DBHelper db = new DBHelper(getApplicationContext());
            exis=db.alreadyExist(user);
            if (exis == 1){
                Toast.makeText(this, "Ya existe este usuario", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }else {
                int id = 0;
                helper.abrir();
                helper.insertUsuario(nom, user, contra, email);
                helper.cerrar();

                Toast.makeText(register_local.this, "Registro exitoso", Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }*/
                /*}
                helper.cerrar();
            } catch (SQLException e) {
            e.printStackTrace();
            }*/
        }
    }
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

}
