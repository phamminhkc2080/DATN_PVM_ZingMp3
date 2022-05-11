package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.listtenmusic.Adapter.DanhsachtatcachudeAdapter;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.ChuDe;
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

public class DanhsachtatcachudeActivity extends AppCompatActivity {
Toolbar toolbartatcachude;
RecyclerView recyclerViewtatcachude;
    DanhsachtatcachudeAdapter danhsachtatcachudeAdapter;
    private RelativeLayout reMini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachtatcachude);
        init();
        GetData();
    }

    private void GetData() {
        Dataservice dataservice= APIService.getService();
        Call<List<ChuDe>> callback=dataservice.GetDataALLChuDe();
        callback.enqueue(new Callback<List<ChuDe>>() {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response) {
                ArrayList<ChuDe> mangchude= (ArrayList<ChuDe>) response.body();
                Log.d("EEE","ArrChuDeesda : "+mangchude.get(0).toString());
                danhsachtatcachudeAdapter=new DanhsachtatcachudeAdapter(DanhsachtatcachudeActivity.this,mangchude);
                recyclerViewtatcachude.setLayoutManager(new GridLayoutManager(DanhsachtatcachudeActivity.this,1));
                recyclerViewtatcachude.setAdapter(danhsachtatcachudeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t) {

            }
        });
    }

    private void init() {
        reMini=(RelativeLayout) findViewById(R.id.reMini);
        reMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachtatcachudeActivity.this,PlayNhacActivity.class);
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
        recyclerViewtatcachude=findViewById(R.id.recylerviewdanhtatcachude);
        toolbartatcachude=findViewById(R.id.toolbardanhsachtatcachude);
        setSupportActionBar(toolbartatcachude);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbartatcachude.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}