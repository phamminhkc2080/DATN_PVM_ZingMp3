package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.listtenmusic.Model.PlayList;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaylistAdapter extends ArrayAdapter<PlayList> {
    public PlaylistAdapter(@NonNull Context context, int resource, @NonNull List<PlayList> objects) {
        super(context, resource, objects);
    }
    class ViewHolder{
        TextView tTenPlaylist;
        ImageView imbackground,imPlaylist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView==null){
            LayoutInflater inflater=LayoutInflater.from(getContext());
            convertView=inflater.inflate(R.layout.dong_playlist,null);
            viewHolder=new ViewHolder();
            viewHolder.tTenPlaylist=convertView.findViewById(R.id.tTenPlaylist);
            viewHolder.imPlaylist=convertView.findViewById(R.id.imPlaylist);
            viewHolder.imbackground=convertView.findViewById(R.id.imBackgroundPlaylist);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();

        }
        PlayList playList=getItem(position);
        Picasso.with(getContext()).load(playList.getHinhPlayList()).into(viewHolder.imbackground);
        Picasso.with(getContext()).load(playList.getIcon()).into(viewHolder.imPlaylist);
        viewHolder.tTenPlaylist.setText(playList.getTen());


        return convertView;
    }
}
