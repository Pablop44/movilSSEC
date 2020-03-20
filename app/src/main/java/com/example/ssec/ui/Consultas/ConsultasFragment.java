package com.example.ssec.ui.Consultas;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.ssec.R;
import com.example.ssec.models.Consulta;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;
import com.example.ssec.adapters.CustomAdapterConsulta;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsultasFragment extends Fragment {

    private String baseUrl;
    private String pageSize = "15";
    private String currentPage = "0";
    private String idFicha = "1";
    private ListView listview;
    private ArrayList<String> names;
    private CustomAdapterConsulta mAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        baseUrl = "http://10.0.2.2:8765/consulta/consultaFicha.json";

        View root = inflater.inflate(R.layout.fragment_consultas, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        listview = (ListView) root.findViewById(R.id.listaConsultas);


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


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(getActivity().getBaseContext(), "Has pulsado: "+ names.get(position), Toast.LENGTH_LONG).show();
            }
        });

        return root;

    }


    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String consultas;

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

                consultas = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Consulta>>(){}.getType();
            List<Consulta> listaConsultas = new Gson().fromJson(consultas, listType);

            mAdapter = new CustomAdapterConsulta(getActivity(), listaConsultas);
            listview.setAdapter(mAdapter);
            Toast.makeText(getActivity(), consultas, Toast.LENGTH_LONG).show();
        }
    }
}
