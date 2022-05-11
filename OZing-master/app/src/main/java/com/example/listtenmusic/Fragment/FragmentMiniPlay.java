package com.example.listtenmusic.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.CreateNotification;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.PlayNhacService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.BIND_AUTO_CREATE;
import static com.example.listtenmusic.R.drawable.pause1;
import static com.example.listtenmusic.R.drawable.pause_playnhac;


public class FragmentMiniPlay  extends Fragment {
    View view;
    int pos=0;
    ServiceConnection serviceConnection;
    PlayNhacService playNhacService;
    private boolean iboundservice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.miniplay,container,false);
        final ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progressBar) ;
        final ImageButton bNext=(ImageButton) view.findViewById(R.id.bEnd);
        final ImageButton bSkiptostart=(ImageButton) view.findViewById(R.id.bStart);
        final CircleImageView imChay=(CircleImageView) view.findViewById(R.id.imChay);
        final TextView tTenBaiHat=(TextView)view.findViewById(R.id.textViewMini);
        final ImageButton bPlay=(ImageButton) view.findViewById(R.id.bPlayMini);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayNhacService.mediaPlayer.isPlaying()) {
                    PlayNhacService.mediaPlayer.pause();
                    bPlay.setImageResource(R.drawable.play1);
                    CreateNotification.createNotification(getContext(), PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                            R.drawable.ic_play_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);
                } else {
                    PlayNhacService.mediaPlayer.start();
                    bPlay.setImageResource(R.drawable.pause1);
                    CreateNotification.createNotification(getContext(), PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                            R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);
                }
            }
        });
        bSkiptostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayNhacActivity.mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                        PlayNhacService.mediaPlayer.stop();
//                        PlayNhacService.mediaPlayer.release();
//                        PlayNhacService.mediaPlayer = null;
//                    }
                    if (PlayNhacActivity.pos < (PlayNhacActivity.mangbaihat.size())) {
                        bPlay.setImageResource(pause_playnhac);
                        PlayNhacActivity.pos--;
                        if (PlayNhacActivity.pos < 0) {
                            PlayNhacActivity.pos = PlayNhacActivity.mangbaihat.size() - 1;
                        }
                        if (PlayNhacActivity.repeat == true) {

                            PlayNhacActivity.pos += 1;

                        }
                        if (PlayNhacActivity.checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(PlayNhacActivity.mangbaihat.size());
                            if (index == PlayNhacActivity.pos) {
                                PlayNhacActivity.pos = index - 1;
                            }
                            PlayNhacActivity.pos = index;
                        }

//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                        ConnectService(PlayNhacActivity.mangbaihat, PlayNhacActivity.pos);
//                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
//                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//                        UpdateTime();
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
                    }
                }
                bSkiptostart.setClickable(false);
                bNext.setClickable(false);
                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bSkiptostart.setClickable(true);
                        bNext.setClickable(true);
                    }
                }, 5000);
            }
        });
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayNhacActivity.mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                            PlayNhacService.mediaPlayer.stop();
//                            PlayNhacService.mediaPlayer.release();
//                            PlayNhacService.mediaPlayer = null;
//                    }
                    if (PlayNhacActivity.pos < (PlayNhacActivity.mangbaihat.size())) {
                        bPlay.setImageResource(pause1);
                        PlayNhacActivity.pos++;
                        if (PlayNhacActivity.repeat == true) {
                            if (PlayNhacActivity.pos == 0) {
                                PlayNhacActivity.pos = PlayNhacActivity.mangbaihat.size();
                            }
                            PlayNhacActivity.pos -= 1;

                        }
                        if (PlayNhacActivity.checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(PlayNhacActivity.mangbaihat.size());
                            if (index == PlayNhacActivity.pos) {
                                PlayNhacActivity.pos = index - 1;
                            }
                            PlayNhacActivity.pos = index;
                        }
                        if (PlayNhacActivity.pos > (PlayNhacActivity.mangbaihat.size() - 1)) {
                            PlayNhacActivity.pos = 0;

                        }

//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                        ConnectService(PlayNhacActivity.mangbaihat, PlayNhacActivity.pos);
//                        PUpdateTime();
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
                        bSkiptostart.setClickable(false);
                        bNext.setClickable(false);
                        Handler handler1 = new Handler();
                        handler1.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                bSkiptostart.setClickable(true);
                                bNext.setClickable(true);
                            }
                        }, 5000);
                    }
            }
            }
        });
        final String[] t = {""};
        final Animation animation=AnimationUtils.loadAnimation(getActivity(),R.anim.anim_rotate);
        imChay.startAnimation(animation);
        final int[] k = {0};
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayNhacService.mediaPlayer != null) {
                    t[0] =PlayNhacActivity.TenBaiHat+"";
                    tTenBaiHat.setText(t[0]);
                    if(PlayNhacActivity.LinkHinhAnh!=null){
                    Picasso.with(getActivity()).load(PlayNhacActivity.LinkHinhAnh).into(imChay);}
                    else {
                        imChay.setImageResource(R.drawable.cdburning);
                    }
//                        String ten=PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos).getTenBaiHat();
//                        tTenBaiHat.setText(ten);
//                        Picasso.with(getActivity()).load(PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos).getHinhBaiHat()).into(imChay);
                    k[0] =PlayNhacService.mediaPlayer.getDuration();
                    if (PlayNhacService.mediaPlayer.isPlaying()) {
//                        Log.d("BBB", "run: "+PlayNhacActivity.TenBaiHat);

                        bPlay.setImageResource(R.drawable.pause1);
                    }
                    else {
                        bPlay.setImageResource(R.drawable.play1);

                    }
                }
                handler.postDelayed(this,1000);
            }
        }, 1000);
        final Handler handlerProgress=new Handler();
        handlerProgress.postDelayed(new Runnable() {
            @Override
            public void run() {
                int d=0;
                if(d!=k[0]){
                    d=k[0];
                    progressBar.setMax(d);
                    if(PlayNhacService.mediaPlayer!=null) {
                        progressBar.setProgress(PlayNhacService.mediaPlayer.getCurrentPosition());
                    }
                }
                handlerProgress.postDelayed(this,300);
            }
        },300);
        return view;
    }
    private void ConnectService(@Nullable final ArrayList<BaiHat> arrbaihat, @Nullable final Integer position) {
//        playNhacService.setMediaPlayer("https://quyetdaik922.000webhostapp.com/MP3/Ai%20Mang%20Co%20Don%20Di%20-%20K-ICM_%20APJ.mp3");
        Intent intent = new Intent(getActivity(), PlayNhacService.class);
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayNhacService.BoundExample binder = (PlayNhacService.BoundExample) service;
                playNhacService = binder.getService();
                playNhacService.setMediaPlayer(arrbaihat.get(position).getLinkbaihat());
                iboundservice = true;
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                iboundservice = false;
            }
        };
        getActivity().bindService(intent, serviceConnection, BIND_AUTO_CREATE);


    }
}
