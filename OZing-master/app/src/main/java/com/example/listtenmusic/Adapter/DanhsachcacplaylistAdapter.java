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
import com.example.listtenmusic.Model.PlayList;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DanhsachcacplaylistAdapter extends RecyclerView.Adapter<DanhsachcacplaylistAdapter.ViewHolder> {
    Context context;
    ArrayList<PlayList> mangplaylist;

    public DanhsachcacplaylistAdapter(Context context, ArrayList<PlayList> mangplaylist) {
        this.context = context;
        this.mangplaylist = mangplaylist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_danhsachcacplaylist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlayList playList = mangplaylist.get(position);
        Picasso.with(context).load(playList.getHinhPlayList()).into(holder.imHinhnen);
        holder.tTenPlaylist.setText(playList.getTen());
    }

    @Override
    public int getItemCount() {
        return mangplaylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imHinhnen;
        TextView tTenPlaylist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imHinhnen = itemView.findViewById(R.id.imdanhsachcacplaylist);
            tTenPlaylist = itemView.findViewById(R.id.tTendanhsachcacplaylist);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DanhsachBaihatActivity.class);
                    intent.putExtra("itemplaylist", mangplaylist.get(getPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
