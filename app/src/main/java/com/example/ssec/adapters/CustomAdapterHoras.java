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
import com.example.ssec.models.Hora;

import java.util.List;

public class CustomAdapterHoras extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Hora> horas;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private Hora hora;
    private Activity mActivity;

    public CustomAdapterHoras(Activity activity, List<Hora> horas) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.horas = horas;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return horas.size();
    }

    @Override
    public Object getItem(int position) {
        return horas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_listview_horas, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.textViewDisponibilidad = (TextView) convertView.findViewById(R.id.disponibilidad);
            mViewHolder.textViewHora = (TextView) convertView.findViewById(R.id.hora);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        hora = (Hora) horas.get(position);

        mViewHolder.textViewHora.setText(hora.getHora());
        if(hora.getDisponible().equals("true")){
            mViewHolder.textViewDisponibilidad.setText("Ocupada");
            convertView.setBackgroundColor(Color.rgb(255, 223, 223));
        }else{
            mViewHolder.textViewDisponibilidad.setText("Disponible");
            convertView.setBackgroundColor(Color.rgb(219, 255, 210));
        }


        return convertView;
    }

    private static class ViewHolder {
        TextView textViewDisponibilidad;
        TextView textViewHora;
    }
}
