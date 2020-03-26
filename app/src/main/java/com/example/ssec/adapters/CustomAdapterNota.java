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
import com.example.ssec.models.Nota;

import java.util.List;

public class CustomAdapterNota extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Nota> notas;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private Nota nota;
    private Activity mActivity;

    public CustomAdapterNota(Activity activity, List<Nota> notas) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.notas = notas;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return notas.size();
    }

    @Override
    public Object getItem(int position) {
        return notas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_listview_notas, parent, false);
            mViewHolder = new ViewHolder();

            mViewHolder.fecha = (TextView) convertView.findViewById(R.id.fecha);
            mViewHolder.cuerpo = (TextView) convertView.findViewById(R.id.cuerpo);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        nota = (Nota) notas.get(position);

        mViewHolder.fecha.setText(nota.getFecha());
        mViewHolder.cuerpo.setText(nota.getDatos());

        return convertView;
    }

    private static class ViewHolder {
        TextView fecha;
        TextView cuerpo;
    }
}
