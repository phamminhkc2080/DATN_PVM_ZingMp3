package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.listtenmusic.Adapter.MainViewPagerAdapter;
import com.example.listtenmusic.Database;
import com.example.listtenmusic.Fragment.FragmentBaiHatYeuThich;
import com.example.listtenmusic.Fragment.FragmentVideoYeuthich;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.PlayNhacService;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DanhsachyeuthichActivity extends AppCompatActivity {

Toolbar toolbar;
ViewPager viewPager;
TabLayout tabLayout;
    RelativeLayout reMini;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachyeuthich);
        reMini = (RelativeLayout) findViewById(R.id.reMini);
        toolbar=(Toolbar) findViewById(R.id.toolbarDanhsachyeuthich);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Danh sách yêu thích");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewPager=(ViewPager) findViewById(R.id.viewpagerDanhsachyethich);
        tabLayout=(TabLayout) findViewById(R.id.myTabLayoutYeuthich);
        tabLayout.setVerticalScrollBarEnabled(true);
        tabLayout.setVerticalFadingEdgeEnabled(true);
//        tabLayout.setV


        MainViewPagerAdapter mainViewPagerAdapter=new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPagerAdapter.addFragment(new FragmentBaiHatYeuThich(),"Bài hát");
        mainViewPagerAdapter.addFragment(new FragmentVideoYeuthich(),"Videos");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayNhacService.mediaPlayer != null) {
                    if (PlayNhacService.mediaPlayer.isPlaying()){
                        reMini.setVisibility(View.VISIBLE);}
                }
                handler.postDelayed(this,300);
            }
        }, 300);
        reMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DanhsachyeuthichActivity.this,PlayNhacActivity.class);
                ArrayList<BaiHat> mangbaihat=PlayNhacActivity.mangbaihat;
                int pos=PlayNhacActivity.pos;
                boolean repeat=PlayNhacActivity.repeat;
                boolean checkrandom=PlayNhacActivity.checkrandom;

                LayDulieutuPlayNhac layDulieutuPlayNhac=new LayDulieutuPlayNhac(mangbaihat,pos,repeat,checkrandom);
                intent.putExtra("miniplay",layDulieutuPlayNhac);
                startActivity(intent);

            }
        });


    }

}