package com.example.ssec.ui.InformeDiabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.ssec.R;

import java.util.HashMap;

public class AddInformeDiabetes extends AppCompatActivity {

    private Spinner spinner_numeroControles;
    private CheckBox AntesComida;
    private CheckBox DespuesComida;
    private CheckBox PadecerEpisodio;
    private CheckBox Dormir;
    private CheckBox Levantarse;
    private Spinner spinner_nivelBajo;
    private Spinner spinner_frecuenciaBajo;
    private Spinner spinner_horarioBajo;
    private Spinner spinner_perdidaConocimiento;
    private Spinner spinner_nivelAlto;
    private Spinner spinner_frecuenciaAlto;
    private Spinner spinner_horarioAlto;
    private Spinner spinner_actividadFisica;
    private Spinner spinner_problemaDieta;
    private Spinner editText_estadoGeneral;
    private Button button_send;
    HashMap<String, String> atributos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_informe_diabetes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Añadir Informe Diabetes");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
