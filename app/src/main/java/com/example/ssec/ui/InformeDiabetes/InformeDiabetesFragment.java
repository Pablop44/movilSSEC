package com.example.ssec.ui.InformeDiabetes;

import android.content.DialogInterface;
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
import androidx.appcompat.app.AlertDialog;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;

import com.example.ssec.R;
import com.example.ssec.adapters.CustomAdapterInforme;
import com.example.ssec.models.Cubierto;
import com.example.ssec.models.InformeDiabetes;
import com.example.ssec.models.Numero;
import com.example.ssec.servicios.ApiService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class InformeDiabetesFragment extends Fragment {

    private String pageSize = "8";
    private String currentPage = "0";
    private String idFicha = "0";
    private Bundle datos;
    private ListView listview;
    private CustomAdapterInforme mAdapter;
    private String totalNumero = "0";
    private TextView total;
    private ImageButton previous;
    private ImageButton forward;
    private ImageButton add;
    private TextView max;
    private TextView min;
    public static final int REQUEST_CODE = 1;
    private Cubierto cubierto;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_informe_diabetes, container, false);

        datos = this.getArguments();

        idFicha = datos.getString("ficha");
        token = datos.getString("token");

        listview = (ListView) root.findViewById(R.id.listaInformesDiabetes);

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
                getInformeDiabetes();
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
                getInformeDiabetes();
                actualizarIndices();
            }
        });

        add = (ImageButton) root.findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cubierto.getCubierto().equals("true")){
                    abrirDialogo();
                }else{
                    Intent intent = new Intent(getActivity(), AddInformeDiabetes.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id", idFicha);
                    bundle.putString("token", token);
                    intent.putExtras(bundle);
                    startActivityForResult(intent , REQUEST_CODE);
                }
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewInformeDiabetes.class);
                InformeDiabetes informeDiabetes = (InformeDiabetes) parent.getItemAtPosition(position);
                intent.putExtra("id", informeDiabetes.getId());
                intent.putExtra("token", token);
                startActivity(intent);
            }
        });


        getInformeDiabetes();
        getNumeroInformes();
        getCubierto();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {

            getInformeDiabetes();
            getNumeroInformes();
            getCubierto();
        }
    }

    public void getInformeDiabetes() {
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("id", idFicha);
        atributos.put("page", currentPage);
        atributos.put("limit", pageSize);

        try {

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/diabetes/diabetesFichas.json"
                            , token
                    );

            apiService.setHttpMethod("POST");
            apiService.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new InformeDiabetesFragment.ExecuteNetworkOperation(apiService);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void getNumeroInformes(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        atributos.put("id", idFicha);

        try {

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/diabetes/numeroInformesDiabetes.json"
                            , token
                    );

            apiService.setHttpMethod("POST");
            apiService.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new InformeDiabetesFragment.ExecuteNetworkOperationNumeros(apiService);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void abrirDialogo(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this.getContext());

        dlgAlert.setMessage("Ya se ha enviado el Informe Diario");
        dlgAlert.setTitle("Ya lo has hecho...");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

    public void getCubierto(){
        try {

            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/diabetes/getCubierto/"+idFicha+".json"
                            , token
                    );

            AsyncTask<Void, Void, String> execute = new InformeDiabetesFragment.ExecuteNetworkOperationGetCubierto(apiService);
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
        private String datosInformes;

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
                datosInformes = apiService.execute();
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
            mAdapter = new CustomAdapterInforme(getActivity(), null, listaInformeDiabetes, null);
            listview.setAdapter(mAdapter);
        }
    }

    public class ExecuteNetworkOperationNumeros extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String numeroInformesAsma;

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

                numeroInformesAsma = apiService.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Numero numero = new Gson().fromJson(numeroInformesAsma, Numero.class);
            totalNumero = numero.getNumero();
            total.setText(totalNumero);
            actualizarIndices();
        }
    }

    public class ExecuteNetworkOperationGetCubierto extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String datosCubierto;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationGetCubierto(ApiService apiService) {
            this.apiService = apiService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                datosCubierto = apiService.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            cubierto = new Gson().fromJson(datosCubierto, Cubierto.class);
            Drawable drawableAdd = add.getDrawable();
            Drawable drawableForward2 = DrawableCompat.wrap(drawableAdd);
            if(cubierto.getCubierto().equals("true")){
                DrawableCompat.setTint(drawableForward2, Color.rgb(203, 203, 203));
            }else{
                DrawableCompat.setTint(drawableForward2, Color.BLACK);
            }
        }
    }
}
