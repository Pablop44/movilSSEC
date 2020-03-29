package com.example.ssec.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ssec.R;
import com.example.ssec.models.Consulta;
import com.example.ssec.models.Tratamiento;

import java.util.List;

public class CustomAdapterTratamiento extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Tratamiento> tratamientos;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private Tratamiento tratamiento;
    private Activity mActivity;

    public CustomAdapterTratamiento(Activity activity, List<Tratamiento> tratamientos) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.tratamientos = tratamientos;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return tratamientos.size();
    }

    @Override
    public Object getItem(int position) {
        return tratamientos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String enfermedad = "";
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_listview_tratamientos, parent, false);
            mViewHolder = new ViewHolder();

            mViewHolder.id = (TextView) convertView.findViewById(R.id.idTratamiento);
            mViewHolder.fechaInicio = (TextView) convertView.findViewById(R.id.fechaInicioTratamiento);
            mViewHolder.fechaFin = (TextView) convertView.findViewById(R.id.fechaFinTratamiento);
            mViewHolder.horario = (TextView) convertView.findViewById(R.id.horario);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        tratamiento = (Tratamiento) tratamientos.get(position);

        String[] valores1 = tratamiento.getFechaHoraInicio();
        String[] valores2 = tratamiento.getFechaHoraFin();

        if(tratamiento.getEnfermedad().equals("asma")){
            enfermedad = "Asma";
        }else if(tratamiento.getEnfermedad().equals("migranas")){
            enfermedad = "Migrañas";
        }else{
            enfermedad = "Diabetes";
        }

        mViewHolder.id.setText("Tratamiento para " + enfermedad);
        mViewHolder.fechaInicio.setText(valores1[0]);
        mViewHolder.fechaFin.setText(valores2[0]);
        mViewHolder.horario.setText(tratamiento.getHorario());

        return convertView;
    }

    private static class ViewHolder {
        TextView id;
        TextView fechaInicio;
        TextView fechaFin;
        TextView horario;
    }
}
