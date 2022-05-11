package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.listtenmusic.R;

public class HuongDanSuDungActivity extends AppCompatActivity {
Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huong_dan_su_dung);
        toolbar=(Toolbar) findViewById(R.id.toolbarHDSD);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Hướng dẫn sử dụng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}