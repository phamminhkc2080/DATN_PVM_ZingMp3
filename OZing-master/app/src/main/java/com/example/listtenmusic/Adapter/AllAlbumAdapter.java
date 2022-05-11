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
import com.example.listtenmusic.Model.Album;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllAlbumAdapter  extends RecyclerView.Adapter<AllAlbumAdapter.ViewHolder>{
    Context context;
    ArrayList<Album> albumArrayList;

    public AllAlbumAdapter(Context context, ArrayList<Album> albumArrayList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_all_album,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
Album album=albumArrayList.get(position);
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imAllAlbum);
        holder.tTenAllbum.setText(album.getTenAlbum());
        holder.tTenCasiAllalbum.setText(album.getTenCaSiAlbum());
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imAllAlbum;
        TextView tTenAllbum,tTenCasiAllalbum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imAllAlbum=itemView.findViewById(R.id.imAllAlbum);
            tTenAllbum=itemView.findViewById(R.id.tTenAllAlbum);
            tTenCasiAllalbum=itemView.findViewById(R.id.tTenCaSiAllAlbum);
            imAllAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, DanhsachBaihatActivity.class);
                    intent.putExtra("album",albumArrayList.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
