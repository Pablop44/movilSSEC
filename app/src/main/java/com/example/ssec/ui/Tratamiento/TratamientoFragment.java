package com.example.ssec.ui.Tratamiento;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.example.ssec.adapters.CustomAdapterTratamiento;
import com.example.ssec.models.Consulta;
import com.example.ssec.models.Numero;
import com.example.ssec.models.Tratamiento;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.example.ssec.ui.Consultas.ConsultasFragment;
import com.example.ssec.ui.Consultas.ViewConsulta;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TratamientoFragment extends Fragment {

    private String pageSize = "8";
    private String currentPage = "0";
    private String idFicha = "1";
    private String totalNumero = "0";
    private ListView listview;
    private CustomAdapterTratamiento mAdapter;
    private ImageButton previous;
    private ImageButton forward;
    private ImageButton add;
    private TextView max;
    private TextView min;
    private TextView total;
    private Gson gson;
    public static final int REQUEST_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();
        View root = inflater.inflate(R.layout.fragment_tratamiento, container, false);
        listview = (ListView) root.findViewById(R.id.listaConsultas);

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
                getTratamientos();
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
                getTratamientos();
                actualizarIndices();
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewTratamiento.class);
                Tratamiento tratamiento = (Tratamiento) parent.getItemAtPosition(position);
                intent.putExtra("id", tratamiento.getId());
                startActivity(intent);
            }
        });

        getTratamientos();
        getNumeroTratamientos();

        return root;
    }

    public void getTratamientos(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("idFicha", idFicha);
        atributos.put("page", currentPage);
        atributos.put("limit", pageSize);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/tratamiento/tratramientosFicha.json"
                            , "pablo"
                            , "pablo"
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new TratamientoFragment.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void getNumeroTratamientos(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("idFicha", idFicha);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/tratamiento/numeroTratramientosFicha.json"
                            , "pablo"
                            , "pablo"
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new TratamientoFragment.ExecuteNetworkOperationNumeros(apiAuthenticationClient);
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
            if(x <= i + 8){
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

        if( 0 > j-8){
            DrawableCompat.setTint(drawablePrevious2, Color.rgb(203,203,203));
            previous.setEnabled(false);
        }else{
            DrawableCompat.setTint(drawablePrevious2, Color.BLACK);
            previous.setEnabled(true);
        }
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String tratamientos;

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
                tratamientos = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Type listType = new TypeToken<ArrayList<Tratamiento>>(){}.getType();
            List<Tratamiento> listaTratamientos = new Gson().fromJson(tratamientos, listType);

            mAdapter = new CustomAdapterTratamiento(getActivity(), listaTratamientos);
            listview.setAdapter(mAdapter);
        }
    }

    public class ExecuteNetworkOperationNumeros extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String numeroTratamientos;
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

                numeroTratamientos = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Numero numero = new Gson().fromJson(numeroTratamientos, Numero.class);
            totalNumero = numero.getNumero();
            total.setText(totalNumero);
            actualizarIndices();
        }
    }
}
