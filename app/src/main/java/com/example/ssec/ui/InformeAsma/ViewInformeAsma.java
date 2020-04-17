package com.example.ssec.ui.InformeAsma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.ssec.R;
import com.example.ssec.models.InformeAsma;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

import java.util.HashMap;

public class ViewInformeAsma extends AppCompatActivity {

    private String idInforme;
    private InformeAsma informeAVer;
    private TextView calidadSueno;
    private TextView dificultadRespirar;
    private TextView tos;
    private TextView gravedadTos;
    private TextView limitaciones;
    private TextView medicacion;
    private TextView silbidos;
    private TextView espirometria;
    private TextView factores;
    private TextView estadoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_informe_asma);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        calidadSueno = (TextView) findViewById(R.id.valueCalidadSueno);
        dificultadRespirar = (TextView) findViewById(R.id.valueDificultadRespirar);
        tos = (TextView) findViewById(R.id.valueTos);
        gravedadTos = (TextView) findViewById(R.id.valueGravedadTos);
        limitaciones = (TextView) findViewById(R.id.valueLimitaciones);
        silbidos = (TextView) findViewById(R.id.valueSilbidos);
        espirometria = (TextView) findViewById(R.id.valueEspirometria);
        medicacion = (TextView) findViewById(R.id.valueMedicacion);
        factores = (TextView) findViewById(R.id.valueFactores);
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
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(ViewInformeAsma.this, R.drawable.ic_file_download_black_24dp);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(ViewInformeAsma.this, R.drawable.ic_edit_black_24dp);
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, Color.WHITE);
        return true;
    }

    public void getDatosInforme(){

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/asma/view/"+idInforme+".json"
                            , ""
                            , ""
                    );

            apiAuthenticationClient.setHttpMethod("GET");

            AsyncTask<Void, Void, String> execute = new ViewInformeAsma.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void actualizarDatos(){

        calidadSueno.setText(informeAVer.getCalidadSueno());
        dificultadRespirar.setText(informeAVer.getDificultadRespirar());
        tos.setText(informeAVer.getTos());

        if(informeAVer.getTos().equals("No")){
            gravedadTos.setText("No ha presentado Tos");
        }else{
            gravedadTos.setText(informeAVer.getGravedadTos());
        }

        medicacion.setText(informeAVer.getUsoMedicacion());
        limitaciones.setText(informeAVer.getLimitaciones());
        silbidos.setText(informeAVer.getSilbidos());
        espirometria.setText(informeAVer.getEspirometria());
        factores.setText(informeAVer.getFactoresCrisis());
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

            informeAVer = new Gson().fromJson(datos, InformeAsma.class);
            String[] fechaHora = informeAVer.getFechaHora();
            getSupportActionBar().setTitle("Asma - "+fechaHora[0]);

            actualizarDatos();

        }
    }
}
