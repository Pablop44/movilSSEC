package com.example.ssec.ui.InformeAsma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ssec.R;

public class AddInformeAsma extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_informe_asma);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Añadir Informe Asma");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
