package com.example.ssec.ui.InformeMigranas;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ssec.R;
import com.example.ssec.servicios.ApiAuthenticationClient;

import java.util.HashMap;

public class AddInformeMigranas extends AppCompatActivity {

    private Spinner spinner_frecuencia;
    private Spinner spinner_duracion;
    private Spinner spinner_horario;
    private Spinner spinner_finalizacion;
    private Spinner spinner_tipoEpisodio;
    private Spinner spinner_intensidad;
    private Spinner spinner_limitaciones;
    private Spinner spinner_despiertoNoche;
    private EditText editText_estadoGeneral;
    private CheckBox Nauseas_Vomitos;
    private CheckBox Sono_foto_osmofobia;
    private CheckBox Fotopsias_escotomas_hemianopsia_diplopia;
    private CheckBox Hemiparesia_hemidisestesia;
    private CheckBox Inestabilidad_vertigo;
    private CheckBox SíntomasDisautonomicos;
    private CheckBox Afasia;
    private CheckBox Confusion_crisisComiciales_fiebre;
    private CheckBox Estres;
    private CheckBox EjercicioFisico;
    private CheckBox FactoresHormonales;
    private CheckBox Dietas_alcohol;
    private CheckBox CambiosAtmosferico;
    private CheckBox CambiosPosturales;
    private CheckBox MovimientoCefalicos;
    private CheckBox ManiobrasValsalva;
    private Button button_send;
    HashMap<String, String> atributos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_informe_migranas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Añadir Informe Migrañas");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        spinner_frecuencia = (Spinner) findViewById(R.id.spinner_frecuencia);
        spinner_duracion = (Spinner) findViewById(R.id.spinner_duracion);
        spinner_horario = (Spinner) findViewById(R.id.spinner_horario);
        spinner_finalizacion = (Spinner) findViewById(R.id.spinner_finalizacion);
        spinner_tipoEpisodio = (Spinner) findViewById(R.id.spinner_tipoEpisodio);
        spinner_intensidad = (Spinner) findViewById(R.id.spinner_intensidad);
        spinner_limitaciones = (Spinner) findViewById(R.id.spinner_limitaciones);
        spinner_despiertoNoche = (Spinner) findViewById(R.id.spinner_despiertoNoche);
        editText_estadoGeneral = (EditText) findViewById(R.id.editText_estadoGeneral);
        button_send = (Button) findViewById(R.id.button_send);
        Nauseas_Vomitos = (CheckBox) findViewById(R.id.Nauseas_Vomitos);
        Sono_foto_osmofobia = (CheckBox) findViewById(R.id.Sono_foto_osmofobia);
        Fotopsias_escotomas_hemianopsia_diplopia = (CheckBox) findViewById(R.id.Fotopsias_escotomas_hemianopsia_diplopia);
        Hemiparesia_hemidisestesia = (CheckBox) findViewById(R.id.Hemiparesia_hemidisestesia);
        Inestabilidad_vertigo = (CheckBox) findViewById(R.id.Inestabilidad_vertigo);
        SíntomasDisautonomicos = (CheckBox) findViewById(R.id.SíntomasDisautonomicos);
        Afasia = (CheckBox) findViewById(R.id.Afasia);
        Confusion_crisisComiciales_fiebre = (CheckBox) findViewById(R.id.Confusion_crisisComiciales_fiebre);
        Estres = (CheckBox) findViewById(R.id.Estres);
        EjercicioFisico = (CheckBox) findViewById(R.id.EjercicioFisico);
        FactoresHormonales = (CheckBox) findViewById(R.id.FactoresHormonales);
        Dietas_alcohol = (CheckBox) findViewById(R.id.Dietas_alcohol);
        CambiosAtmosferico = (CheckBox) findViewById(R.id.CambiosAtmosferico);
        MovimientoCefalicos = (CheckBox) findViewById(R.id.MovimientoCefalicos);
        CambiosPosturales = (CheckBox) findViewById(R.id.CambiosPosturales);
        ManiobrasValsalva = (CheckBox) findViewById(R.id.ManiobrasValsalva);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatosInforme();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public boolean validarDatos(){
        String frecuencia = spinner_frecuencia.getSelectedItem().toString();
        String duracion = spinner_duracion.getSelectedItem().toString();
        String horario = spinner_horario.getSelectedItem().toString();
        String finalizacion = spinner_finalizacion.getSelectedItem().toString();
        String tipoEpisodio = spinner_tipoEpisodio.getSelectedItem().toString();
        String intensidad = spinner_intensidad.getSelectedItem().toString();
        String limitaciones = spinner_limitaciones.getSelectedItem().toString();
        if(limitaciones.equals("Sí")){
            limitaciones = "Si";
        }
        String despiertoNoche = spinner_despiertoNoche.getSelectedItem().toString();
        if(despiertoNoche.equals("Sí")){
            despiertoNoche = "Si";
        }
        String estadoGeneral = editText_estadoGeneral.getText().toString();
        if(estadoGeneral.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha cubierto el estado general", Toast.LENGTH_LONG).show();
            editText_estadoGeneral.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_estadoGeneral.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }
        return true;
    }

    public void checkSintomas(String idInformeMigranas){
        if(Nauseas_Vomitos.isChecked()){
            enviarSintoma(idInformeMigranas, "Nauseas_Vomitos");
        }
        if(Sono_foto_osmofobia.isChecked()){
            enviarSintoma(idInformeMigranas, "Sono_foto_osmofobia");
        }
        if(Fotopsias_escotomas_hemianopsia_diplopia.isChecked()){
            enviarSintoma(idInformeMigranas, "Fotopsias_escotomas_hemianopsia_diplopia");
        }
        if(Hemiparesia_hemidisestesia.isChecked()){
            enviarSintoma(idInformeMigranas, "Hemiparesia_hemidisestesia");
        }
        if(Inestabilidad_vertigo.isChecked()){
            enviarSintoma(idInformeMigranas, "Inestabilidad_vertigo");
        }
        if(SíntomasDisautonomicos.isChecked()){
            enviarSintoma(idInformeMigranas, "SíntomasDisautonomicos");
        }
        if(Afasia.isChecked()){
            enviarSintoma(idInformeMigranas, "Afasia");
        }
        if(Confusion_crisisComiciales_fiebre.isChecked()){
            enviarSintoma(idInformeMigranas, "Confusion_crisisComiciales_fiebre");
        }
    }

    public void checkFactores(String idInformeMigranas){
        if(Estres.isChecked()){
            enviarFactor(idInformeMigranas, "Estres");
        }
        if(EjercicioFisico.isChecked()){
            enviarFactor(idInformeMigranas, "EjercicioFisico");
        }
        if(FactoresHormonales.isChecked()){
            enviarFactor(idInformeMigranas, "FactoresHormonales");
        }
        if(Dietas_alcohol.isChecked()){
            enviarFactor(idInformeMigranas, "Dietas_alcohol");
        }
        if(CambiosAtmosferico.isChecked()){
            enviarFactor(idInformeMigranas, "CambiosAtmosferico");
        }
        if(MovimientoCefalicos.isChecked()){
            enviarFactor(idInformeMigranas, "MovimientoCefalicos");
        }
        if(CambiosPosturales.isChecked()){
            enviarFactor(idInformeMigranas, "CambiosPosturales");
        }
        if(ManiobrasValsalva.isChecked()){
            enviarFactor(idInformeMigranas, "ManiobrasValsalva");
        }
    }

    public void enviarDatosInforme(){
        if(validarDatos()){
            try {

                ApiAuthenticationClient apiAuthenticationClient =
                        new ApiAuthenticationClient(
                                "http://10.0.2.2:8765/migranas/add.json"
                                , ""
                                , ""
                        );

                apiAuthenticationClient.setHttpMethod("POST");
                apiAuthenticationClient.setParameters(atributos);

                AsyncTask<Void, Void, String> execute = new AddInformeMigranas.ExecuteNetworkOperationInforme(apiAuthenticationClient);
                execute.execute();

            } catch (Exception ex) {
            }
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
            Toast.makeText(getApplicationContext(), datos, Toast.LENGTH_LONG).show();
            //checkFactores(datos);
            //checkSintomas(datos);
        }
    }

    public void enviarFactor( String idMigranas, String aEnviar) {
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("migranas", idMigranas);
        atributos.put("factores", aEnviar);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/factores/add.json"
                            , ""
                            , ""
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new AddInformeMigranas.ExecuteNetworkOperationFactor(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public class ExecuteNetworkOperationFactor extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datosCubierto;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationFactor(ApiAuthenticationClient apiAuthenticationClient) {
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

    public void enviarSintoma( String idMigranas, String aEnviar) {
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("migranas", idMigranas);
        atributos.put("sintomas", aEnviar);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/migranas/add.json"
                            , ""
                            , ""
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new AddInformeMigranas.ExecuteNetworkOperationSintoma(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public class ExecuteNetworkOperationSintoma extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datosCubierto;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationSintoma(ApiAuthenticationClient apiAuthenticationClient) {
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
}
