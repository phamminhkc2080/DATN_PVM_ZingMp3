package com.example.listtenmusic.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HenGioService extends Service {
    IBinder iBinder = new BoundExample();
    public static int time;
    boolean check = false;
    public static boolean clickswitch=false;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (check == true) {
                    if (time > 0) {
                        time = time - 1;
                    }
                    else {
                        if(clickswitch==false){
                        Toast.makeText(HenGioService.this, "Hết giờ", Toast.LENGTH_SHORT).show();
                        if(PlayNhacService.mediaPlayer!=null && PlayNhacService.mediaPlayer.isPlaying()){
                            PlayNhacService.mediaPlayer.pause();
                            PlayNhacActivity.bPlay.setImageResource(R.drawable.play_playnhac);
                        }
                        check=false;
                        }
                    }
                }
                handler.postDelayed(this, 1000);
            }
        }, 1000);
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
        public HenGioService getService() {
            return HenGioService.this;
        }
    }

    public void setTime(int t) {
        if (t > 0) {
            time = t * 60;
            check = true;
        }
    }

    public int getTime() {
        return time;
    }

    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String getDurationString1(long seconds) {

        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(long number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

}
