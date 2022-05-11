package com.example.listtenmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.DanhSachTatcaAlbumActivity;
import com.example.listtenmusic.Adapter.AlbumAdapter;
import com.example.listtenmusic.Model.Album;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentAlbum extends Fragment {
    View view;
RecyclerView recyclerViewAlbum;
TextView tXemthemAlbum;
AlbumAdapter albumAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_album,container,false);
        recyclerViewAlbum=view.findViewById(R.id.recylerviewAlbum);
        tXemthemAlbum=view.findViewById(R.id.tXemthemAlbum);

    GetData();
    tXemthemAlbum.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(), DanhSachTatcaAlbumActivity.class);
            startActivity(intent);
        }
    });
    return view;
    }

    private void GetData() {
        Dataservice dataservice= APIService.getService();
        Call<List<Album>> callback=dataservice.GetDataAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> albumArrayList= (ArrayList<Album>) response.body();
                albumAdapter=new AlbumAdapter(getActivity(),albumArrayList);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerViewAlbum.setLayoutManager(linearLayoutManager);
                recyclerViewAlbum.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                Log.d("BBB", "fall: ");
            }
        });
    }
}
