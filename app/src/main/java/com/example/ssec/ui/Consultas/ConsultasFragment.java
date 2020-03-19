package com.example.ssec.ui.Consultas;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ssec.MainActivity;
import com.example.ssec.R;
import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

import java.util.HashMap;

public class ConsultasFragment extends Fragment {

    private String baseUrl;
    private String pageSize = "15";
    private String currentPage = "0";
    private String idFicha = "1";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        baseUrl = "http://10.0.2.2:8765/consulta/consultaFicha.json";

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("id", idFicha);
        atributos.put("page", currentPage);
        atributos.put("limit", pageSize);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            baseUrl
                            , "pablo"
                            , "pablo"
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new ConsultasFragment.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
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
