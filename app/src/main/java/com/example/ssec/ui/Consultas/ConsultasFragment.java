package com.example.ssec.ui.Consultas;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.example.ssec.R;
import com.example.ssec.models.Consulta;
import com.example.ssec.models.Numero;
import com.example.ssec.servicios.ApiService;
import com.google.gson.Gson;
import com.example.ssec.adapters.CustomAdapterConsulta;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ConsultasFragment extends Fragment {

    private String pageSize = "8";
    private String currentPage = "0";
    private String idFicha = "1";
    private String totalNumero = "0";
    private ListView listview;
    private CustomAdapterConsulta mAdapter;
    private ImageButton previous;
    private ImageButton forward;
    private ImageButton add;
    private TextView max;
    private TextView min;
    private TextView total;
    private Gson gson;
    private Bundle datos;
    private String token;
    public static final int REQUEST_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        gson = new Gson();
        View root = inflater.inflate(R.layout.fragment_consultas, container, false);
        listview = (ListView) root.findViewById(R.id.listaConsultas);

        datos = this.getArguments();
        idFicha = datos.getString("ficha");
        token = datos.getString("token");

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
                getConsultas();
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
                getConsultas();
                actualizarIndices();
            }
        });

        add = (ImageButton) root.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddConsulta.class);
                intent.putExtra("token", token);
                startActivityForResult(intent , REQUEST_CODE);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewConsulta.class);
                Consulta consulta = (Consulta) parent.getItemAtPosition(position);
                intent.putExtra("id", consulta.getId());
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });

        getNumeroConsultas();
        getConsultas();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {

            getConsultas();
            getNumeroConsultas();
        }
    }

    public void getConsultas(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("id", idFicha);
        atributos.put("page", currentPage);
        atributos.put("limit", pageSize);

        try {

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/consulta/consultaFicha.json"
                            , token
                    );

            apiService.setHttpMethod("POST");
            apiService.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new ConsultasFragment.ExecuteNetworkOperation(apiService);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void getNumeroConsultas(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("id", idFicha);

        try {

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/consulta/numeroConsultas.json"
                            , token
                    );

            apiService.setHttpMethod("POST");
            apiService.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new ConsultasFragment.ExecuteNetworkOperationNumeros(apiService);
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
        if(totalNumero.equals("0")){
            min.setText("0");
        }

        if( 0 > j-8){
            DrawableCompat.setTint(drawablePrevious2, Color.rgb(203,203,203));
            previous.setEnabled(false);
        }else{
            DrawableCompat.setTint(drawablePrevious2, Color.BLACK);
            previous.setEnabled(true);
        }
    }


    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String consultas;

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

                consultas = apiService.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);


            Type listType = new TypeToken<ArrayList<Consulta>>(){}.getType();
            List<Consulta> listaConsultas = new Gson().fromJson(consultas, listType);

            mAdapter = new CustomAdapterConsulta(getActivity(), listaConsultas);
            listview.setAdapter(mAdapter);

        }
    }

    public class ExecuteNetworkOperationNumeros extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String numeroConsultas;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationNumeros(ApiService apiService) {
            this.apiService = apiService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                numeroConsultas = apiService.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Numero numero = new Gson().fromJson(numeroConsultas, Numero.class);
            totalNumero = numero.getNumero();
            total.setText(totalNumero);
            actualizarIndices();
        }
    }
}
