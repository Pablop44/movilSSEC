package com.example.ssec.ui.InformeMigranas;

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
import com.example.ssec.models.InformeMigranas;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

public class ViewInformeMigranas extends AppCompatActivity {

    private String idInforme;
    private InformeMigranas informeAVer;
    private TextView frecuencia;
    private TextView duracion;
    private TextView horario;
    private TextView finalizacion;
    private TextView tipoEpisodio;
    private TextView intensidad;
    private TextView limitaciones;
    private TextView despiertoNoche;
    private TextView estadoGeneral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_informe_migranas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        frecuencia = (TextView) findViewById(R.id.valueFrecuencia);
        duracion = (TextView) findViewById(R.id.valueDuracion);
        horario = (TextView) findViewById(R.id.valueHorario);
        finalizacion = (TextView) findViewById(R.id.valueFinalizacion);
        tipoEpisodio = (TextView) findViewById(R.id.tipoEpisodio);
        intensidad = (TextView) findViewById(R.id.valueIntensidad);
        limitaciones = (TextView) findViewById(R.id.valueLimitaciones);
        despiertoNoche = (TextView) findViewById(R.id.valueDespiertoNoche);
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
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(ViewInformeMigranas.this, R.drawable.ic_file_download_black_24dp);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(ViewInformeMigranas.this, R.drawable.ic_edit_black_24dp);
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, Color.WHITE);
        return true;
    }

    public void getDatosInforme(){

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/migranas/view/"+idInforme+".json"
                            , ""
                            , ""
                    );

            apiAuthenticationClient.setHttpMethod("GET");

            AsyncTask<Void, Void, String> execute = new ViewInformeMigranas.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void actualizarDatos(){

        frecuencia.setText(informeAVer.getFrecuencia());
        duracion.setText(informeAVer.getDuracion());
        horario.setText(informeAVer.getHorario());
        finalizacion.setText(informeAVer.getFinalizacion());
        tipoEpisodio.setText(informeAVer.getTipoEpisodio());
        intensidad.setText(informeAVer.getIntensidad());
        limitaciones.setText(informeAVer.getLimitaciones());
        despiertoNoche.setText(informeAVer.getDespiertoNoche());
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

            informeAVer = new Gson().fromJson(datos, InformeMigranas.class);
            String[] fechaHora = informeAVer.getFechaHora();
            getSupportActionBar().setTitle("Migrañas - "+fechaHora[0]);

            actualizarDatos();
        }
    }
}
