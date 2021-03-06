package com.example.ssec;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ssec.models.Enfermedad;
import com.example.ssec.models.Ficha;
import com.example.ssec.servicios.ApiService;
import com.example.ssec.ui.Perfil.PerfilFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class activityInicio extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String username;
    private String token;
    private String idFicha;
    private PerfilFragment perfilFragment;
    private Ficha ficha;
    private Bundle b;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_inicio);

        b = getIntent().getExtras();

        username = b.getString("usuario");
        token = b.getString("token");
        idFicha = b.getString("ficha");

        getFicha();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.nombreUsuario);
        navUsername.setText(username);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_consultas, R.id.nav_tratamientos, R.id.nav_notas, R.id.nav_perfiles, R.id.nav_informeAsma, R.id.nav_informeDiabetes, R.id.nav_informeMigranas)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Menu menu = navigationView.getMenu();
        MenuItem perfil = menu.findItem(R.id.nav_perfiles);
        MenuItem consultas = menu.findItem(R.id.nav_consultas);
        MenuItem tratamientos = menu.findItem(R.id.nav_tratamientos);
        MenuItem notas = menu.findItem(R.id.nav_notas);
        MenuItem diabetes = menu.findItem(R.id.report_diabetes);
        MenuItem asma = menu.findItem(R.id.report_asma);
        MenuItem migranas = menu.findItem(R.id.report_migranas);
        perfil.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(activityInicio.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_perfiles, b);
                drawer.closeDrawers();
                return true;
            }
        });

        consultas.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(activityInicio.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_consultas, b);
                drawer.closeDrawers();
                return true;
            }
        });

        tratamientos.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(activityInicio.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_tratamientos, b);
                drawer.closeDrawers();
                return true;
            }
        });

        notas.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(activityInicio.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_notas, b);
                drawer.closeDrawers();
                return true;
            }
        });

        diabetes.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(activityInicio.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_informeDiabetes, b);
                drawer.closeDrawers();
                return true;
            }
        });

        asma.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(activityInicio.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_informeAsma, b);
                drawer.closeDrawers();
                return true;
            }
        });

        migranas.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(activityInicio.this, R.id.nav_host_fragment);
                navController.navigate(R.id.nav_informeMigranas, b);
                drawer.closeDrawers();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_inicio, menu);
        Drawable unwrappedDrawable = AppCompatResources.getDrawable(activityInicio.this, R.drawable.ic_exit_app);
        Drawable wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable);
        DrawableCompat.setTint(wrappedDrawable, Color.WHITE);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                break;
            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void logout(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(activityInicio.this);

        dlgAlert.setTitle("¿Quiere cerrar Sesión?");
        dlgAlert.setPositiveButton("SI",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        requestLogout();
                        activityInicio.this.finish();
                    }
                });
        dlgAlert.setNegativeButton("NO", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void requestLogout() {

        try {
            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/user/logout.json"
                            , token
                    );

            AsyncTask<Void, Void, String> execute = new activityInicio.ExecuteNetworkOperation(apiService);
            execute.execute();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error en el cierre de sesión", Toast.LENGTH_LONG).show();
        }

    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String isValidCredentials;

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
                isValidCredentials = apiService.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


    public void getFicha(){
        try {
            ApiService apiService =
                    new ApiService(
                            "http://10.0.2.2:8765/ficha/viewPaciente/"+idFicha+".json"
                            , token
                    );

            AsyncTask<Void, Void, String> execute = new activityInicio.ExecuteNetworkOperationGetFicha(apiService);
            execute.execute();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error en el acceso a datos", Toast.LENGTH_LONG).show();
        }

    }

    public class ExecuteNetworkOperationGetFicha extends AsyncTask<Void, Void, String> {

        private ApiService apiService;
        private String datosFicha;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationGetFicha(ApiService apiService) {
            this.apiService = apiService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                datosFicha = apiService.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ficha = new Gson().fromJson(datosFicha, Ficha.class);
            Menu menu = navigationView.getMenu();
            MenuItem diabetes = menu.findItem(R.id.report_diabetes);
            MenuItem migranas = menu.findItem(R.id.report_migranas);
            MenuItem asma = menu.findItem(R.id.report_asma);
            migranas.setVisible(false);
            diabetes.setVisible(false);
            asma.setVisible(false);

            for (Enfermedad enfermedad : ficha.getEnfermedad()) {
                if(enfermedad.getEnfermedad().equals("migranas")){
                    migranas.setVisible(true);
                }
                if(enfermedad.getEnfermedad().equals("diabetes")){
                    diabetes.setVisible(true);
                }
                if(enfermedad.getEnfermedad().equals("asma")){
                    asma.setVisible(true);
                }
            }
        }
    }
}
