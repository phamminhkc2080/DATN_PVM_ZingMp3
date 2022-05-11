package com.example.listtenmusic.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Adapter.AllAlbumAdapter;
import com.example.listtenmusic.Model.Album;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.example.listtenmusic.Service.PlayNhacService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachTatcaAlbumActivity extends AppCompatActivity {
RecyclerView recyclerViewallalbum;
Toolbar toolbar;
    AllAlbumAdapter allAlbumAdapter;
    private RelativeLayout reMini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_tatca_album);
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice= APIService.getService();
        Call<List<Album>> callback=dataservice.GetTatCaAlbum();
        callback.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                ArrayList<Album> mangAlbum= (ArrayList<Album>) response.body();
                allAlbumAdapter=new AllAlbumAdapter(DanhSachTatcaAlbumActivity.this,mangAlbum);
            recyclerViewallalbum.setLayoutManager(new GridLayoutManager(DanhSachTatcaAlbumActivity.this,2));
            recyclerViewallalbum.setAdapter(allAlbumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {

            }
        });
    }

    private void init() {
        reMini=(RelativeLayout) findViewById(R.id.reMini);
        reMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhSachTatcaAlbumActivity.this,PlayNhacActivity.class);
                ArrayList<BaiHat> mangbaihat=PlayNhacActivity.mangbaihat;
                int pos=PlayNhacActivity.pos;
                boolean repeat=PlayNhacActivity.repeat;
                boolean checkrandom=PlayNhacActivity.checkrandom;

                LayDulieutuPlayNhac layDulieutuPlayNhac=new LayDulieutuPlayNhac(mangbaihat,pos,repeat,checkrandom);
                intent.putExtra("miniplay",layDulieutuPlayNhac);
                startActivity(intent);

            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayNhacService.mediaPlayer != null) {
                    reMini.setVisibility(View.VISIBLE);
                }
                handler.postDelayed(this,300);
            }
        }, 300);
        recyclerViewallalbum=findViewById(R.id.recylerviewTatCaAlbum);
        toolbar=findViewById(R.id.toolbartatcaalbum);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất cả các Album");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}