package com.example.ssec.ui.Consultas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.ssec.MainActivity;
import com.example.ssec.R;
import com.example.ssec.adapters.CustomAdapterConsulta;
import com.example.ssec.adapters.CustomAdapterHoras;
import com.example.ssec.aux.DatePickerFragment;
import com.example.ssec.models.Consulta;
import com.example.ssec.models.Hora;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddConsulta extends AppCompatActivity {

    private EditText editText_fechaConsulta;
    private CustomAdapterHoras mAdapter;
    private String idFicha = "1";
    private ListView listViewHoras;
    private int positionClicked = -1;
    private String horaSeleccionada = "";
    private View viewAcambiar = null;
    private Button buttonAnadir;
    private Button buttonCancelar;
    private EditText editText_motivo;
    private String selectedDate;
    private String motivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_consulta);

        getSupportActionBar().setTitle(R.string.anadir_consulta);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        editText_fechaConsulta = (EditText) findViewById(R.id.editText_fechaConsulta);
        listViewHoras = (ListView) findViewById(R.id.listaHoras);
        buttonAnadir = (Button) findViewById(R.id.anadirConsultaButton);
        buttonCancelar = (Button) findViewById(R.id.cancelarConsultaButton);
        editText_motivo = (EditText) findViewById(R.id.editText_motivo);

        editText_fechaConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        listViewHoras.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (positionClicked != position) {
                    Hora hora = (Hora) parent.getItemAtPosition(position);
                    if (hora.getDisponible().equals("false")) {
                        horaSeleccionada = hora.getHora();
                        view.setBackgroundColor(Color.rgb(210, 235, 235));
                        if (viewAcambiar != null) {
                            viewAcambiar.setBackgroundColor(Color.rgb(219, 255, 210));
                        }
                        viewAcambiar = view;
                        positionClicked = position;
                    } else {
                        Toast.makeText(getApplicationContext(), "La hora ya está ocupada", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddConsulta.this.finish();
            }
        });

        buttonAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText_fechaConsulta.getText().toString() == "" || editText_motivo.getText().toString() == "" || horaSeleccionada == ""){

                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(AddConsulta.this);
                    dlgAlert.setTitle("Cubra todos los campos");
                    dlgAlert.setPositiveButton("Confirmar", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                }else{
                    AlertDialog.Builder dlgAlert2  = new AlertDialog.Builder(AddConsulta.this);
                    dlgAlert2.setMessage("Con fecha "+editText_fechaConsulta.getText().toString()+" y hora "+horaSeleccionada);
                    dlgAlert2.setTitle("¿Quiere confirmar la consulta?");
                    dlgAlert2.setNegativeButton("Cancelar", null);
                    dlgAlert2.setPositiveButton("Confirmar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    makePostAddConsulta();
                                }
                            });
                    dlgAlert2.setCancelable(true);
                    dlgAlert2.create().show();
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            public String nacimiento;
            String separador1;
            String separador;

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                if(month < 9){
                    separador1 = "-0";
                }else{
                    separador1 = "-";
                }
                if(day < 10){
                    separador = "0";
                }else{
                    separador = "";
                }

                // +1 because January is zero
                selectedDate = separador + day + separador1 + (month + 1) + "-" + year;
                editText_fechaConsulta.setText(selectedDate);
                getHorasConsultas(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }


    public void getHorasConsultas(String selectedDate){
        HashMap<String, String> atributos = new HashMap<String, String>();
        String[] valores = selectedDate.split("-");
        String fechaFinal = valores[2] + "-" + valores[1] + "-" + valores[0];
        atributos.put("fecha", fechaFinal);
        atributos.put("ficha", idFicha);

        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/consulta/getHorasPaciente.json"
                            , "pablo"
                            , "pablo"
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new AddConsulta.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public void makePostAddConsulta(){
        HashMap<String, String> atributos = new HashMap<String, String>();
        String[] valores = selectedDate.split("-");
        String fechaFinal = valores[2] + "-" + valores[1] + "-" + valores[0] + " "+horaSeleccionada+":00";
        motivo = editText_motivo.getText().toString();
        atributos.put("fecha", fechaFinal);
        atributos.put("motivo", motivo);
        atributos.put("ficha", idFicha);
        atributos.put("estado", "en tiempo");
        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/consulta/add.json"
                            , "pablo"
                            , "pablo"
                    );

            apiAuthenticationClient.setHttpMethod("POST");
            apiAuthenticationClient.setParameters(atributos);

            AsyncTask<Void, Void, String> execute = new AddConsulta.ExecuteNetworkOperationAddConsulta(apiAuthenticationClient);
            execute.execute();

        } catch (Exception ex) {
        }
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String horas;

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

                horas = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            Type listType = new TypeToken<ArrayList<Hora>>(){}.getType();
            List<Hora> listaHoras = new Gson().fromJson(horas, listType);

            mAdapter = new CustomAdapterHoras(AddConsulta.this, listaHoras);
            listViewHoras.setAdapter(mAdapter);

        }
    }


    public class ExecuteNetworkOperationAddConsulta extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String exitoONo;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationAddConsulta(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                exitoONo = apiAuthenticationClient.execute();

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(exitoONo != ""){
                Toast.makeText(getApplicationContext(), "Consulta creada correctamente", Toast.LENGTH_SHORT).show();
                AddConsulta.this.finish();
            }else {
                Toast.makeText(getApplicationContext(), "No se ha podido crear la consulta", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
