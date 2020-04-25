package com.example.ssec.ui.Consultas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ssec.R;
import com.example.ssec.adapters.CustomAdapterConsulta;
import com.example.ssec.models.Consulta;
import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewConsulta extends AppCompatActivity {

    private String idConsulta = "";
    private TextView valueLugar;
    private TextView valueMotivo;
    private TextView valueFecha;
    private TextView valueHora;
    private TextView valueDiagnostico;
    private TextView valueObservaciones;
    private TextView valueEstado;
    private Consulta consultaAver;
    private Button aplazarConsulta;
    private Button cancelarConsulta;
    public static final int REQUEST_CODE = 1;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_consulta);

        valueLugar = (TextView) findViewById(R.id.valueLugar);
        valueMotivo = (TextView) findViewById(R.id.valueMotivo);
        valueFecha = (TextView) findViewById(R.id.valueFecha);
        valueHora = (TextView) findViewById(R.id.valueHora);
        valueDiagnostico = (TextView) findViewById(R.id.valueDiagnostico);
        valueObservaciones = (TextView) findViewById(R.id.valueObservaciones);
        valueEstado = (TextView) findViewById(R.id.valueEstado);

        aplazarConsulta = (Button) findViewById(R.id.buttonAplazarConsulta);

        aplazarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewConsulta.this, AddConsulta.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", idConsulta);
                intent.putExtras(bundle);
                startActivityForResult(intent , REQUEST_CODE);
            }
        });

        cancelarConsulta = (Button) findViewById(R.id.buttonCancelarConsulta);

        cancelarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogo();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        idConsulta = intent.getStringExtra("id");
        token = intent.getStringExtra("token");
        getDatosFicha();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
           getDatosFicha();
        }
    }

    public void abrirDialogo(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage("¿Estás seguro de que quieres cancelar la cita?");
        dlgAlert.setTitle("Cancelar cita");
        dlgAlert.setNegativeButton("Cerrar", null);
        dlgAlert.setPositiveButton("Cancelar Consulta",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        cancelarConsultaMethod();
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void getDatosFicha(){
        try {
            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/consulta/view/"+idConsulta+".json"
                            , token
                    );

            apiAuthenticationClient.setHttpMethod("GET");

            AsyncTask<Void, Void, String> execute = new ViewConsulta.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }


    public void cancelarConsultaMethod(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("id", idConsulta);
        atributos.put("estado", "cancelada");
        try {


            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/consulta/editarConsulta/.json"
                            , token
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new ViewConsulta.ExecuteNetworkOperationCancelar(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void actualizarDatos(){

        String[] fechaHora = consultaAver.getFechaHora();

        if(consultaAver.getLugar() == null){
            valueLugar.setText("Lugar no definido");
        }else{
            valueLugar.setText(consultaAver.getLugar());
        }
        valueMotivo.setText(consultaAver.getMotivo());
        valueFecha.setText(fechaHora[0]);
        valueHora.setText(fechaHora[1]);

        if(consultaAver.diagnosticoNull()){
            valueDiagnostico.setText("No tiene");
        }else{
            valueDiagnostico.setText("Si tiene");
        }

        if(consultaAver.observacionesNull()){
            valueObservaciones.setText("No tiene");
        }else{
            valueObservaciones.setText("Si tiene");
        }

        if(consultaAver.getEstado().equals("en tiempo")){
            valueEstado.setText("En tiempo");
        }else if(consultaAver.getEstado().equals("realizada")){
            valueEstado.setText("Realizada");
            cancelarConsulta.setEnabled(false);
            aplazarConsulta.setEnabled(false);
        }else if(consultaAver.getEstado().equals("cancelada")){
            valueEstado.setText("Cancelada");
            cancelarConsulta.setEnabled(false);
            aplazarConsulta.setEnabled(false);
        }else if(consultaAver.getEstado().equals("aplazada")){
            valueEstado.setText("Aplazada");
            cancelarConsulta.setEnabled(false);
            aplazarConsulta.setEnabled(false);
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

            consultaAver = new Gson().fromJson(datos, Consulta.class);
            getSupportActionBar().setTitle("Consulta "+consultaAver.getId());
            actualizarDatos();
        }
    }


    public class ExecuteNetworkOperationCancelar extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datos;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationCancelar(ApiAuthenticationClient apiAuthenticationClient) {
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
            consultaAver = new Gson().fromJson(datos, Consulta.class);
            Toast.makeText(getApplicationContext(), "Se ha cancelado con éxito", Toast.LENGTH_LONG).show();
            actualizarDatos();
        }
    }
}
