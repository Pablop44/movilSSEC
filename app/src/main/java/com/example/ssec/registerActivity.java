package com.example.ssec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ssec.aux.DatePickerFragment;
import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

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
                String nacimientoFinal = valores[2] + "-" + valores[1] + "-" + valores[0];

                String genero = spGenero.getSelectedItem().toString();

                try {

                    ApiAuthenticationClient apiAuthenticationClient =
                            new ApiAuthenticationClient(
                                    baseUrl
                                    , username
                                    , password
                            );

                    apiAuthenticationClient.setHttpMethod("post");

                    apiAuthenticationClient.setParameter("dni", dni);
                    apiAuthenticationClient.setParameter("username", username);
                    apiAuthenticationClient.setParameter("password", password);
                    apiAuthenticationClient.setParameter("email", email);
                    apiAuthenticationClient.setParameter("nombre", nombre);
                    apiAuthenticationClient.setParameter("apellidos", apellidos);
                    apiAuthenticationClient.setParameter("poblacion", poblacion);
                    apiAuthenticationClient.setParameter("telefono", telefono);
                    apiAuthenticationClient.setParameter("nacimiento", nacimientoFinal);
                    apiAuthenticationClient.setParameter("genero", genero);

                    AsyncTask<Void, Void, String> execute = new ExecuteNetworkOperation(apiAuthenticationClient);
                    execute.execute();

                } catch (Exception ex) {
                }
            }
        });
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

                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), isValidCredentials, Toast.LENGTH_LONG).show();

        }
    }
}


