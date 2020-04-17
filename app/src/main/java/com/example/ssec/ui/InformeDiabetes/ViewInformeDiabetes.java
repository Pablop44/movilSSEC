package com.example.ssec.ui.InformeDiabetes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.TestLooperManager;
import android.view.Menu;
import android.widget.TextView;

import com.example.ssec.R;
import com.example.ssec.models.InformeDiabetes;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

public class ViewInformeDiabetes extends AppCompatActivity {

    private String idInforme;
    private InformeDiabetes informeAVer;
    private TextView numeroControles;
    private TextView nivelBajo;
    private TextView frecuenciaBajo;
    private TextView horarioBajo;
    private TextView perdidaConocimiento;
    private TextView nivelAlto;
    private TextView frecuenciaAlto;
    private TextView horarioAlto;
    private TextView actividadFisica;
    private TextView problemaDieta;
    private TextView estadoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_informe_diabetes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        numeroControles = (TextView) findViewById(R.id.valueNumeroControles);
        nivelBajo = (TextView) findViewById(R.id.valueNivelBajo);
        frecuenciaBajo = (TextView) findViewById(R.id.valueFrecuenciaBajo);
        horarioBajo = (TextView) findViewById(R.id.valueHorarioBajo);
        perdidaConocimiento = (TextView) findViewById(R.id.valuePerdidaConocimiento);
        nivelAlto = (TextView) findViewById(R.id.valueNivelAlto);
        frecuenciaAlto = (TextView) findViewById(R.id.valueFrecuenciaAlto);
        horarioAlto = (TextView) findViewById(R.id.valueHorarioAlto);
        actividadFisica = (TextView) findViewById(R.id.valueActividadFisica);
        problemaDieta = (TextView) findViewById(R.id.valueProblemaDieta);
        estadoGeneral = (TextView) findViewById(R.id.valueEstadoGeneral);

        Intent intent = getIntent();
        idInforme = intent.getStringExtra("id");
        getDatosInforme();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_informe_menu, menu);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(ViewInformeDiabetes.this, R.drawable.ic_file_download_black_24dp);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(ViewInformeDiabetes.this, R.drawable.ic_edit_black_24dp);
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, Color.WHITE);
        return true;
    }

    public void getDatosInforme(){

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/diabetes/view/"+idInforme+".json"
                            , ""
                            , ""
                    );

            apiAuthenticationClient.setHttpMethod("GET");

            AsyncTask<Void, Void, String> execute = new ViewInformeDiabetes.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void actualizarDatos(){

        numeroControles.setText(informeAVer.getNumeroControles());
        nivelBajo.setText(informeAVer.getNivelBajo());

        if(informeAVer.getNivelBajo().equals("No")){
            frecuenciaBajo.setText("No ha presentado niveles bajos de azúcar");
        }else{
            frecuenciaBajo.setText(informeAVer.getFrecuenciaBajo());
        }

        if(informeAVer.getNivelBajo().equals("No")){
            horarioBajo.setText("No ha presentado niveles bajos de azúcar");
        }else{
            horarioBajo.setText(informeAVer.getHorarioBajo());
        }

        perdidaConocimiento.setText(informeAVer.getPerdidaConocimiento());

        nivelAlto.setText(informeAVer.getNivelAlto());

        if(informeAVer.getNivelAlto().equals("No")){
            frecuenciaAlto.setText("No ha presentado niveles altos de azúcar");
        }else{
            frecuenciaAlto.setText(informeAVer.getFrecuenciaAlto());
        }

        if(informeAVer.getNivelAlto().equals("No")){
            horarioAlto.setText("No ha presentado niveles altos de azúcar");
        }else{
            horarioAlto.setText(informeAVer.getHorarioAlto());
        }

        actividadFisica.setText(informeAVer.getActividadFisica());
        problemaDieta.setText(informeAVer.getProblemaDieta());
        estadoGeneral.setText(informeAVer.getEstadoGeneral());
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datos;

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

                datos = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            informeAVer = new Gson().fromJson(datos, InformeDiabetes.class);
            String[] fechaHora = informeAVer.getFechaHora();
            getSupportActionBar().setTitle("Diabetes - "+fechaHora[0]);

            actualizarDatos();

        }
    }
}
