package com.example.ssec.ui.InformeAsma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ssec.R;
import com.example.ssec.servicios.ApiService;

import java.util.HashMap;

public class AddInformeAsma extends AppCompatActivity {

    private Spinner spinner_calidadSueno;
    private Spinner spinner_dificultadRespirar;
    private Spinner spinner_tos;
    private Spinner spinner_gravedadTos;
    private Spinner spinner_limitaciones;
    private Spinner spinner_silbidos;
    private Spinner spinner_medicacion;
    private EditText editText_espirometria;
    private EditText editText_factores;
    private EditText editText_estadoGeneral;
    private Button button_send;
    HashMap<String, String> atributos;
    private String idFicha;
    private Intent intent;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_informe_asma);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Añadir Informe Asma");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        intent = getIntent();
        Bundle extras = intent.getExtras();
        idFicha = extras.getString("id");
        token = extras.getString("token");

        spinner_calidadSueno = (Spinner) findViewById(R.id.spinner_calidadSueno);
        spinner_dificultadRespirar = (Spinner) findViewById(R.id.spinner_dificultadRespirar);
        spinner_tos = (Spinner) findViewById(R.id.spinner_tos);
        spinner_gravedadTos = (Spinner) findViewById(R.id.spinner_gravedadTos);
        spinner_limitaciones = (Spinner) findViewById(R.id.spinner_limitaciones);
        spinner_silbidos = (Spinner) findViewById(R.id.spinner_silbidos);
        spinner_medicacion = (Spinner) findViewById(R.id.spinner_medicacion);
        editText_espirometria = (EditText) findViewById(R.id.editText_espirometria);
        editText_factores = (EditText) findViewById(R.id.editText_factores);
        editText_estadoGeneral = (EditText) findViewById(R.id.editText_estadoGeneral);
        button_send = (Button) findViewById(R.id.button_send);

        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatosInforme();
            }
        });

        spinner_tos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    spinner_gravedadTos.setEnabled(true);
                }else{
                    spinner_gravedadTos.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        String calidaSueno = spinner_calidadSueno.getSelectedItem().toString();
        String dificultadRespirar = spinner_dificultadRespirar.getSelectedItem().toString();
        if(dificultadRespirar.equals("Sí")){
            dificultadRespirar = "Si";
        }
        String tos = spinner_tos.getSelectedItem().toString();
        String gravedadTos;

        if(tos.equals("Sí")){
            tos = "Si";
            gravedadTos = spinner_gravedadTos.getSelectedItem().toString();
        }else{
            gravedadTos = "No";
        }
        String limitaciones = spinner_limitaciones.getSelectedItem().toString();
        if(limitaciones.equals("Sí")){
            limitaciones = "Si";
        }
        String silbidos = spinner_silbidos.getSelectedItem().toString();
        if(silbidos.equals("Sí")){
            silbidos = "Si";
        }
        String usoMedicacion = spinner_medicacion.getSelectedItem().toString();
        if(usoMedicacion.equals("Sí")){
            usoMedicacion = "Si";
        }
        String espirometria = editText_espirometria.getText().toString();
        if(espirometria.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha cubierto la espirometria", Toast.LENGTH_LONG).show();
            editText_espirometria.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_espirometria.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }
        String factores = editText_factores.getText().toString();
        if(factores.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha cubierto los factores", Toast.LENGTH_LONG).show();
            editText_factores.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_factores.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }
        String estadoGeneral = editText_estadoGeneral.getText().toString();
        if(estadoGeneral.equals("")){
            Toast.makeText(getApplicationContext(), "No se ha cubierto el estado general", Toast.LENGTH_LONG).show();
            editText_estadoGeneral.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.holo_red_light), PorterDuff.Mode.SRC_ATOP);
            return false;
        }else{
            editText_estadoGeneral.getBackground().mutate().setColorFilter(getResources().getColor(android.R.color.darker_gray), PorterDuff.Mode.SRC_ATOP);
        }

        //la fecha se calcula en el back
        atributos.put("ficha", idFicha);
        atributos.put("calidadSueno", calidaSueno);
        atributos.put("dificultadRespirar", dificultadRespirar);
        atributos.put("tos", tos);
        atributos.put("gravedadTos", gravedadTos);
        atributos.put("limitaciones", limitaciones);
        atributos.put("silbidos", silbidos);
        atributos.put("usoMedicacion", usoMedicacion);
        atributos.put("espirometria", espirometria);
        atributos.put("factoresCrisis", factores);
        atributos.put("estadoGeneral", estadoGeneral);

        return true;
    }

    public void enviarDatosInforme(){
        if(validarDatos()){
            try {

                ApiService apiService =
                        new ApiService(
                                "http://10.0.2.2:8765/asma/add.json"
                                , token
                        );

                apiService.setHttpMethod("POST");
                apiService.setParameters(atributos);

                AsyncTask<Void, Void, String> execute = new AddInformeAsma.ExecuteNetworkOperationInforme(apiService);
                execute.execute();

            } catch (Exception ex) {
            }
        }
    }

    public class ExecuteNetworkOperationInforme extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String datos;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationInforme(ApiService apiService) {
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
            if(datos != ""){
                Toast.makeText(getApplicationContext(), "Se ha guardado con éxito el Informe", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, intent);
                AddInformeAsma.this.finish();
            }else{
                Toast.makeText(getApplicationContext(), "No se ha guardado con éxito el Informe", Toast.LENGTH_LONG).show();
            }
        }
    }
}
