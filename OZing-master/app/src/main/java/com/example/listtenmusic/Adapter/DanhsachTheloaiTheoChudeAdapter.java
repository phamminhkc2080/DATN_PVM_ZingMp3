package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.DanhsachBaihatActivity;
import com.example.listtenmusic.Model.TheLoai;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhsachTheloaiTheoChudeAdapter extends RecyclerView.Adapter<DanhsachTheloaiTheoChudeAdapter.ViewHolder>{
    Context context;
    ArrayList<TheLoai> mangtheloai;

    public DanhsachTheloaiTheoChudeAdapter(Context context, ArrayList<TheLoai> mangtheloai) {
        this.context = context;
        this.mangtheloai = mangtheloai;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_theloaitheochude,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
TheLoai theLoai=mangtheloai.get(position);
        Picasso.with(context).load(theLoai.getHinhTheLoai()).into(holder.imhinhnen);
        holder.tTenTheloai.setText(theLoai.getTenTheLoai());
    }

    @Override
    public int getItemCount() {
        return mangtheloai.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
ImageView imhinhnen;
TextView tTenTheloai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imhinhnen=itemView.findViewById(R.id.imTheLoaiTheoChude);
            tTenTheloai=itemView.findViewById(R.id.tTenTheloaitheochude);
            imhinhnen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DanhsachBaihatActivity.class);
                    intent.putExtra("idtheloai",mangtheloai.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

}
