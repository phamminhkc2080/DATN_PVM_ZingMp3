package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listtenmusic.Adapter.ThuVienNhacAdapter;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.MyProvider;
import com.example.listtenmusic.R;

import java.util.ArrayList;

public class ThuVienNhacActivity extends AppCompatActivity {
ArrayList<BaiHat> mangbaihat=new ArrayList<>();
Toolbar toolbar;
ListView lvThuViennhac;
ThuVienNhacAdapter thuVienNhacAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thu_vien_nhac);
        init();
    }

    private void init() {
        toolbar=(Toolbar) findViewById(R.id.toolbarThuviennhac);
        lvThuViennhac=(ListView) findViewById(R.id.lvthuviennhac);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thư viện nhạc");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        MyProvider myProvider=new MyProvider(this);
        mangbaihat=myProvider.getData();
        if (mangbaihat.size()>0) {
            thuVienNhacAdapter = new ThuVienNhacAdapter(ThuVienNhacActivity.this,mangbaihat);
            lvThuViennhac.setAdapter(thuVienNhacAdapter);
            lvThuViennhac.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(ThuVienNhacActivity.this,PlayNhacActivity.class);
                    intent.putExtra("cakhuc",mangbaihat.get(position));
                    startActivity(intent);
                }
            });
        }else {
            Toast.makeText(this, "không có bài hát nào", Toast.LENGTH_SHORT).show();
        }
    }
}