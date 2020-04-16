package com.example.ssec.ui.InformeAsma;

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
import com.example.ssec.servicios.ApiAuthenticationClient;

import java.util.HashMap;

public class InformeAsmaFragment extends Fragment {

    private String baseUrl;
    private String pageSize = "8";
    private String currentPage = "0";
    private String idFicha = "0";
    private Bundle datos;
    private TextView prueba;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        baseUrl = "http://10.0.2.2:8765/nota/notasFicha.json";

        View root = inflater.inflate(R.layout.fragment_informe_asma, container, false);

        datos = this.getArguments();

        idFicha = datos.getString("ficha");

        return root;
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String isValidCredentials;

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
                isValidCredentials = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getActivity(), isValidCredentials, Toast.LENGTH_LONG).show();
        }
    }
}
