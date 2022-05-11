package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.listtenmusic.R;

public class MainActivity extends AppCompatActivity {
    ImageView im_icon1,im_AnimIcon;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_open_app);
        im_icon1=(ImageView) findViewById(R.id.im_icon1);
        im_AnimIcon=(ImageView) findViewById(R.id.im_icon);

        Animation animationAphal= AnimationUtils.loadAnimation(this,R.anim.anim_aphal);
        im_AnimIcon.startAnimation(animationAphal);

        final Handler handlerChuyenlayout=new Handler();
        handlerChuyenlayout.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(MainActivity.this, Layout_main.class);
                startActivity(intent);
                finish();
                handlerChuyenlayout.removeCallbacks(this);
            }
        },2000);



//
//        im_icon1.setImageLevel(100);
//
//        final ClipDrawable clipDrawable=(ClipDrawable) im_icon1.getDrawable();
//
//        final Handler handler=new Handler();
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                int currentLever=clipDrawable.getLevel();
//                im_icon1.setImageLevel(clipDrawable.getLevel()+500);
//                handler.postDelayed(this,1);
//                if (currentLever>10000){
//                    Toast.makeText(MainActivity.this,"Chúc bạn nghe nhạc vui vẻ :)",Toast.LENGTH_SHORT).show();
//                    Handler handlerChuyenlayout=new Handler();
//                    handlerChuyenlayout.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            Intent intent=new Intent(MainActivity.this,Layout_main.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    },2000);
//                    handler.removeCallbacks(this);
//
//                }
//            }
//        },1);

    }

}