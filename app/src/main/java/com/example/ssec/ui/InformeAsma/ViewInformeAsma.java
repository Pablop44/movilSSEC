package com.example.ssec.ui.InformeAsma;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.ssec.R;
import com.example.ssec.models.InformeAsma;
import com.example.ssec.servicios.ApiService;
import com.google.gson.Gson;

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
    private String token;

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
        token = intent.getStringExtra("token");
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                break;
            default:
                return false;
        }
        return true;
    }

    public void getDatosInforme(){

        try {

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/asma/view/"+idInforme+".json"
                            , token
                    );

            apiService.setHttpMethod("GET");

            AsyncTask<Void, Void, String> execute = new ViewInformeAsma.ExecuteNetworkOperation(apiService);
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

        private ApiService apiService;
        private String datos;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperation(ApiService apiService) {
            this.apiService = apiService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                datos = apiService.execute();

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
            getSupportActionBar().setTitle("Asma - " + fechaHora[0]);

            actualizarDatos();
        }
    }
}

