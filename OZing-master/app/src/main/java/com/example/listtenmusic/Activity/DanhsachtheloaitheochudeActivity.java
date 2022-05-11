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
import android.widget.Toast;

import com.example.listtenmusic.Adapter.DanhsachTheloaiTheoChudeAdapter;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.ChuDe;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.Model.TheLoai;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.example.listtenmusic.Service.PlayNhacService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachtheloaitheochudeActivity extends AppCompatActivity {
    ChuDe chuDe;
    RecyclerView recyclerViewtheloaitheochude;
    Toolbar toolbar;
    DanhsachTheloaiTheoChudeAdapter danhsachTheloaiTheoChudeAdapter;
    private RelativeLayout reMini;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_danhsachtheloaitheochude);
        GetIntent();
        init();
        GetData();

    }

    private void GetData() {
        Log.d("CCC", "GetIDChuDe : " + chuDe.getIdChuDe());
        Dataservice dataservice = APIService.getService();
        Call<List<TheLoai>> callback = dataservice.GetTheLoaiTheoChude(chuDe.getIdChuDe());
        callback.enqueue(new Callback<List<TheLoai>>() {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response) {
                ArrayList<TheLoai> mangtheloai = (ArrayList<TheLoai>) response.body();
                Log.d("CCC", "ARRTheLoai : " + mangtheloai.size());

                danhsachTheloaiTheoChudeAdapter = new DanhsachTheloaiTheoChudeAdapter(DanhsachtheloaitheochudeActivity.this, mangtheloai);
                recyclerViewtheloaitheochude.setLayoutManager(new GridLayoutManager(DanhsachtheloaitheochudeActivity.this, 2));
                recyclerViewtheloaitheochude.setAdapter(danhsachTheloaiTheoChudeAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t) {
                Log.d("CCC", "error : " + t.getMessage());

            }
        });

    }

    private void init() {
        reMini = (RelativeLayout) findViewById(R.id.reMini);
        reMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachtheloaitheochudeActivity.this, PlayNhacActivity.class);
                ArrayList<BaiHat> mangbaihat = PlayNhacActivity.mangbaihat;
                int pos = PlayNhacActivity.pos;
                boolean repeat = PlayNhacActivity.repeat;
                boolean checkrandom = PlayNhacActivity.checkrandom;

                LayDulieutuPlayNhac layDulieutuPlayNhac = new LayDulieutuPlayNhac(mangbaihat, pos, repeat, checkrandom);
                intent.putExtra("miniplay", layDulieutuPlayNhac);
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
                handler.postDelayed(this, 300);
            }
        }, 300);
        recyclerViewtheloaitheochude = findViewById(R.id.recylerviewtheloaitheochude);
        toolbar = findViewById(R.id.toolbartheloaitheochude);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void GetIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra("theloaitheochude")) {
            chuDe = (ChuDe) intent.getSerializableExtra("theloaitheochude");
//            Toast.makeText(this,chuDe.getTenChuDe(),Toast.LENGTH_SHORT).show();
        }
    }
}