package com.example.ssec.ui.InformeDiabetes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;

import android.annotation.SuppressLint;
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
import com.example.ssec.models.InformeDiabetes;
import com.example.ssec.models.Momento;
import com.example.ssec.servicios.ApiService;
import com.google.gson.Gson;

import java.util.Iterator;

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
    private String token;

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
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(ViewInformeDiabetes.this, R.drawable.ic_file_download_black_24dp);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);

        Drawable unwrappedDrawable1 = AppCompatResources.getDrawable(ViewInformeDiabetes.this, R.drawable.ic_edit_black_24dp);
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

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/diabetes/view/"+idInforme+".json"
                            , token
                    );

            apiService.setHttpMethod("GET");

            AsyncTask<Void, Void, String> execute = new ViewInformeDiabetes.ExecuteNetworkOperation(apiService);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    @SuppressLint("ResourceType")
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

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutDiabetes);

        Iterator<Momento> iterador = informeAVer.getMomentos().iterator();

        if(iterador.hasNext()){
            TextView textView = new TextView(this);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.BELOW, R.id.momentos);
            params.setMargins(0,0,0,5);
            textView.setLayoutParams(params);
            textView.setId(View.generateViewId());

            StringBuilder str = new StringBuilder();

            while(iterador.hasNext()){
                Momento momento = iterador.next();
                if(momento.getMomento().equals("Levantarse")){
                    str.append("Al levantarse\n");
                } else if (momento.getMomento().equals("PadecerEpisodio")) {
                    str.append("Al padecer un episodio\n");
                } else if (momento.getMomento().equals("DespuesComida")) {
                    str.append("Después de la comida\n");
                } else if (momento.getMomento().equals("AntesComida")) {
                    str.append("Antes de la comida\n");
                }else{
                    str.append("Al irse a dormir\n");
                }
            }

            textView.setText(str.toString());
            relativeLayout.addView(textView);

            TextView texto = (TextView) findViewById(R.id.nivelBajo);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params2.addRule(RelativeLayout.BELOW, textView.getId());
            texto.setLayoutParams(params2);
        }else{
            TextView texto = (TextView) findViewById(R.id.momentos);
            texto.setText("");
        }


        actividadFisica.setText(informeAVer.getActividadFisica());
        problemaDieta.setText(informeAVer.getProblemaDieta());
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

            informeAVer = new Gson().fromJson(datos, InformeDiabetes.class);
            String[] fechaHora = informeAVer.getFechaHora();
            getSupportActionBar().setTitle("Diabetes - "+fechaHora[0]);

            actualizarDatos();

        }
    }
}
