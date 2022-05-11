package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;

import java.util.ArrayList;

public class PlayNhacAdapter extends RecyclerView.Adapter<PlayNhacAdapter.ViewHolder>{
    Context context;
    ArrayList<BaiHat> mangbaihat;

    public PlayNhacAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_playbaihat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat=mangbaihat.get(position);
        holder.tTenCasi.setText(baiHat.getCasi());
        holder.tTenbaihat.setText(baiHat.getTenbaihat());
        holder.tIndex.setText(position+1+"");

    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tIndex,tTenbaihat,tTenCasi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tIndex=itemView.findViewById(R.id.tPlaynhacIndex);
            tTenbaihat=itemView.findViewById(R.id.tPlaynhacTenBaiHat);
            tTenCasi=itemView.findViewById(R.id.tPlaynhacTenCasi);
        }

    }
}
