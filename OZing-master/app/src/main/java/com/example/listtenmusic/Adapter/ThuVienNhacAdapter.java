package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ThuVienNhacAdapter extends BaseAdapter {
    Context context;
    ArrayList<BaiHat>arrbaihat;
    View view;
    public ThuVienNhacAdapter(Context context, ArrayList<BaiHat> arrbaihat) {
        this.context = context;
        this.arrbaihat = arrbaihat;
    }

    @Override
    public int getCount() {
        return arrbaihat.size();
    }

    @Override
    public Object getItem(int position) {
        return arrbaihat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView=inflater.inflate(R.layout.dong_thuvienbaihat,null);
        ImageView imageView=(ImageView) convertView.findViewById(R.id.imthuvien);
        TextView tTenBaiHat=(TextView) convertView.findViewById(R.id.tTenbaihatThuvien);
        TextView tCaSi=(TextView) convertView.findViewById(R.id.tTenCasiThuvien);

        tCaSi.setText(arrbaihat.get(position).getCasi());
        tTenBaiHat.setText(arrbaihat.get(position).getTenbaihat());


        return convertView;
    }
}
