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
import com.example.ssec.models.Medicamento;
import com.example.ssec.models.Tratamiento;

import java.util.List;

public class CustomAdapterMedicamento extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Medicamento> medicamentos;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private Medicamento medicamento;
    private Activity mActivity;

    public CustomAdapterMedicamento(Activity activity, List<Medicamento> medicamentos) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.medicamentos = medicamentos;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return medicamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return medicamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String enfermedad = "";
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_listview_medicamentos, parent, false);
            mViewHolder = new ViewHolder();

            mViewHolder.nombre = (TextView) convertView.findViewById(R.id.nombreMedicamento);
            mViewHolder.via = (TextView) convertView.findViewById(R.id.viaAdministracion);
            mViewHolder.marca = (TextView) convertView.findViewById(R.id.marca);
            mViewHolder.dosis = (TextView) convertView.findViewById(R.id.dosis);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        medicamento = (Medicamento) medicamentos.get(position);

        mViewHolder.nombre.setText(medicamento.getNombre());
        mViewHolder.via.setText(medicamento.getViaAdministracion());
        mViewHolder.marca.setText(medicamento.getMarca());
        mViewHolder.dosis.setText(medicamento.getDosis()+" mg");

        return convertView;
    }

    private static class ViewHolder {
        TextView nombre;
        TextView via;
        TextView marca;
        TextView dosis;
    }
}
