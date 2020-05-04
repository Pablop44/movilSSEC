package com.example.ssec;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
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
import com.example.ssec.servicios.ApiAuthenticationClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    HashMap<String, String> atributos;
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
               registrarUser();
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public boolean validarDatos(){
        atributos =  new HashMap<String, String>();

        String dni = editText_dni.getText().toString();
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();
        String email = editText_email.getText().toString();
        String nombre = editText_nombre.getText().toString();
        String apellidos = editText_apellidos.getText().toString();
        String telefono = editText_telefono.getText().toString();
        String poblacion = editText_poblacion.getText().toString();
        String nacimiento = editText_nacimiento.getText().toString();

        String genero = spGenero.getSelectedItem().toString();

        if(!validarNIF(dni) || dni.equals("")){
            Toast.makeText(getApplicationContext(), "El NIF es incorrecto o es vacío", Toast.LENGTH_LONG).show();
            editText_dni.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_dni.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!username.matches("[A-Za-z0-9ñÑáéíóúÁÉÍÓÚ]+") || username.equals("")){
            Toast.makeText(getApplicationContext(), "El nombre de usuario es incorrecto o vacío", Toast.LENGTH_LONG).show();
            editText_username.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_username.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!nombre.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+") || nombre.equals("")){
            Toast.makeText(getApplicationContext(), "El nombre es incorrecto", Toast.LENGTH_LONG).show();
            editText_nombre.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_nombre.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!apellidos.matches("[a-zA-ZñÑáéíóúÁÉÍÓÚ\\s]+") || apellidos.equals("")){
            Toast.makeText(getApplicationContext(), "Los apellidos son incorrectos", Toast.LENGTH_LONG).show();
            editText_apellidos.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_apellidos.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!email.matches("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$") || email.equals("")){
            Toast.makeText(getApplicationContext(), "El email es incorrecto", Toast.LENGTH_LONG).show();
            editText_email.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_email.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#$^+=!*()@%&])(?=\\S+$).{8,}$") || password.equals("")){
            Toast.makeText(getApplicationContext(), "La contraseña es incorrecta", Toast.LENGTH_LONG).show();
            editText_password.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            atributos.put("password", password);
            editText_password.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(genero.equals("Género")){
            Toast.makeText(getApplicationContext(), "Selecciona un género", Toast.LENGTH_LONG).show();
            spGenero.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            spGenero.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!telefono.matches("^([9,7,6]{1})+([0-9]{8})$") || telefono.equals("")){
            Toast.makeText(getApplicationContext(), "El teléfono es incorrecto", Toast.LENGTH_LONG).show();
            editText_telefono.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_telefono.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!poblacion.matches("[a-zA-Z\\s]+") || poblacion.equals("")){
            Toast.makeText(getApplicationContext(), "La poblacion son incorrectos", Toast.LENGTH_LONG).show();
            editText_poblacion.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_poblacion.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(nacimiento.equals("")){
            Toast.makeText(getApplicationContext(), "La fecha de nacimiento es incorrecta", Toast.LENGTH_LONG).show();
            return false;
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            Calendar c = Calendar.getInstance();

            Date date = null;
            try {
                date = sdf.parse(nacimiento);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if(c.getTime().compareTo(date) < 0){
                Toast.makeText(getApplicationContext(), "La fecha es incorrecta", Toast.LENGTH_LONG).show();
                editText_nacimiento.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                return false;
            }else{
                editText_nacimiento.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
            }
            String[] valores = nacimiento.split("-");
            String nacimientoFinal = valores[2] + "-" + valores[1] + "-" + valores[0];
            atributos.put("nacimiento", nacimientoFinal);
        }

        if(genero == "Hombre"){
            genero = "Male";
        }else if(genero == "Mujer"){
            genero = "Female";
        }

        atributos.put("dni", dni);
        atributos.put("username", username);
        atributos.put("email", email);
        atributos.put("nombre", nombre);
        atributos.put("apellidos", apellidos);
        atributos.put("telefono", telefono);
        atributos.put("poblacion", poblacion);
        atributos.put("genero", genero);

        return true;
    }

    public void registrarUser(){
        if(validarDatos()) {
            try {

                ApiAuthenticationClient apiAuthenticationClient =
                        new ApiAuthenticationClient(
                                baseUrl
                                , ""
                        );

                apiAuthenticationClient.setHttpMethod("POST");
                apiAuthenticationClient.setParameters(atributos);

                AsyncTask<Void, Void, String> execute = new registerActivity.ExecuteNetworkOperation(apiAuthenticationClient);
                execute.execute();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), "Fallo al registrarse", Toast.LENGTH_LONG).show();
            }
        }
    }

    private static boolean validarNIF(String nif) {
        boolean correcto = false;
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);
        if (matcher.matches()) {
            String letra = matcher.group(2);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;
            String reference = letras.substring(index, index + 1);
            if (reference.equalsIgnoreCase(letra)) {
                correcto = true;
            } else {
                correcto = false;
            }
        } else {
            correcto = false;
        }
        return correcto;
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


                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

                Calendar c = Calendar.getInstance();

                Date date = null;
                try {
                    date = sdf.parse(selectedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(c.getTime().compareTo(date) < 0){
                    editText_nacimiento.setText(selectedDate);
                    editText_nacimiento.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                }else{
                    editText_nacimiento.setText(selectedDate);
                    editText_nacimiento.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
                }

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

                dlgAlert.setMessage("Vaya a su correo para activar su cuenta");
                dlgAlert.setTitle("Éxito");
                dlgAlert.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finalizarActividad();
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();


            }else {

                Toast.makeText(getApplicationContext(), "Error al crear el usuario, comprueba los datos", Toast.LENGTH_LONG).show();
                findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);

            }
        }
    }

    public void finalizarActividad(){
        finish();
    }

}


