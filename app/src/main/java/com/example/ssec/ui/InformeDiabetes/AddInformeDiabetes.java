package com.example.ssec.ui.InformeDiabetes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ssec.R;

public class AddInformeDiabetes extends AppCompatActivity {

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
