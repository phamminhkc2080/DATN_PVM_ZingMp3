package com.example.listtenmusic.Service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.CreateNotification;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.listtenmusic.R.drawable.pause1;
import static com.example.listtenmusic.R.drawable.pause_playnhac;

public class PlayNhacService extends Service  {
    IBinder iBinder = new BoundExample();
    ArrayList<BaiHat> arr;
    public static boolean isNext = false;
    boolean next;
    public static MediaPlayer mediaPlayer;
    private NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        createChannel();
//        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
        return iBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class BoundExample extends Binder {
        public PlayNhacService getService() {
            return PlayNhacService.this;
        }
    }

    //    public String getCurrentTime() {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM:mm:ss MM/dd/yyyy");
//        return (dateFormat.format(new Date()));
//    }
    public void setMediaPlayer(String link) {
//        mediaPlayer=null;
////        new PlayMP3().execute(link);
        PlayNhac(link);
        CreateNotification.createNotification(getApplicationContext(), PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size()-1);
    }

    class PlayMP3 extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat) {
            super.onPostExecute(baihat);
            try {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            mediaPlayer.stop();
                            mediaPlayer.reset();
                        }
                    });
                }
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
                if (!baihat.equals("")) {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(baihat);
                    mediaPlayer.prepare();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
//            Log.d("CCC", "init: "+mediaPlayer.getDuration());
            TimeSong();
            PlayNhacActivity playNhacActivity = new PlayNhacActivity();
            playNhacActivity.UpdateTime();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    isNext = true;
                    try {
                        Thread.sleep(3000);
                        PlayNhacActivity.bNext.callOnClick();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,"ListtenMusic", NotificationManager.IMPORTANCE_LOW);
//            CharSequence name = getString(R.string.channel_name);
//            String description = getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_LOW;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.cancelAll();
                notificationManager.createNotificationChannel(channel);
            }
        }
    }


    ////
    private void PlayNhac(String link) {
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
