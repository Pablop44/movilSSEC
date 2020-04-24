package com.example.ssec.ui.Perfil;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ssec.R;
import com.example.ssec.aux.DatePickerFragment;
import com.example.ssec.models.Cubierto;
import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditUser extends AppCompatActivity {

    private User usuario;
    private String idUser;
    private EditText editText_dni;
    private EditText editText_username;
    private EditText editText_password;
    private EditText editText_email;
    private EditText editText_nombre;
    private EditText editText_apellidos;
    private EditText editText_telefono;
    private EditText editText_poblacion;
    private EditText editText_nacimiento;
    private Spinner spinner_genero;
    private Button button_editar;
    HashMap<String, String> atributos;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setTitle("Editar usuario");

        intent = getIntent();
        idUser = intent.getStringExtra("id");

        editText_dni = (EditText) findViewById(R.id.editText_dni);
        editText_username = (EditText) findViewById(R.id.editText_username);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_nombre = (EditText) findViewById(R.id.editText_nombre);
        editText_apellidos = (EditText) findViewById(R.id.editText_apellidos);
        editText_telefono = (EditText) findViewById(R.id.editText_telefono);
        editText_poblacion = (EditText) findViewById(R.id.editText_poblacion);
        editText_nacimiento = (EditText) findViewById(R.id.editText_nacimiento);
        spinner_genero = (Spinner) findViewById(R.id.spinner_genero);
        button_editar = (Button) findViewById(R.id.button_editar);

        editText_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        button_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarUser();
            }
        });


        getDatosUser();
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
        String[] valores = nacimiento.split("-");
        String nacimientoFinal = valores[2] + "-" + valores[1] + "-" + valores[0];
        String genero = spinner_genero.getSelectedItem().toString();

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

        if(!password.equals("")){
            if(!password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[#$^+=!*()@%&])(?=\\S+$).{8,}$")){
                Toast.makeText(getApplicationContext(), "La contraseña es incorrecta", Toast.LENGTH_LONG).show();
                editText_password.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
                return false;
            }else{
                atributos.put("password", password);
                editText_password.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
            }
        }

        if(genero.equals("Género")){
            Toast.makeText(getApplicationContext(), "Selecciona un género", Toast.LENGTH_LONG).show();
            spinner_genero.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            spinner_genero.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!telefono.matches("^([9,7,6]{1})+([0-9]{8})$") || telefono.equals("")){
            Toast.makeText(getApplicationContext(), "El teléfono es incorrecto", Toast.LENGTH_LONG).show();
            editText_telefono.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_telefono.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(!poblacion.matches("[a-zA-Z\\s]+") || poblacion.equals("")){
            Toast.makeText(getApplicationContext(), "Los apellidos son incorrectos", Toast.LENGTH_LONG).show();
            editText_poblacion.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_poblacion.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        if(genero == "Hombre"){
            genero = "Male";
        }else if(genero == "Mujer"){
            genero = "Female";
        }

        atributos.put("id", idUser);
        atributos.put("dni", dni);
        atributos.put("username", username);
        atributos.put("email", email);
        atributos.put("nombre", nombre);
        atributos.put("apellidos", apellidos);
        atributos.put("telefono", telefono);
        atributos.put("poblacion", poblacion);
        atributos.put("nacimiento", nacimientoFinal);
        atributos.put("genero", genero);

        return true;
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

    public void editarUser(){
        if(validarDatos()){
            try {

                ApiAuthenticationClient apiAuthenticationClient =
                        new ApiAuthenticationClient(
                                "http://10.0.2.2:8765/user/editarUser.json"
                                , ""
                                , ""
                        );

                apiAuthenticationClient.setHttpMethod("POST");
                apiAuthenticationClient.setParameters(atributos);

                AsyncTask<Void, Void, String> execute = new EditUser.ExecuteNetworkOperationEditar(apiAuthenticationClient);
                execute.execute();

            } catch (Exception ex) {
            }
        }
    }

    public void actualizarDatos(){
        editText_dni.setText(usuario.getDni());
        editText_username.setText(usuario.getUsername());
        editText_email.setText(usuario.getEmail());
        editText_nombre.setText(usuario.getNombre());
        editText_apellidos.setText(usuario.getApellidos());
        editText_telefono.setText(usuario.getTelefono());
        editText_poblacion.setText(usuario.getPoblacion());
        editText_nacimiento.setText(usuario.getNacimiento());
        if(usuario.getGenero().equals("Male")){
            spinner_genero.setSelection(1);
        }else{
            spinner_genero.setSelection(2);;
        }
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

    public void getDatosUser() {
        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/user/view/"+idUser+".json"
                            , ""
                            , ""
                    );

            AsyncTask<Void, Void, String> execute = new EditUser.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();
        } catch (Exception ex) {
        }
    }


    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datosUser;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperation(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                datosUser = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            usuario = new Gson().fromJson(datosUser, User.class);
            actualizarDatos();
        }
    }

    public class ExecuteNetworkOperationEditar extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datos;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationEditar(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                datos = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(datos != ""){
                Toast.makeText(getApplicationContext(), "Se ha editado con éxito", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                EditUser.this.finish();
            }else{
                Toast.makeText(getApplicationContext(), "Fallo al editar", Toast.LENGTH_LONG).show();
            }
        }
    }
}
