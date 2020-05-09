package com.example.ssec.ui.Tratamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ssec.R;
import com.example.ssec.adapters.CustomAdapterMedicamento;
import com.example.ssec.models.Medicamento;
import com.example.ssec.models.Tratamiento;
import com.example.ssec.servicios.ApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewTratamiento extends AppCompatActivity {

    private String idTratamiento = "";
    private Tratamiento tratamientoAVer;
    private TextView posologia;
    private TextView fechaInicio;
    private TextView fechaFin;
    private TextView horario;
    private ListView listaMedicamentos;
    private TextView labelMedicamentos;
    private CustomAdapterMedicamento mAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tratamiento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        posologia = (TextView) findViewById(R.id.valuePosologia);
        fechaInicio = (TextView) findViewById(R.id.valueFechaInicio);
        fechaFin = (TextView) findViewById(R.id.valuefechaFin);
        horario = (TextView) findViewById(R.id.valueHorario);
        listaMedicamentos = (ListView) findViewById(R.id.listaMedicamentos);
        labelMedicamentos = (TextView) findViewById(R.id.labelMedicamentos);

        Intent intent = getIntent();
        idTratamiento = intent.getStringExtra("id");
        token = intent.getStringExtra("token");
        getDatosTratamiento();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void actualizarDatos(){

        String[] fechaIncioArray = tratamientoAVer.getFechaHoraInicio();
        String[] fechaFinArray = tratamientoAVer.getFechaHoraFin();

        posologia.setText(tratamientoAVer.getPosologia());
        fechaInicio.setText(fechaIncioArray[0]);
        fechaFin.setText(fechaFinArray[0]);
        horario.setText(tratamientoAVer.getHorario());
    }

    public void getDatosTratamiento(){
        HashMap<String, String> atributos = new HashMap<String, String>();

        try {

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/tratamiento/viewPaciente/"+idTratamiento+".json"
                            , token
                    );

            apiService.setHttpMethod("GET");
            apiService.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new ViewTratamiento.ExecuteNetworkOperation(apiService);
            execute.execute();

        } catch (Exception ex) {
        }
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
            String enfermedad = "";
            super.onPostExecute(result);

            tratamientoAVer = new Gson().fromJson(datos, Tratamiento.class);
            if(tratamientoAVer.getEnfermedad().equals("asma")){
                enfermedad = "Asma";
            }else if(tratamientoAVer.getEnfermedad().equals("migranas")){
                enfermedad = "Migrañas";
            }else{
                enfermedad = "Diabetes";
            }
            getSupportActionBar().setTitle("Tratamiento para "+enfermedad);

            if(tratamientoAVer.getMedicamentos() != null){
                Type listType = new TypeToken<ArrayList<Medicamento>>(){}.getType();
                List<Medicamento> listaMedicamentosList = tratamientoAVer.getMedicamentos();
                if(listaMedicamentosList.size() <= 0){
                    TextView labelMedicamentos = findViewById(R.id.labelMedicamentos);
                    labelMedicamentos.setText("Aún no existen medicamentos asociados a este tratamiento");
                }
                mAdapter = new CustomAdapterMedicamento(ViewTratamiento.this, listaMedicamentosList);

                listaMedicamentos.setAdapter(mAdapter);
            }else{
                labelMedicamentos.setText("Este tratamiento no tiene medicamentos asociados");
            }

            actualizarDatos();

        }
    }
}
