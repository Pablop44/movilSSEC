package com.example.ssec.ui.InformeMigranas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ssec.R;

public class AddInformeMigranas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_informe_migranas);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Añadir Informe Migrañas");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
