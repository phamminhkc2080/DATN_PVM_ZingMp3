package com.example.listtenmusic;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.listtenmusic.Activity.Layout_main;
import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.Service.NotificationActionService;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class CreateNotification {

    public static final String CHANNEL_ID = "channel1";

    public static final String ACTION_PREVIUOS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static final String ACTION_CLEAR = "actionclear";

    public static Notification notification;

    public static void createNotification(Context context, BaiHat track, int playbutton, int pos, int size) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            MediaSessionCompat mediaSessionCompat = new MediaSessionCompat( context, "tag");

            Bitmap icon  = null;
            try {
                icon = BitmapFactory.decodeStream((InputStream)new URL(track.getHinhbaihat()).getContent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            PendingIntent pendingIntentPrevious;
            int drw_previous;
//            if (pos == 0){
//                pendingIntentPrevious = null;
//                drw_previous = 0;
//            } else
                {
                Intent intentPrevious = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_PREVIUOS);
                pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                        intentPrevious, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_previous = R.drawable.ic_pre_black;
            }


            Intent intentPlay = new Intent(context, NotificationActionService.class)
                    .setAction(ACTION_PLAY);
            PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0,
                    intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);

            PendingIntent pendingIntentNext;
            int drw_next;
//            if (pos == size){
//                pendingIntentNext = null;
//                drw_next = 0;
//            } else
                {
                Intent intentNext = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_NEXT);
                pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                        intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
                drw_next = R.drawable.ic_next_black;
            }

            //onClickDelete
            PendingIntent pendingIntentClear;
            int drw_clear;
                Intent intentClear = new Intent(context, NotificationActionService.class)
                        .setAction(ACTION_CLEAR);
                pendingIntentClear = PendingIntent.getBroadcast(context, 0,
                        intentClear, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_clear = R.drawable.ic_clear;


            //onClickNoti
            Intent intentOnCLick = new Intent(context, PlayNhacActivity.class);
            ArrayList<BaiHat> mangbaihat = PlayNhacActivity.mangbaihat;
            int posIt = PlayNhacActivity.pos;
            boolean repeat = PlayNhacActivity.repeat;
            boolean checkrandom = PlayNhacActivity.checkrandom;

            LayDulieutuPlayNhac layDulieutuPlayNhac = new LayDulieutuPlayNhac(mangbaihat, posIt, repeat, checkrandom);
            intentOnCLick.putExtra("miniplay", layDulieutuPlayNhac);
            PendingIntent contentIntent =
                    PendingIntent.getActivity(context, 0, intentOnCLick, PendingIntent.FLAG_UPDATE_CURRENT);
            //create notification
            notification=null;
            notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_library_music)
                    .setContentTitle(track.getTenbaihat())
                    .setContentText(track.getCasi())
                    .setLargeIcon(icon)
//                    .setOnlyAlertOnce(true)//show notification for only first time
//                    .setShowWhen(false)
                    .addAction(drw_previous, "Previous", pendingIntentPrevious)
                    .addAction(playbutton, "Play", pendingIntentPlay)
                    .addAction(drw_next, "Next", pendingIntentNext)
                    .addAction(drw_clear, "Clear", pendingIntentClear)
                    .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.getSessionToken())
                    )
                    .setOngoing(true)
                    .setContentIntent(contentIntent)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();

            notificationManagerCompat.notify(1, notification);


        }
    }
}
