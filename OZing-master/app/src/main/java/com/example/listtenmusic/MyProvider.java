package com.example.listtenmusic;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.listtenmusic.Model.BaiHat;

import java.util.ArrayList;

public class MyProvider {
    public static final String TAG = "MyProvider";
    private ContentResolver mResolver;
    private Context mContext;

    public MyProvider(Context mContext) {
        this.mContext = mContext;
        mResolver = mContext.getContentResolver();
    }

    // Load toan bo file nhac
    public ArrayList<BaiHat> getData() {
        ArrayList<BaiHat> arr = new ArrayList<BaiHat>();
        // Thiết lập URI
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        // Thiet lap điều kiện chị lấy file nhạc
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        // Thiet lập các trường sẽ được lấy
        final String[] projection = new String[]{
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION

        };
        // Sắp xếp file nhạc theo tên
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";
        try {
            Cursor cursor = mResolver.query(uri, projection, selection, null, sortOrder);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String tenbaihat = cursor.getString(0);
                String casi = cursor.getString(1);
                String link = cursor.getString(2);
                Log.d("CCC", "getData: "+tenbaihat);
//                String album = cursor.getString(3);
//                int duration = cursor.getInt(4);
//                BaiHat itemSong = new BaiHat(title, artist, data, album, duration);
                BaiHat baiHat=new BaiHat(tenbaihat,casi,link);
                arr.add(baiHat);
                cursor.moveToNext();
            }

            return arr;
        } catch (Exception ex) {
            Toast.makeText(mContext, "Không tìm thấy file nhạc nào ", Toast.LENGTH_SHORT).show();
        }
        return  null;
    }
}