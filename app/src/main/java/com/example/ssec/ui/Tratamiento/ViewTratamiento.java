package com.example.ssec.ui.Tratamiento;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ssec.R;
import com.example.ssec.models.Consulta;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.example.ssec.ui.Consultas.ViewConsulta;
import com.google.gson.Gson;

import java.util.HashMap;

public class ViewTratamiento extends AppCompatActivity {

    private String idTratamiento = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tratamiento);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Tratamiento para ");

        Intent intent = getIntent();
        idTratamiento = intent.getStringExtra("id");
        getDatosTratamiento();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void getDatosTratamiento(){
        HashMap<String, String> atributos = new HashMap<String, String>();

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/tratamiento/view/"+idTratamiento+".json"
                            , "pablo"
                            , "pablo"
                    );

            apiAuthenticationClient.setHttpMethod("GET");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new ViewTratamiento.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
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

            Toast.makeText(getApplicationContext(), datos, Toast.LENGTH_LONG).show();

        }
    }
}
