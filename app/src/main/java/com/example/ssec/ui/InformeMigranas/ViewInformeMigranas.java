package com.example.ssec.ui.InformeMigranas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssec.R;
import com.example.ssec.models.Factor;
import com.example.ssec.models.InformeMigranas;
import com.example.ssec.models.Sintoma;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

import java.util.Iterator;

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
    private String token;

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
        tipoEpisodio = (TextView) findViewById(R.id.valuetipoEpisodio);
        intensidad = (TextView) findViewById(R.id.valueIntensidad);
        limitaciones = (TextView) findViewById(R.id.valueLimitaciones);
        despiertoNoche = (TextView) findViewById(R.id.valueDespiertoNoche);
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
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(ViewInformeMigranas.this, R.drawable.ic_file_download_black_24dp);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(ViewInformeMigranas.this, R.drawable.ic_edit_black_24dp);
        Drawable wrappedDrawable1 = DrawableCompat.wrap(unwrappedDrawable1);
        DrawableCompat.setTint(wrappedDrawable1, Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.download:
                Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
                break;
            default:
                return false;
        }
        return true;
    }

    public void getDatosInforme(){

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/migranas/view/"+idInforme+".json"
                            , token
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

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutMigranas);

        Iterator<Sintoma> iterador = informeAVer.getSintomas().iterator();

        if(iterador.hasNext()){
            TextView textView = new TextView(this);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.sintomas);
            params.setMargins(0,0,0,5);
            textView.setLayoutParams(params);
            textView.setId(View.generateViewId());

            StringBuilder str = new StringBuilder();

            while(iterador.hasNext()){
                Sintoma sintoma = iterador.next();
                if(sintoma.getSintomas().equals("Fotopsias_escotomas_hemianopsia_diplopia")){
                    str.append("Fotopsias, escotomas, hemianopsia, diplopía\n");
                } else if (sintoma.getSintomas().equals("Afasia")) {
                    str.append("Afasia\n");
                } else if (sintoma.getSintomas().equals("Sono_foto_osmofobia")) {
                    str.append("Sono/foto/osmofobia\n");
                } else if (sintoma.getSintomas().equals("SíntomasDisautonomicos")) {
                    str.append("Síntomas disautonómicos\n");
                }else if (sintoma.getSintomas().equals("Nauseas_Vomitos")) {
                    str.append("Náuseas/vómitos\n");
                }else if (sintoma.getSintomas().equals("Confusion_crisisComiciales_fiebre")) {
                    str.append("Confusión/crisis comiciales/fiebre\n");
                }else if (sintoma.getSintomas().equals("Hemiparesia_hemidisestesia")) {
                    str.append("Hemiparesia/hemidisestesia\n");
                }else{
                    str.append("Inestabilidad, vértigo\n");
                }
            }

            textView.setText(str.toString());
            relativeLayout.addView(textView);

            TextView sintomas = (TextView) findViewById(R.id.factores);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.BELOW, textView.getId());
            sintomas.setLayoutParams(params2);
        }


        RelativeLayout relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayoutMigranas);

        Iterator<Factor> iterador2 = informeAVer.getFactores().iterator();

        if(iterador2.hasNext()){
            TextView textView2 = new TextView(this);
            textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.BELOW, R.id.factores);
            params2.setMargins(0,0,0,5);
            textView2.setLayoutParams(params2);
            textView2.setId(View.generateViewId());

            StringBuilder str2 = new StringBuilder();

            while(iterador2.hasNext()){
                Factor factor = iterador2.next();
                if(factor.getFactores().equals("Estres")){
                    str2.append("Estrés\n");
                } else if (factor.getFactores().equals("FactoresHormonales")) {
                    str2.append("Factores hormonales (menstruación, anticonceptivos)\n");
                } else if (factor.getFactores().equals("ManiobrasValsalva")) {
                    str2.append("Maniobras de Valsalva (tos, coito…)\n");
                } else if (factor.getFactores().equals("EjercicioFisico")) {
                    str2.append("Ejercicio físico\n");
                } else if (factor.getFactores().equals("CambiosAtmosferico")) {
                    str2.append("Cambios atmosféricos\n");
                } else if (factor.getFactores().equals("MovimientoCefalicos")) {
                    str2.append("Movimientos cefálicos\n");
                } else if (factor.getFactores().equals("Dietas_alcohol")) {
                    str2.append("Dietas, alcohol\n");
                }else{
                    str2.append("CambiosPosturales\n");
                }
            }

            textView2.setText(str2.toString());
            relativeLayout2.addView(textView2);

            TextView estadoGeneral = (TextView) findViewById(R.id.estadoGeneral);
            RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params3.addRule(RelativeLayout.BELOW, textView2.getId());
            estadoGeneral.setLayoutParams(params3);
        }


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
