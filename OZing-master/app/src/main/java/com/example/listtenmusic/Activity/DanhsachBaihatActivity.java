package com.example.listtenmusic.Activity;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.listtenmusic.Adapter.DanhsachbaihatAdapter;
import com.example.listtenmusic.Model.Album;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.Model.PlayList;
import com.example.listtenmusic.Model.QuangCao;
import com.example.listtenmusic.Model.TheLoai;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.example.listtenmusic.Service.PlayNhacService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhsachBaihatActivity extends AppCompatActivity {
    RelativeLayout reMini;
    QuangCao quangCao;
    PlayList playList;
    TheLoai theLoai;
    Album album;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerViewdanhsachbaihat;
    FloatingActionButton floatingActionButton;
    ImageView imdanhsachcakhuc;
    DanhsachbaihatAdapter danhsachbaihatAdapter;
    ArrayList<BaiHat> mangBaihat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsach_baihat);
        //Tranhs phat sinh khi su dung mang, Kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        DataIntent();
        init();
        if (quangCao != null && !quangCao.getTenBaiHat().equals("")) {
//            Toast.makeText(this,quangCao.getIDQuangCao()+"OO",Toast.LENGTH_SHORT).show();
            setValuesInView(quangCao.getTenBaiHat(), quangCao.getHinhBaiHat());
            GetDataQuangCao(quangCao.getIdQuangCao().trim() + "");
        }
        if (playList != null && !playList.getTen().equals("")) {
            setValuesInView(playList.getTen(), playList.getHinhPlayList());
            GetDataPlaylist(playList.getIdPlayList());

        }
        if (theLoai != null && !theLoai.getTenTheLoai().equals("")) {
            setValuesInView(theLoai.getTenTheLoai(), theLoai.getHinhTheLoai());
            GetDataTheLoai(theLoai.getIDTheLoai());
        }
        if (album != null && !album.getTenAlbum().equals("")) {
            setValuesInView(album.getTenAlbum(), album.getHinhAlbum());
            GetDataAlbum(album.getIdAlbum());
        }
    }

    private void GetDataAlbum(String idAlbum) {
        Dataservice dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhsachbaihatAlbum(idAlbum);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaihat = (ArrayList<BaiHat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachBaihatActivity.this, mangBaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachBaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
            eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void GetDataTheLoai(String idtheloai) {
        Dataservice dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhsachbaihatTheloai(idtheloai);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaihat = (ArrayList<BaiHat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachBaihatActivity.this, mangBaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachBaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
            eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void GetDataPlaylist(String idplaylist) {
        Dataservice dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhsachbaihatplaylist(idplaylist);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaihat = (ArrayList<BaiHat>) response.body();
                //Log.d("CCC", "MangPlaylist" + mangBaihat.size());
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachBaihatActivity.this, mangBaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachBaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
            eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

            }
        });
    }

    private void GetDataQuangCao(String idquangcao) {
        Dataservice dataservice = APIService.getService();
        Call<List<BaiHat>> callback = dataservice.GetDanhsachbaihatquangcao(idquangcao + "");
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                mangBaihat = (ArrayList<BaiHat>) response.body();
                danhsachbaihatAdapter = new DanhsachbaihatAdapter(DanhsachBaihatActivity.this, mangBaihat);
                recyclerViewdanhsachbaihat.setLayoutManager(new LinearLayoutManager(DanhsachBaihatActivity.this));
                recyclerViewdanhsachbaihat.setAdapter(danhsachbaihatAdapter);
            eventClick();
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {
               // Log.d("CCC", "" + t.getMessage());
            }
        });
    }

    private void setValuesInView(String ten, String hinh) {
        collapsingToolbarLayout.setTitle(ten);
        try {
            URL url = new URL(hinh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), bitmap);
            collapsingToolbarLayout.setBackground(bitmapDrawable);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Picasso.with(this).load(hinh).into(imdanhsachcakhuc);
    }

    private void init() {
        reMini=(RelativeLayout) findViewById(R.id.reMini);
        reMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachBaihatActivity.this,PlayNhacActivity.class);
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
        imdanhsachcakhuc = findViewById(R.id.imdanhsachcakhuc);
        coordinatorLayout = findViewById(R.id.coordinator);
        collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        toolbar = findViewById(R.id.toolbardanhsachbaihat);
        recyclerViewdanhsachbaihat = findViewById(R.id.recylerviewdanhsachbaihat);
        floatingActionButton = findViewById(R.id.floatingactionbutton);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        floatingActionButton.setEnabled(false);
    }

    private void DataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("QuangCao")) {
                quangCao = (QuangCao) intent.getSerializableExtra("QuangCao");
//                Toast.makeText(this,quangCao.getTenBaiHat(),Toast.LENGTH_SHORT).show();
            }
            if (intent.hasExtra("itemplaylist")) {
                playList = (PlayList) intent.getSerializableExtra("itemplaylist");
            }
            if (intent.hasExtra("idtheloai")) {
                theLoai = (TheLoai) intent.getSerializableExtra("idtheloai");
            }
            if (intent.hasExtra("album")) {
                album = (Album) intent.getSerializableExtra("album");
            }
        }
    }
    private void eventClick(){
        floatingActionButton.setEnabled(true);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DanhsachBaihatActivity.this,PlayNhacActivity.class);
                intent.putExtra("cacbaihat",mangBaihat);
                startActivity(intent);
            }
        });
    }
}