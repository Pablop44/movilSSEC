package com.example.ssec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ssec.aux.DatePickerFragment;
import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;

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
    private Spinner spGenero;

    private String baseUrl;

    private final static String[] genero = {"Género", "Hombre", "Mujer"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle(R.string.register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        baseUrl = "http://10.0.2.2:8765/user/registerPaciente.json";

        editText_dni = (EditText) findViewById(R.id.editText_dni);
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_nombre = (EditText) findViewById(R.id.editText_nombre);
        editText_apellidos = (EditText) findViewById(R.id.editText_apellidos);
        editText_telefono = (EditText) findViewById(R.id.editText_telefono);
        editText_poblacion = (EditText) findViewById(R.id.editText_poblacion);
        editText_nacimiento = (EditText) findViewById(R.id.editText_nacimiento);
        spGenero = (Spinner) findViewById(R.id.spinner_genero);

        button_register = (Button) findViewById(R.id.button_register);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, genero);
        spGenero.setAdapter(adapter);

        editText_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dni = editText_dni.getText().toString();
                String username = editText_username.getText().toString();
                String password = editText_password.getText().toString();
                String email = editText_email.getText().toString();
                String nombre = editText_nombre.getText().toString();
                String apellidos = editText_apellidos.getText().toString();
                String telefono = editText_telefono.getText().toString();
                String poblacion = editText_poblacion.getText().toString();
                String nacimiento = editText_nacimiento.getText().toString();

                String[] valores = nacimiento.split("-");
                String nacimientoFinal = valores[2] + "-" + valores[1] + "-" + valores[0] + " 00:00:00";

                String genero = spGenero.getSelectedItem().toString();

                if(genero == "Hombre"){
                    genero = "Male";
                }else if(genero == "Mujer"){
                    genero = "Female";
                }

                HashMap<String, String> atributos = new HashMap<String, String>();
                atributos.put("dni", dni);
                atributos.put("username", username);
                atributos.put("password", password);
                atributos.put("email", email);
                atributos.put("nombre", nombre);
                atributos.put("apellidos", apellidos);
                atributos.put("telefono", telefono);
                atributos.put("poblacion", poblacion);
                atributos.put("nacimiento", nacimientoFinal);
                atributos.put("genero", genero);

                try{

                    ApiAuthenticationClient apiAuthenticationClient =
                            new ApiAuthenticationClient(
                                    baseUrl
                                    , username
                                    , password
                            );

                    apiAuthenticationClient.setHttpMethod("POST");
                    apiAuthenticationClient.setParameters(atributos);

                    AsyncTask<Void, Void, String> execute = new registerActivity.ExecuteNetworkOperation(apiAuthenticationClient);
                    execute.execute();
                }
                catch(Exception ex)
                {
                    Toast.makeText(getApplicationContext(), "Fallo al registrarse", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            public String nacimiento;
            String separador1;
            String separador;

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                if(month < 9){
                    separador1 = "-0";
                }else{
                    separador1 = "-";
                }
                if(day < 10){
                    separador = "0";
                }else{
                    separador = "";
                }

                // +1 because January is zero
                final String selectedDate = separador + day + separador1 + (month + 1) + "-" + year;
                editText_nacimiento.setText(selectedDate);


            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String isValidCredentials;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperation(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Display the progress bar.
            findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                isValidCredentials = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(isValidCredentials != ""){
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(registerActivity.this);

                dlgAlert.setMessage("Vaya a su correo electrónico y active su cuenta");
                dlgAlert.setTitle("Éxito");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finalizarActividad();
                            }
                        });
            }else {
                Toast.makeText(getApplicationContext(), "Error al crear el usuario", Toast.LENGTH_LONG).show();
                findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
            }
        }
    }

    public void finalizarActividad(){
        finish();
    }

}


