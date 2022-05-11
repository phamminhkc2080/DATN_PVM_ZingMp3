package com.example.listtenmusic.Service;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.CreateNotification;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class NotificationActionService extends BroadcastReceiver {

    NotificationManager notificationManager;
    private ServiceConnection serviceConnection;
    private MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
//        context.sendBroadcast(new Intent("TRACKS_TRACKS")
//                .putExtra("Action_Name", intent.getAction()));
//        Log.d("BBB", "onReceive: ");
//        String nhan=intent.getAction();
//        String a=intent.getStringExtra("play");
//        Toast.makeText(context, "adb"+nhan+"-"+a, Toast.LENGTH_SHORT).show();
        String action = intent.getAction();
//        Toast.makeText(context, "ac:"+action, Toast.LENGTH_SHORT).show();
        switch (action) {
            case CreateNotification.ACTION_PREVIUOS:
                //phải gọi onclick trc vì sau khi gọi lại thì pos ms thay đổi
//                    PlayNhacActivity.bSkiptostart.callOnClick();
                if (PlayNhacActivity.mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                        PlayNhacService.mediaPlayer.stop();
//                        PlayNhacService.mediaPlayer.release();
//                        PlayNhacService.mediaPlayer = null;
//                    }
                    if (PlayNhacActivity.pos < (PlayNhacActivity.mangbaihat.size())) {
//                            bPlay.setImageResource(pause_playnhac);
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
                        PlayNhac(PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos).getLinkbaihat());
//                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
//                        PlayNhacActivity.getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//                        UpdateTime();
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
                    }
                }
                CreateNotification.createNotification(context, PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                        R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);

                break;
            case CreateNotification.ACTION_PLAY: {

                if (PlayNhacService.mediaPlayer.isPlaying()) {
//                        PlayNhacActivity.bPlay.callOnClick();
                    PlayNhacService.mediaPlayer.pause();
                    PlayNhacActivity.bPlay.setImageResource(R.drawable.play_playnhac);
                    CreateNotification.createNotification(context, PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                            R.drawable.ic_play_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);

                } else {
//                        PlayNhacActivity.bPlay.callOnClick();
                    PlayNhacService.mediaPlayer.start();
                    PlayNhacActivity.bPlay.setImageResource(R.drawable.pause_playnhac);
                    CreateNotification.createNotification(context, PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                            R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);

                }
                break;
            }
            case CreateNotification.ACTION_NEXT:
//                    PlayNhacActivity.bNext.callOnClick();
                if (PlayNhacActivity.mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                            PlayNhacService.mediaPlayer.stop();
//                            PlayNhacService.mediaPlayer.release();
//                            PlayNhacService.mediaPlayer = null;
//                    }
                    if (PlayNhacActivity.pos < (PlayNhacActivity.mangbaihat.size())) {
//                            PlayNhacActivity.bPlay.setImageResource(pause_playnhac);
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
                        PlayNhac(PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos).getLinkbaihat());
//                        PUpdateTime();
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
//                            bSkiptostart.setClickable(false);
//                            bNext.setClickable(false);
//                            Handler handler1 = new Handler();
//                            handler1.postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    bSkiptostart.setClickable(true);
//                                    bNext.setClickable(true);
//                                }
//                            }, 5000);
                    }
                }
                CreateNotification.createNotification(context, PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                        R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);

                break;
            case CreateNotification.ACTION_CLEAR:
                PlayNhacService.mediaPlayer.pause();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,"ListtenMusic", NotificationManager.IMPORTANCE_LOW);
                     notificationManager = context.getSystemService(NotificationManager.class);
                    if (notificationManager != null) {
                        notificationManager.cancelAll();
                        notificationManager.createNotificationChannel(channel);
                    }
                }
                break;
        }
    }
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Intent it=getIntent();
//            if(it.getAction()!=null && it.getAction().equals("TRACKS_TRACKS")){

        }
    };
    private void PlayNhac(String link) {
        PlayNhacService.mediaPlayer.stop();
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(link);
            mediaPlayer.prepare();
            mediaPlayer.start();
            TimeSong();
            PlayNhacActivity playNhacActivity = new PlayNhacActivity();
            playNhacActivity.UpdateTime();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    try {
                        Thread.sleep(3000);
                        if (PlayNhacActivity.pos == PlayNhacActivity.mangbaihat.size() - 1) {
                            if (PlayNhacActivity.repeat == true) {
                                PlayNhacActivity.bNext.callOnClick();
                            }
                        }
                        else {
                            PlayNhacActivity.bNext.callOnClick();
                        }

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
            PlayNhacService.mediaPlayer=mediaPlayer;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        PlayNhacActivity.tTimetong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        PlayNhacActivity.seekBartime.setMax(mediaPlayer.getDuration());

    }





}