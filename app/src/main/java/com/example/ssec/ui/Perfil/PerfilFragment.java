package com.example.ssec.ui.Perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.ssec.MainActivity;
import com.example.ssec.R;
import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.google.gson.Gson;

import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {

    private Bundle datos;
    private String idUser = "0";
    private User usuario;
    private TextView valuedni;
    private TextView valueusername;
    private TextView valueemail;
    private TextView valuenombre;
    private TextView valueapellidos;
    private TextView valuetelefono;
    private TextView valuepoblacion;
    private TextView valuegenero;
    private TextView valuefechaNacimiento;
    private Button button_editar;
    private Button button_desactivarCuenta;
    public static final int REQUEST_CODE = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        datos = this.getArguments();
        idUser = datos.getString("id");

        valuedni =(TextView) root.findViewById(R.id.valuedni);
        valueusername =(TextView) root.findViewById(R.id.valueusername);
        valueemail =(TextView) root.findViewById(R.id.valueemail);
        valuenombre =(TextView) root.findViewById(R.id.valuenombre);
        valueapellidos =(TextView) root.findViewById(R.id.valueapellidos);
        valuetelefono =(TextView) root.findViewById(R.id.valuetelefono);
        valuepoblacion =(TextView) root.findViewById(R.id.valuepoblacion);
        valuegenero =(TextView) root.findViewById(R.id.valuegenero);
        valuefechaNacimiento =(TextView) root.findViewById(R.id.valuefechaNacimiento);
        button_editar =(Button) root.findViewById(R.id.button_editar);
        button_desactivarCuenta =(Button) root.findViewById(R.id.button_desactivarCuenta);

        button_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditUser.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", idUser);
                intent.putExtras(bundle);
                startActivityForResult(intent , REQUEST_CODE);
            }
        });

        button_desactivarCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialogo();
            }
        });

        getDatosUser();

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE  && resultCode  == RESULT_OK) {
            getDatosUser();
        }
    }

    public void abrirDialogo(){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this.getContext());

        dlgAlert.setMessage("Para volver a utilizarla el administrador deberá autorizarte de nuevo");
        dlgAlert.setTitle("¿Quieres desactivar tu cuenta?");
        dlgAlert.setNegativeButton("Cancelar", null);
        dlgAlert.setPositiveButton("Desactivar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        desactivarCuenta();
                    }
                });
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

    public void actualizarDatos(){
        valuedni.setText(usuario.getDni());
        valueusername.setText(usuario.getUsername());
        valueemail.setText(usuario.getEmail());
        valuenombre.setText(usuario.getNombre());
        valueapellidos.setText(usuario.getApellidos());
        valuetelefono.setText(usuario.getTelefono());
        valuepoblacion.setText(usuario.getPoblacion());
        if(usuario.getGenero().equals("Male")){
            valuegenero.setText("Hombre");
        }else{
            valuegenero.setText("Mujer");
        }

        valuefechaNacimiento.setText(usuario.getNacimiento());
    }

    public void getDatosUser() {
        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/user/view/"+idUser+".json"
                            , ""
                            , ""
                    );

            AsyncTask<Void, Void, String> execute = new PerfilFragment.ExecuteNetworkOperation(apiAuthenticationClient);
            execute.execute();
        } catch (Exception ex) {
        }
    }

    public class ExecuteNetworkOperation extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datosUser;

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
                datosUser = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            usuario = new Gson().fromJson(datosUser, User.class);
            actualizarDatos();
        }
    }


    public void desactivarCuenta() {
        try {

            ApiAuthenticationClient apiAuthenticationClient =
                    new ApiAuthenticationClient(
                            "http://10.0.2.2:8765/cuenta/desactivar/"+idUser+".json"
                            , ""
                            , ""
                    );

            AsyncTask<Void, Void, String> execute = new PerfilFragment.ExecuteNetworkOperationDesactivar(apiAuthenticationClient);
            execute.execute();
        } catch (Exception ex) {
        }
    }

    public class ExecuteNetworkOperationDesactivar extends AsyncTask<Void, Void, String> {

        private ApiAuthenticationClient apiAuthenticationClient;
        private String datos;

        /**
         * Overload the constructor to pass objects to this class.
         */
        public ExecuteNetworkOperationDesactivar(ApiAuthenticationClient apiAuthenticationClient) {
            this.apiAuthenticationClient = apiAuthenticationClient;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... params) {
            try {
                datos = apiAuthenticationClient.execute();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toast.makeText(getActivity(), "Se ha desativado la cuenta, hasta otra...", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }
}
