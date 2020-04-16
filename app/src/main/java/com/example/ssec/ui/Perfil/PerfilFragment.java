package com.example.ssec.ui.Perfil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ActivityNavigator;

import com.example.ssec.R;
import com.example.ssec.models.User;
import com.example.ssec.servicios.ApiAuthenticationClient;
import com.example.ssec.ui.Consultas.ConsultasFragment;
import com.google.gson.Gson;

import java.util.HashMap;

public class PerfilFragment extends Fragment {

    private Bundle datos;
    private String idUser = "0";
    private User usuario;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        datos = this.getArguments();
        idUser = datos.getString("id");

        getDatosUser();

        return root;
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
            Toast.makeText(getActivity(), datosUser, Toast.LENGTH_LONG).show();
        }
    }
}
