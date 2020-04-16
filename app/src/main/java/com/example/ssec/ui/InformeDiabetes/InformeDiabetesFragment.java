package com.example.ssec.ui.InformeDiabetes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ssec.R;
import com.example.ssec.models.InformeDiabetes;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InformeDiabetesFragment extends Fragment {

    private String baseUrl;
    private String pageSize = "8";
    private String currentPage = "0";
    private String idFicha = "0";
    private Bundle datos;
    private TextView prueba;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_informe_diabetes, container, false);

        datos = this.getArguments();

        idFicha = datos.getString("ficha");

        getInformeDiabetes();

        return root;
    }

    public void getInformeDiabetes() {
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("id", idFicha);
        atributos.put("page", currentPage);
        atributos.put("limit", pageSize);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/diabetes/diabetesFichas.json"
                            , ""
                            , ""
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new InformeDiabetesFragment.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datosInformes;

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
                datosInformes = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Type listType = new TypeToken<ArrayList<InformeDiabetes>>(){}.getType();
            List<InformeDiabetes> listaInformeDiabetes = new Gson().fromJson(datosInformes, listType);
            Toast.makeText(getActivity(), datosInformes, Toast.LENGTH_LONG).show();
        }
    }
}
