package com.example.ssec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiService;
import com.google.gson.Gson;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private Button button_login_login;
    private Button button_register;
    private EditText editText_login_username;
    private EditText editText_login_password;
    private String username;
    private String password;
    private String baseUrl;

    private User userRegistered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        baseUrl = "http://10.0.2.2:8765/user/loginPaciente.json";

        editText_login_username = (EditText) findViewById(R.id.editText_login_username);
        editText_login_password = (EditText) findViewById(R.id.editText_login_password);

        button_login_login = (Button) findViewById(R.id.button_login_login);
        button_register = (Button) findViewById(R.id.button_register);

        button_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = editText_login_username.getText().toString();
                password = editText_login_password.getText().toString();

                HashMap<String, String> atributos = new HashMap<String, String>();
                atributos.put("username", username);
                atributos.put("password", password);

                try {



                    ApiService apiService =
                            new ApiService(
                                    baseUrl
                                    , ""
                            );

                    apiService.setHttpMethod("POST");
                    apiService.setParameters(atributos);

                    AsyncTask<Void, Void, String> execute = new ExecuteNetworkOperation(apiService);
                    execute.execute();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), "Error en el Login", Toast.LENGTH_LONG).show();
                }
            }
        });

        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivityRegister();
            }
        });
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String isValidCredentials;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperation(ApiService apiService) {
            this.apiService = apiService;
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
                isValidCredentials = apiService.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if(isValidCredentials != ""){
                userRegistered = new Gson().fromJson(isValidCredentials, User.class);
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Inicio de Sesión del usuario: "+userRegistered.getUsername(), Toast.LENGTH_SHORT).show();
                goToSecondActivity();
            }else{
                findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(MainActivity.this);

                dlgAlert.setMessage("Compruebe las credenciales y no deje los campos vacios");
                dlgAlert.setTitle("Error al Iniciar Sesión");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
            }
            // Hide the progress bar.

        }
    }

    /**
     * Open a new activity window.
     */
    private void goToSecondActivity() {
        Bundle bundle = new Bundle();
        bundle.putString("usuario", userRegistered.getUsername());
        bundle.putString("id", userRegistered.getId());
        bundle.putString("dni", userRegistered.getDni());
        bundle.putString("nombre", userRegistered.getNombre());
        bundle.putString("apellidos", userRegistered.getApellidos());
        bundle.putString("telefono", userRegistered.getTelefono());
        bundle.putString("poblacion", userRegistered.getPoblacion());
        bundle.putString("genero", userRegistered.getGenero());
        bundle.putString("naciemiento", userRegistered.getNacimiento());
        bundle.putString("rol", userRegistered.getRol());
        bundle.putString("ficha", userRegistered.getFicha());
        bundle.putString("token", userRegistered.getToken());

        Intent intent = new Intent(this, activityInicio.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void goToActivityRegister() {
        Intent intent = new Intent(this, registerActivity.class);
        startActivity(intent);
    }

}
