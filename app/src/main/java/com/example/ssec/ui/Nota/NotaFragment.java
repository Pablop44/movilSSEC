package com.example.ssec.ui.Nota;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ssec.R;
import com.example.ssec.adapters.CustomAdapterConsulta;
import com.example.ssec.adapters.CustomAdapterNota;
import com.example.ssec.models.Consulta;
import com.example.ssec.models.Nota;
import com.example.ssec.models.Numero;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.example.ssec.ui.Consultas.ConsultasFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotaFragment extends Fragment {

    private String baseUrl;
    private String pageSize = "4";
    private String currentPage = "0";
    private String idFicha = "0";
    private String totalNumero = "0";
    private Gson gson;
    private CustomAdapterNota mAdapter;
    private ListView listview;
    private ImageButton previous;
    private ImageButton forward;
    private ImageButton add;
    private TextView max;
    private TextView min;
    private TextView total;
    private Bundle datos;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        baseUrl = "http://10.0.2.2:8765/nota/notasFicha.json";
        gson = new Gson();
        View root = inflater.inflate(R.layout.fragment_notas, container, false);

        datos = this.getArguments();
        idFicha = datos.getString("ficha");
        token = datos.getString("token");

        listview = (ListView) root.findViewById(R.id.listaNotas);

        max = (TextView) root.findViewById(R.id.max);

        min = (TextView) root.findViewById(R.id.min);

        total = (TextView) root.findViewById(R.id.total);

        total.setText(totalNumero);

        previous = (ImageButton) root.findViewById(R.id.previous);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(currentPage);
                i -= 1;

                currentPage = Integer.toString(i);
                getNotas();
                actualizarIndices();
            }
        });

        forward = (ImageButton) root.findViewById(R.id.forward);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = Integer.parseInt(currentPage);
                i += 1;

                currentPage = Integer.toString(i);
                getNotas();
                actualizarIndices();
            }
        });

        getNotas();
        getNumeroNotas();

        return root;
    }

    public void getNumeroNotas(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("idFicha", idFicha);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/nota/numeroNotas.json"
                            , token
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new NotaFragment.ExecuteNetworkOperationNumeros(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void getNotas() {
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("idFicha", idFicha);
        atributos.put("page", currentPage);
        atributos.put("limit", pageSize);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            baseUrl
                            , token
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new NotaFragment.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void actualizarIndices(){

        int i = 0;
        i = (Integer.parseInt(currentPage)+1) * Integer.parseInt(pageSize);
        max.setText(Integer.toString(i));

        int x = Integer.parseInt(totalNumero);

        Drawable drawableForward = forward.getDrawable();
        Drawable drawableForward2 = DrawableCompat.wrap(drawableForward);

        Drawable drawablePrevious = previous.getDrawable();
        Drawable drawablePrevious2 = DrawableCompat.wrap(drawablePrevious);

        if( x <= i){
            if(x <= i + 4){
                DrawableCompat.setTint(drawableForward2, Color.rgb(203, 203, 203));
                forward.setEnabled(false);
                max.setText(totalNumero);
            }else {
                DrawableCompat.setTint(drawableForward2, Color.BLACK);
                forward.setEnabled(true);
            }
        }else{
            DrawableCompat.setTint(drawableForward2, Color.BLACK);
            forward.setEnabled(true);
        }

        int j = 0;
        j = (Integer.parseInt(currentPage) * Integer.parseInt(pageSize))+1;
        min.setText(Integer.toString(j));

        if( 0 > j-4){
            DrawableCompat.setTint(drawablePrevious2, Color.rgb(203,203,203));
            previous.setEnabled(false);
        }else{
            DrawableCompat.setTint(drawablePrevious2, Color.BLACK);
            previous.setEnabled(true);
        }
    }


    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String notas;

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
                notas = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Type listType = new TypeToken<ArrayList<Nota>>(){}.getType();
            List<Nota> listaNotas = new Gson().fromJson(notas, listType);

            mAdapter = new CustomAdapterNota(getActivity(), listaNotas);
            listview.setAdapter(mAdapter);
        }
    }

    public class ExecuteNetworkOperationNumeros extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String numeroNotas;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationNumeros(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                numeroNotas = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Numero numero = new Gson().fromJson(numeroNotas, Numero.class);
            totalNumero = numero.getNumero();
            total.setText(totalNumero);
            actualizarIndices();
        }
    }
}
