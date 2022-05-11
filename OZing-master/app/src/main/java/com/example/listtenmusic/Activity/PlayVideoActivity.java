package com.example.listtenmusic.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.listtenmusic.Database;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.example.listtenmusic.Service.PlayNhacService;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayVideoActivity extends AppCompatActivity {
    PlayerView playerView;
    ProgressBar progressBar;
    ImageView bfullscreen, b_backvideo, bLoveVideo;
    SimpleExoPlayer simpleExoPlayer;
    boolean flag = false;
    TextView tTenVideo;
    BaiHat baiHat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        if (PlayNhacService.mediaPlayer != null && PlayNhacService.mediaPlayer.isPlaying()) {
            PlayNhacService.mediaPlayer.pause();
        }
        init();
        GetDataIntent();
        events();
    }

    private void events() {
        Uri uri = Uri.parse(baiHat.getLinkbaihat());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        LoadControl loadControl = new DefaultLoadControl();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();

        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(PlayVideoActivity.this, trackSelector, loadControl);
//
//        String userAgent = Util.getUserAgent(MainActivity.this,getApplicationName(MainActivity.this));
//        DefaultHttpDataSourceFactory factory=new DefaultHttpDataSourceFactory(userAgent,
//                null /* listener */,
//                DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
//                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS,
//                true /* allowCrossProtocolRedirects */);
//        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(MainActivity.this, Util.getUserAgent(MainActivity.this, getApplicationName(MainActivity.this)));
        DefaultHttpDataSourceFactory factory = new DefaultHttpDataSourceFactory(getApplicationName(PlayVideoActivity.this));
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(uri, factory, extractorsFactory, null, null);
        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);
        simpleExoPlayer.prepare(mediaSource);

        simpleExoPlayer.setPlayWhenReady(true);
        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                //check condition
                if (playbackState == Player.STATE_BUFFERING) {
                    //when buffering
                    //shoew progress bar
                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    //When readey
                    //Hide progress bar
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });
        bfullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == true) {
                    bfullscreen.setImageResource(R.drawable.ic_fullscreen);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    flag = false;
                } else {
                    bfullscreen.setImageResource(R.drawable.ic_fullscreen_exit);
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    flag = true;
                }
            }
        });
        b_backvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tTenVideo.setText(baiHat.getTenbaihat());
        bLoveVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bLoveVideo.setImageResource(R.drawable.icon_love_true);
                Dataservice dataservice = APIService.getService();
                Call<String> callback = dataservice.UpdateLuotThich("1", baiHat.getIdbaihat());
                callback.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String kq = response.body();
                        if (kq.equals("ok")) {
                            ArrayList<BaiHat> arr=read();
                            boolean kt=true;
                            for(int i=0;i<arr.size();i++){
                                BaiHat a=arr.get(i);
                                if(a.getIdbaihat().trim().equals(baiHat.getIdbaihat().trim())){
                                    delete(a.getIdbaihat().trim());
                                    insert(baiHat);
                                    kt=false;
                                }

                            }
                            if(kt==true){
                                insert(baiHat);
                            }
                            Toast.makeText(PlayVideoActivity.this, "Đã thích", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(PlayVideoActivity.this, "Bị lỗi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
                bLoveVideo.setEnabled(false);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //stop video when ready
        simpleExoPlayer.setPlayWhenReady(false);
        //get playback state
        simpleExoPlayer.getPlaybackState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //play video when redy
        simpleExoPlayer.setPlayWhenReady(true);

        //get playback state
        simpleExoPlayer.getPlaybackState();

    }

    public static String getApplicationName(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }


    private void GetDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("videos")) {
                baiHat = intent.getParcelableExtra("videos");

            }
        }

    }

    private void init() {

        playerView = (PlayerView) findViewById(R.id.player_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        bfullscreen = (ImageView) findViewById(R.id.fullscreen);
        b_backvideo = (ImageView) findViewById(R.id.im_backvideo);
        bLoveVideo = (ImageView) findViewById(R.id.imLoveVideo);
        tTenVideo = (TextView) findViewById(R.id.tTenVideo);

    }
    private void insert(BaiHat baiHat){

        ContentValues contentValues=new ContentValues();
        contentValues.put("IDBaiHat",baiHat.getIdbaihat());
        contentValues.put("tenBaiHat",baiHat.getTenbaihat());
        contentValues.put("hinhBaiHat",baiHat.getHinhbaihat());
        contentValues.put("caSi",baiHat.getCasi());
        contentValues.put("linkBaiHat",baiHat.getLinkbaihat());
        contentValues.put("luotThich",baiHat.getLuotthich());
        SQLiteDatabase sqLiteDatabase= Database.initDatabase(PlayVideoActivity.this,"luudanhsachnhacyeuthich1.db");
        sqLiteDatabase.insert("BaiHat",null,contentValues);
    }
    private   ArrayList<BaiHat> read(){
        SQLiteDatabase database= Database.initDatabase(PlayVideoActivity.this,"luudanhsachnhacyeuthich1.db");
        Cursor cursor= database.rawQuery("Select * from BaiHat",null);
        ArrayList<BaiHat> arr=new ArrayList<>();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            String idbh=cursor.getString(0);
            String tenbh=cursor.getString(1);
            String hinhbh=cursor.getString(2);
            String casi=cursor.getString(3);
            String link=cursor.getString(4);
            String luotthich=cursor.getString(5);
            arr.add(new BaiHat(idbh,tenbh,hinhbh,casi,link,luotthich));
        }
        return arr;
    }
    private void delete(String id){
        SQLiteDatabase database=Database.initDatabase(PlayVideoActivity.this,"luudanhsachnhacyeuthich1.db");
        database.delete("BaiHat","IDBaiHat=?",new String[] {id+""});
    }

}