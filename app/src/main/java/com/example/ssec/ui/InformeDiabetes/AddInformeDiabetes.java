package com.example.ssec.ui.InformeDiabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ssec.R;
import com.example.ssec.models.Cubierto;
import com.example.ssec.models.InformeDiabetes;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class AddInformeDiabetes extends AppCompatActivity {

    private Spinner spinner_numeroControles;
    private CheckBox AntesComida;
    private CheckBox DespuesComida;
    private CheckBox PadecerEpisodio;
    private CheckBox Dormir;
    private CheckBox Levantarse;
    private Spinner spinner_nivelBajo;
    private Spinner spinner_frecuenciaBajo;
    private Spinner spinner_horarioBajo;
    private Spinner spinner_perdidaConocimiento;
    private Spinner spinner_nivelAlto;
    private Spinner spinner_frecuenciaAlto;
    private Spinner spinner_horarioAlto;
    private Spinner spinner_actividadFisica;
    private Spinner spinner_problemaDieta;
    private EditText editText_estadoGeneral;
    private Button button_send;
    HashMap<String, String> atributos;
    private String idFicha;
    private Intent intent;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_informe_diabetes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Añadir Informe Diabetes");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        intent = getIntent();
        Bundle extras = intent.getExtras();
        idFicha = extras.getString("id");
        token = extras.getString("token");

        spinner_numeroControles = (Spinner) findViewById(R.id.spinner_numeroControles);
        spinner_nivelBajo = (Spinner) findViewById(R.id.spinner_nivelBajo);
        spinner_frecuenciaBajo = (Spinner) findViewById(R.id.spinner_frecuenciaBajo);
        spinner_horarioBajo = (Spinner) findViewById(R.id.spinner_horarioBajo);
        spinner_perdidaConocimiento = (Spinner) findViewById(R.id.spinner_perdidaConocimiento);
        spinner_nivelAlto = (Spinner) findViewById(R.id.spinner_nivelAlto);
        spinner_frecuenciaAlto = (Spinner) findViewById(R.id.spinner_frecuenciaAlto);
        spinner_horarioAlto = (Spinner) findViewById(R.id.spinner_horarioAlto);
        spinner_actividadFisica = (Spinner) findViewById(R.id.spinner_actividadFisica);
        spinner_problemaDieta = (Spinner) findViewById(R.id.spinner_problemaDieta);
        editText_estadoGeneral = (EditText) findViewById(R.id.editText_estadoGeneral);
        button_send = (Button) findViewById(R.id.button_send);
        AntesComida = (CheckBox) findViewById(R.id.AntesComida);
        DespuesComida = (CheckBox) findViewById(R.id.DespuesComida);
        PadecerEpisodio = (CheckBox) findViewById(R.id.PadecerEpisodio);
        Dormir = (CheckBox) findViewById(R.id.Dormir);
        Levantarse = (CheckBox) findViewById(R.id.Levantarse);

        spinner_nivelBajo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    spinner_frecuenciaBajo.setEnabled(true);
                    spinner_horarioBajo.setEnabled(true);
                }else{
                    spinner_frecuenciaBajo.setEnabled(false);
                    spinner_horarioBajo.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_nivelAlto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    spinner_frecuenciaAlto.setEnabled(true);
                    spinner_horarioAlto.setEnabled(true);
                }else{
                    spinner_frecuenciaAlto.setEnabled(false);
                    spinner_horarioAlto.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatosInforme();
            }
        });
    }

    public boolean validarDatos(){
        atributos =  new HashMap<String, String>();
        String numeroControles = spinner_numeroControles.getSelectedItem().toString();
        String nivelBajo = spinner_nivelBajo.getSelectedItem().toString();
        String frecuenciaBajo;
        String horarioBajo;

        if(nivelBajo.equals("Sí")){
            nivelBajo = "Si";
            frecuenciaBajo = spinner_frecuenciaBajo.getSelectedItem().toString();
            horarioBajo = spinner_horarioAlto.getSelectedItem().toString();
        }else{
            frecuenciaBajo = "No";
            horarioBajo = "No";
        }

        String perdidaConocimiento = spinner_perdidaConocimiento.getSelectedItem().toString();
        if(perdidaConocimiento.equals("Sí")){
            perdidaConocimiento = "Si";
        }

        String nivelAlto = spinner_nivelAlto.getSelectedItem().toString();
        String frecuenciaAlto;
        String horarioAlto;

        if(nivelAlto.equals("Sí")){
            nivelAlto = "Si";
            frecuenciaAlto = spinner_frecuenciaBajo.getSelectedItem().toString();
            horarioAlto = spinner_horarioAlto.getSelectedItem().toString();
        }else{
            frecuenciaAlto = "No";
            horarioAlto = "No";
        }

        String actividadFisica = spinner_actividadFisica.getSelectedItem().toString();
        if(actividadFisica.equals("Sí")){
            actividadFisica = "Si";
        }

        String problemaDieta = spinner_problemaDieta.getSelectedItem().toString();
        if(problemaDieta.equals("Sí")){
            problemaDieta = "Si";
        }

        String estadoGeneral = editText_estadoGeneral.getText().toString();
        if(estadoGeneral.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha cubierto el estado general", Toast.LENGTH_LONG).show();
            editText_estadoGeneral.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_estadoGeneral.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        //la fecha se calcula en back
        atributos.put("ficha", idFicha);
        atributos.put("numeroControles", numeroControles);
        atributos.put("nivelBajo", nivelBajo);
        atributos.put("frecuenciaBajo", frecuenciaBajo);
        atributos.put("horarioBajo", horarioBajo);
        atributos.put("perdidaConocimiento", perdidaConocimiento);
        atributos.put("nivelAlto", nivelAlto);
        atributos.put("frecuenciaAlto", frecuenciaAlto);
        atributos.put("horarioAlto", horarioAlto);
        atributos.put("actividadFisica", actividadFisica);
        atributos.put("problemaDieta", problemaDieta);
        atributos.put("estadoGeneral", estadoGeneral);

        return  true;
    }

    public void checkMomentos(String idInformeDiabetes){
        if(AntesComida.isChecked()){
            enviarMomento(idInformeDiabetes, "AntesComida");
        }
        if(DespuesComida.isChecked()){
            enviarMomento(idInformeDiabetes, "DespuesComida");
        }
        if(PadecerEpisodio.isChecked()){
            enviarMomento(idInformeDiabetes, "PadecerEpisodio");
        }
        if(Levantarse.isChecked()){
            enviarMomento(idInformeDiabetes, "Levantarse");
        }
        if(Dormir.isChecked()){
            enviarMomento(idInformeDiabetes, "Dormir");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void enviarDatosInforme(){
        if(validarDatos()){
            //peticion al back
            try {

                ApiAuthenticationClient apiAuthenticationClient =
                        new ApiAuthenticationClient(
                                "http://10.0.2.2:8765/diabetes/add.json"
                                , token
                        );

                apiAuthenticationClient.setHttpMethod("POST");
                apiAuthenticationClient.setParameters(atributos);

                AsyncTask<Void, Void, String> execute = new AddInformeDiabetes.ExecuteNetworkOperationInforme(apiAuthenticationClient);
                execute.execute();

            } catch (Exception ex) {
            }
        }
    }

    public void enviarMomento( String idDiabetes, String aEnviar) {
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("diabetes", idDiabetes);
        atributos.put("momento", aEnviar);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/momentos/add.json"
                            , token
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new AddInformeDiabetes.ExecuteNetworkOperationMomentos(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public class ExecuteNetworkOperationMomentos extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datosCubierto;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationMomentos(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                datosCubierto = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

        }
    }

    public class ExecuteNetworkOperationInforme extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datos;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationInforme(ApiAuthenticationClient apiAuthenticationClient) {
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
                Toast.makeText(getApplicationContext(), "Se ha guardado con éxito el Informe", Toast.LENGTH_LONG).show();
                InformeDiabetes informeDiabetes = new Gson().fromJson(datos, InformeDiabetes.class);
                checkMomentos(informeDiabetes.getId());
            }else{
                Toast.makeText(getApplicationContext(), "No se ha guardado con éxito el Informe", Toast.LENGTH_LONG).show();
            }
            setResult(RESULT_OK, intent);
            AddInformeDiabetes.this.finish();
        }
    }
}
