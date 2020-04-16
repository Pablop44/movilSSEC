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
import com.example.ssec.models.InformeAsma;
import com.example.ssec.models.InformeDiabetes;
import com.example.ssec.models.InformeMigranas;

import java.util.List;

public class CustomAdapterInforme extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<InformeAsma> informesAsma = null;
    private List<InformeDiabetes> informeDiabetes = null;
    private List<InformeMigranas> informeMigranas = null;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private InformeMigranas informeM;
    private InformeDiabetes informeD;
    private InformeAsma informeA;
    private Activity mActivity;

    public CustomAdapterInforme(Activity activity, List<InformeAsma> informesAsma, List<InformeDiabetes> informeDiabetes, List<InformeMigranas> informeMigranas) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.informeDiabetes = informeDiabetes;
        this.informeMigranas = informeMigranas;
        this.informesAsma = informesAsma;
        mActivity = activity;
    }

    private Boolean getNullDiabetes(){
        return informeDiabetes != null;
    }

    private Boolean getNullAsma(){
        return informesAsma != null;
    }

    private Boolean getNullMigranas(){
        return informeMigranas != null;
    }

    @Override
    public int getCount() {
        if(getNullAsma()){
            return informesAsma.size();
        }else if(getNullDiabetes()){
            return informeDiabetes.size();
        }else{
            return informeMigranas.size();
        }
    }

    @Override
    public Object getItem(int position) {
        if(getNullAsma()){
            return informesAsma.get(position);
        }else if(getNullDiabetes()){
            return informeDiabetes.get(position);
        }else{
            return informeMigranas.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_listview_informe, parent, false);
            mViewHolder = new ViewHolder();
            mViewHolder.textViewTitle = (TextView) convertView.findViewById(R.id.report_title);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if(getNullAsma()){
            informeA = (InformeAsma) informesAsma.get(position);
            mViewHolder.textViewTitle.setText("Informe con Fecha - "+informeA.getFecha());
        }else if(getNullDiabetes()){
            informeD = (InformeDiabetes) informeDiabetes.get(position);
            mViewHolder.textViewTitle.setText("Informe con Fecha - "+informeD.getFecha());
        }else{
            informeM = (InformeMigranas) informeMigranas.get(position);
            mViewHolder.textViewTitle.setText("Informe con Fecha - "+informeM.getFecha());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewTitle;
    }
}
