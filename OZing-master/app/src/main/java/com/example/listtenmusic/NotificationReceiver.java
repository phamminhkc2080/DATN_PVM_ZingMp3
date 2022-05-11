package com.example.listtenmusic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.Service.PlayNhacService;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer=PlayNhacService.mediaPlayer;

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        int id = intent.getIntExtra("EXTRA_BUTTON_CLICKED", -1);
        context.sendBroadcast(new Intent("TRACKS_TRACKS")
                .putExtra("EXTRA_BUTTON_CLICKED", id));
//        switch (id) {
//            case R.id.im_pre_tb: {
//                Toast.makeText(context, "pre", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case R.id.im_play_tb: {
//                ImageView imageView=view.findViewById(R.id.im_play_tb);
//                if (mediaPlayer.isPlaying()==true){
//
//                    mediaPlayer.pause();
//                }
//                else {
//                    mediaPlayer.start();
//                }
//                break;
//            }
//            case R.id.im_next_tb: {
//                Toast.makeText(context, "next", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case R.id.im_close_tb: {
//                Toast.makeText(context, "close", Toast.LENGTH_SHORT).show();
//                break;
//            }
//        }
    }
    public void intt(){

    }
}
