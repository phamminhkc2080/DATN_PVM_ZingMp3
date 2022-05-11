package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.DanhsachtheloaitheochudeActivity;
import com.example.listtenmusic.Model.ChuDe;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhsachtatcachudeAdapter extends RecyclerView.Adapter<DanhsachtatcachudeAdapter.ViewHolder> {
    Context context;
    ArrayList<ChuDe> mangchude;

    public DanhsachtatcachudeAdapter(Context context, ArrayList<ChuDe> mangchude) {
        this.context = context;
        this.mangchude = mangchude;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_cac_chude, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ChuDe chuDe = mangchude.get(position);
        Picasso.with(context).load(chuDe.getHinhChuDe()).into(holder.imChude);
    }

    @Override
    public int getItemCount() {
        return mangchude.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imChude;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imChude = itemView.findViewById(R.id.imdongcacchude);
            imChude.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachtheloaitheochudeActivity.class);
                    intent.putExtra("theloaitheochude", mangchude.get(getPosition()));
                    Log.d("DDD", "ArrString : " + mangchude.get(getPosition()).toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
