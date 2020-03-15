package com.example.ssec;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ssec.aux.DatePickerFragment;

public class registerActivity extends AppCompatActivity {

    private Button button_register;
    private EditText editText_dni;
    private EditText editText_username;
    private EditText editText_password;
    private EditText editText_email;
    private EditText editText_nombre;
    private EditText editText_apellidos;
    private EditText editText_telefono;
    private EditText editText_poblacion;
    private EditText editText_nacimiento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText_dni = (EditText) findViewById(R.id.editText_dni);
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_nombre = (EditText) findViewById(R.id.editText_nombre);
        editText_apellidos = (EditText) findViewById(R.id.editText_apellidos);
        editText_telefono = (EditText) findViewById(R.id.editText_telefono);
        editText_poblacion = (EditText) findViewById(R.id.editText_poblacion);
        editText_nacimiento = (EditText) findViewById(R.id.editText_nacimiento);

        button_register = (Button) findViewById(R.id.button_register);


        editText_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}
