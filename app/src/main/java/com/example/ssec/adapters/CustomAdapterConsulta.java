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

import java.util.List;

public class CustomAdapterConsulta extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Consulta> consultas;
    private ViewHolder mViewHolder;

    private Bitmap mBitmap;
    private Consulta consulta;
    private Activity mActivity;

    public CustomAdapterConsulta(Activity activity, List<Consulta> consultas) {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        this.consultas = consultas;
        mActivity = activity;
    }

    @Override
    public int getCount() {
        return consultas.size();
    }

    @Override
    public Object getItem(int position) {
        return consultas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_listview_consultas, parent, false);
            mViewHolder = new ViewHolder();

            mViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.post_thumbnail);
            mViewHolder.author = (TextView) convertView.findViewById(R.id.post_author);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.post_title);
            mViewHolder.date = (TextView) convertView.findViewById(R.id.post_date);

            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        consulta = (Consulta) consultas.get(position);

        if(consulta.getLugar() == null){
            mViewHolder.author.setText("No definido");
        }else{
            mViewHolder.author.setText(consulta.getLugar());
        }

        mViewHolder.title.setText(consulta.getMotivo());
        mViewHolder.date.setText(consulta.getFecha());

        return convertView;
    }

    private static class ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView author;
        TextView date;
    }
}
