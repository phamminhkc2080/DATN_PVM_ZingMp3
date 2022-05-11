package com.example.listtenmusic.Activity;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listtenmusic.Adapter.ViewPagerPlayNhac;
import com.example.listtenmusic.CreateNotification;
import com.example.listtenmusic.Fragment.FragmentDanhSachCacBaihat;
import com.example.listtenmusic.Fragment.FragmentDiaNhac;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.LayDulieutuPlayNhac;
import com.example.listtenmusic.NotificationReceiver;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.example.listtenmusic.Service.HenGioService;
import com.example.listtenmusic.Service.OnClearFromRecentService;
import com.example.listtenmusic.Service.PlayNhacService;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.listtenmusic.R.*;
import static com.example.listtenmusic.R.drawable.pause_playnhac;

public class PlayNhacActivity extends AppCompatActivity {
    BroadcastReceiver receiver;
    private static final String CHANNEL_ID = "Music_CHANNEL";
    ImageButton bYeuthich, bDownload;
    Toolbar toolbarplaynhac;
    public static TextView tTimesong, tTimetong;
    public static SeekBar seekBartime;
    public static ImageButton bPlay, bShuffle, bSkiptostart, bNext, bRepeat;
    ViewPager viewPagerPlaynhac;
    public static ArrayList<BaiHat> mangbaihat = new ArrayList<>();
    public static ViewPagerPlayNhac adapterPlaynhac;
    FragmentDiaNhac fragmentDiaNhac;
    FragmentDanhSachCacBaihat fragmentDanhSachCacBaihat;
    //    public static MediaPlayer mediaPlayer;
    public static int pos = 0;
    public static boolean repeat = false;
    public static boolean checkrandom = false;
    boolean next = false;
    public static String TenBaiHat, LinkHinhAnh;
    private boolean iboundservice = false;
    PlayNhacService playNhacService;
    ServiceConnection serviceConnection;
    private DownloadManager downloadManager;
    HenGioService henGioService;
    NotificationManager notificationManager ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_play_nhac);
        //Tranhs phat sinh khi su dung mang, Kiem tra tin hieu mang
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
//        if(iboundservice==true){
//        Log.d("BBB", "onCreate: ");
//            unbindService(serviceConnection);
//            iboundservice=false;
//        }

        GetDataFromIntent();

        init();
        NhanDataSauClickMiniPlay();
        eventsClick();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
//        {
//            createChannel();
//            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
//            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
//        }
//        createChannel();
//        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(pos),
//                drawable.ic_pause_black, pos, mangbaihat.size()-1);

//        registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
//        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
//        showNotificationPlay();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mangbaihat.size() > 0) {
                    TenBaiHat = mangbaihat.get(pos).getTenbaihat();
                    LinkHinhAnh = mangbaihat.get(pos).getHinhbaihat();
                    fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhbaihat());
                    getSupportActionBar().setTitle(mangbaihat.get(pos).getTenbaihat());
                }
                handler.postDelayed(this, 500);
            }
        }, 500);
//        Intent it=getIntent();
//        Toast.makeText(this, ""+it.getStringExtra("actionname"), Toast.LENGTH_SHORT).show();


    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,"ListtenMusic", NotificationManager.IMPORTANCE_DEFAULT);
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
    private void DeleteChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
             NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,"ListtenMusic", NotificationManager.IMPORTANCE_LOW);
            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.cancelAll();
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            Intent it=getIntent();
//            if(it.getAction()!=null && it.getAction().equals("TRACKS_TRACKS")){
            String action = intent.getExtras().getString("Action_Name");
            switch (action) {
                case CreateNotification.ACTION_PREVIUOS:
                    //phải gọi onclick trc vì sau khi gọi lại thì pos ms thay đổi
                    bSkiptostart.callOnClick();
                    Log.d("BBB", "onReceive:Pla ");
                    CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(pos),
                            drawable.ic_pause_black, pos, mangbaihat.size() - 1);

                    break;
                case CreateNotification.ACTION_PLAY: {
                    Log.d("BBB", "onReceive:Pla ");
                    if (PlayNhacService.mediaPlayer.isPlaying()) {
                        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(pos),
                                drawable.ic_play_black, pos, mangbaihat.size() - 1);
                        bPlay.callOnClick();
                    } else {
                        CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(pos),
                                drawable.ic_pause_black, pos, mangbaihat.size() - 1);
                        bPlay.callOnClick();
                    }
                    break;
                }
                case CreateNotification.ACTION_NEXT:
                    bNext.callOnClick();
                    CreateNotification.createNotification(PlayNhacActivity.this, mangbaihat.get(pos),
                            drawable.ic_pause_black, pos, mangbaihat.size() - 1);

                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dongho, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case id.menudongho: {
                final Dialog dialog = new Dialog(PlayNhacActivity.this);
                dialog.setContentView(R.layout.hengio);
                final TextView tTG = (TextView) dialog.findViewById(R.id.tThoiGianHen);
                final Switch aSwitch = (Switch) dialog.findViewById(R.id.switchHenGio);
                ImageView b15p = (ImageView) dialog.findViewById(R.id.imhengio15p);
                final ImageView b30p = (ImageView) dialog.findViewById(R.id.imhengio30p);
                ImageView b60p = (ImageView) dialog.findViewById(R.id.imhengio60p);
                ImageView b120p = (ImageView) dialog.findViewById(R.id.imhengio120p);
                final EditText edThoiGianHen = (EditText) dialog.findViewById(R.id.edNhapsophut);
                if (HenGioService.time <= 0) {
                    aSwitch.setChecked(false);
                    aSwitch.setEnabled(false);
                } else {
                    HenGioService.clickswitch=false;
                    aSwitch.setChecked(true);
                    aSwitch.setEnabled(true);
                }

                final Intent intent = new Intent(PlayNhacActivity.this, HenGioService.class);
                serviceConnection = new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        HenGioService.BoundExample boundExample = (HenGioService.BoundExample) service;
                        henGioService = boundExample.getService();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tTG.setText(getDurationString(henGioService.getTime()));
                                handler.postDelayed(this, 500);
                            }
                        }, 500);

                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {

                    }
                };
                bindService(intent, serviceConnection, BIND_AUTO_CREATE);
                aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == false) {
                            HenGioService.clickswitch=true;
                            HenGioService.time = 0;
                            dialog.cancel();
                        }
                    }
                });
                b15p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int t = 15;
                        Intent intent1 = new Intent(PlayNhacActivity.this, HenGioService.class);

                        serviceConnection = new ServiceConnection() {
                            @Override
                            public void onServiceConnected(ComponentName name, IBinder service) {
                                HenGioService.BoundExample boundExample = (HenGioService.BoundExample) service;
                                henGioService = boundExample.getService();
//                                CountDownTimer countDownTimer=new CountDownTimer(15*60*1000,1000) {
//                                    public void onTick(long millisUntilFinished) {
////                                        edThoiGianHen.setText("seconds remaining: " + getDurationString1(millisUntilFinished/1000));
//                                        //here you can have your logic to set text to edittext
//                                    }
//                                    public void onFinish() {
//                                        edThoiGianHen.setText("done!");
//                                    }
//
//                                }.start();
                                henGioService.setTime(t);

                                dialog.cancel();
                            }

                            @Override
                            public void onServiceDisconnected(ComponentName name) {

                            }
                        };
                        bindService(intent1, serviceConnection, BIND_AUTO_CREATE);

                    }
                });

                b30p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int t = 30;
                        Intent intent1 = new Intent(PlayNhacActivity.this, HenGioService.class);

                        serviceConnection = new ServiceConnection() {
                            @Override
                            public void onServiceConnected(ComponentName name, IBinder service) {
                                HenGioService.BoundExample boundExample = (HenGioService.BoundExample) service;
                                henGioService = boundExample.getService();
                                henGioService.setTime(t);

                                dialog.cancel();
                            }

                            @Override
                            public void onServiceDisconnected(ComponentName name) {

                            }
                        };
                        bindService(intent1, serviceConnection, BIND_AUTO_CREATE);
                    }
                });
                b60p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int t = 60;
                        Intent intent1 = new Intent(PlayNhacActivity.this, HenGioService.class);

                        serviceConnection = new ServiceConnection() {
                            @Override
                            public void onServiceConnected(ComponentName name, IBinder service) {
                                HenGioService.BoundExample boundExample = (HenGioService.BoundExample) service;
                                henGioService = boundExample.getService();
                                henGioService.setTime(t);

                                dialog.cancel();
                            }

                            @Override
                            public void onServiceDisconnected(ComponentName name) {

                            }
                        };
                        bindService(intent1, serviceConnection, BIND_AUTO_CREATE);

                    }
                });
                b120p.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final int t = 120;
                        Intent intent1 = new Intent(PlayNhacActivity.this, HenGioService.class);

                        serviceConnection = new ServiceConnection() {
                            @Override
                            public void onServiceConnected(ComponentName name, IBinder service) {
                                HenGioService.BoundExample boundExample = (HenGioService.BoundExample) service;
                                henGioService = boundExample.getService();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                                henGioService.setTime(t);

                                dialog.cancel();
                            }

                            @Override
                            public void onServiceDisconnected(ComponentName name) {

                            }
                        };
                        bindService(intent1, serviceConnection, BIND_AUTO_CREATE);

                    }
                });
                edThoiGianHen.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            // Perform action on key press
                            Intent intent1 = new Intent(PlayNhacActivity.this, HenGioService.class);
                            serviceConnection = new ServiceConnection() {
                                @Override
                                public void onServiceConnected(ComponentName name, IBinder service) {
                                    HenGioService.BoundExample boundExample = (HenGioService.BoundExample) service;
                                    henGioService = boundExample.getService();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                                    henGioService.setTime(Integer.parseInt(edThoiGianHen.getText().toString().trim()));

                                    dialog.cancel();
                                }

                                @Override
                                public void onServiceDisconnected(ComponentName name) {

                                }
                            };
                            bindService(intent1, serviceConnection, BIND_AUTO_CREATE);
                            return true;
                        }
                        return false;
                    }
                });
                dialog.show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void ConnectService(@Nullable final ArrayList<BaiHat> arrbaihat, @Nullable final Integer position) {
//        playNhacService.setMediaPlayer("https://quyetdaik922.000webhostapp.com/MP3/Ai%20Mang%20Co%20Don%20Di%20-%20K-ICM_%20APJ.mp3");
        Intent intent = new Intent(PlayNhacActivity.this, PlayNhacService.class);
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
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);

    }

    private void eventsClick() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapterPlaynhac.getItem(1) != null) {
                    if (mangbaihat.size() > 0) {
                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhbaihat());
                        handler.removeCallbacks(this);
                    } else {
                        handler.postDelayed(this, 300);
                    }
                }
            }
        }, 500);
        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PlayNhacService.mediaPlayer.isPlaying()) {
                    PlayNhacService.mediaPlayer.pause();
                    bPlay.setImageResource(drawable.play_playnhac);
                    CreateNotification.createNotification(getApplicationContext(), PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                            R.drawable.ic_play_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);
                } else {
                    PlayNhacService.mediaPlayer.start();
                    bPlay.setImageResource(R.drawable.pause_playnhac);
                    CreateNotification.createNotification(getApplicationContext(), PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                            R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);
                }
            }
        });
        bRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (repeat == false) {
                    if (checkrandom == true) {
                        checkrandom = false;
                        bRepeat.setImageResource(drawable.repeat_true_playnhac);
                        bShuffle.setImageResource(drawable.shuffle_playnhac);

                    }
                    bRepeat.setImageResource(drawable.repeat_true_playnhac);
                    repeat = true;
                } else {
                    bRepeat.setImageResource(drawable.repeat_playnhac);
                    repeat = false;
                }
            }
        });
        bShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkrandom == false) {
                    if (repeat == true) {
                        repeat = false;
                        bShuffle.setImageResource(drawable.shuffle_true_playnhac);
                        bRepeat.setImageResource(drawable.repeat_playnhac);

                    }
                    bShuffle.setImageResource(drawable.shuffle_true_playnhac);
                    checkrandom = true;
                } else {
                    bShuffle.setImageResource(drawable.shuffle_playnhac);
                    checkrandom = false;
                }
            }
        });
        seekBartime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                PlayNhacService.mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
        bNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                            PlayNhacService.mediaPlayer.stop();
//                            PlayNhacService.mediaPlayer.release();
//                            PlayNhacService.mediaPlayer = null;
//                    }
                    if (pos < (mangbaihat.size())) {
                        bPlay.setImageResource(pause_playnhac);
                        pos++;
                        if (repeat == true) {
                            if (pos == 0) {
                                pos = mangbaihat.size();
                            }
                            pos -= 1;

                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == pos) {
                                pos = index - 1;
                            }
                            pos = index;
                        }
                        if (pos > (mangbaihat.size() - 1)) {
                            pos = 0;

                        }
//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                        ConnectService(mangbaihat, pos);
                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenbaihat());
                        UpdateTime();
                        TenBaiHat = mangbaihat.get(pos).getTenbaihat();
                        LinkHinhAnh = mangbaihat.get(pos).getHinhbaihat();
                        CreateNotification.createNotification(getApplicationContext(), PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                                R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);
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
        bSkiptostart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.size() > 0) {
//                    if (PlayNhacService.mediaPlayer.isPlaying() || PlayNhacService.mediaPlayer != null) {
//                        PlayNhacService.mediaPlayer.stop();
//                        PlayNhacService.mediaPlayer.release();
//                        PlayNhacService.mediaPlayer = null;
//                    }
                    if (pos < (mangbaihat.size())) {
                        bPlay.setImageResource(pause_playnhac);
                        pos--;
                        if (pos < 0) {
                            pos = mangbaihat.size() - 1;
                        }
                        if (repeat == true) {
                            pos += 1;

                        }
                        if (checkrandom == true) {
                            Random random = new Random();
                            int index = random.nextInt(mangbaihat.size());
                            if (index == pos) {
                                pos = index - 1;
                            }
                            pos = index;
                        }

//                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
                        ConnectService(mangbaihat, pos);
                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhbaihat());
                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenbaihat());
                        UpdateTime();
                        TenBaiHat = mangbaihat.get(pos).getTenbaihat();
                        LinkHinhAnh = mangbaihat.get(pos).getHinhbaihat();
                        CreateNotification.createNotification(getApplicationContext(), PlayNhacActivity.mangbaihat.get(PlayNhacActivity.pos),
                                R.drawable.ic_pause_black, PlayNhacActivity.pos, PlayNhacActivity.mangbaihat.size() - 1);
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
    }

    private void GetDataFromIntent() {
        Intent intent = getIntent();
        mangbaihat.clear();
        if (intent != null) {
            if (intent.hasExtra("cakhuc")) {
                BaiHat baiHat = intent.getParcelableExtra("cakhuc");
                mangbaihat.add(baiHat);
            }
            if (intent.hasExtra("cacbaihat")) {

                //Help
                ArrayList<? extends BaiHat> baiHatArrayList = intent.getParcelableArrayListExtra("cacbaihat");
                mangbaihat = (ArrayList<BaiHat>) baiHatArrayList;
            }

        }

    }

    private void init() {
        bYeuthich = (ImageButton) findViewById(id.imyeuthich);
        bDownload = (ImageButton) findViewById(id.imDownload);
        toolbarplaynhac = findViewById(id.toolbarplaynhac);
        tTimesong = findViewById(id.tTimeSong);
        tTimetong = findViewById(id.tTongThoiGian);
        seekBartime = findViewById(id.seebarSong);
        bShuffle = findViewById(id.imshuffle);
        bSkiptostart = findViewById(id.imSkiptostart);
        bPlay = findViewById(id.implay);
        bNext = findViewById(id.imNext);
        bRepeat = findViewById(id.imRepeat);
        viewPagerPlaynhac = findViewById(id.viewpagerPlaynhac);
        setSupportActionBar(toolbarplaynhac);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbarplaynhac.setTitleTextColor(Color.WHITE);
        fragmentDiaNhac = new FragmentDiaNhac();
        fragmentDanhSachCacBaihat = new FragmentDanhSachCacBaihat();
        adapterPlaynhac = new ViewPagerPlayNhac(getSupportFragmentManager());
        adapterPlaynhac.AddFragment(fragmentDanhSachCacBaihat);
        adapterPlaynhac.AddFragment(fragmentDiaNhac);
        viewPagerPlaynhac.setAdapter(adapterPlaynhac);
        fragmentDiaNhac = (FragmentDiaNhac) adapterPlaynhac.getItem(1);
        if (mangbaihat.size() > 0) {
//            if (mediaPlayer != null) {
//                mediaPlayer.stop();
//                mediaPlayer.reset();
//            }
            pos = 0;
            getSupportActionBar().setTitle(mangbaihat.get(0).getTenbaihat());
//                new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
            ConnectService(mangbaihat, 0);
            bPlay.setImageResource(R.drawable.pause_playnhac);
            TenBaiHat = mangbaihat.get(0).getTenbaihat();
            LinkHinhAnh = mangbaihat.get(0).getHinhbaihat();

        }
        bYeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.get(pos).getIdbaihat() != null) {
                    bYeuthich.setImageResource(R.drawable.icon_love_true);
                    Dataservice dataservice = APIService.getService();
                    Call<String> callback = dataservice.UpdateLuotThich("1", mangbaihat.get(pos).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String kq = response.body();
                            if (kq.equals("ok")) {
                                Toast.makeText(PlayNhacActivity.this, "Đã thích", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(PlayNhacActivity.this, "Bị lỗi", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    bYeuthich.setEnabled(false);
                }
            }
        });

        bDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mangbaihat.get(pos).getIdbaihat() != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PlayNhacActivity.this);
                    builder.setTitle("Tải xuống");
                    builder.setMessage("Tải xuống:" + mangbaihat.get(pos).getTenbaihat());
                    builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                            Uri uri = Uri.parse("" + mangbaihat.get(pos).getLinkbaihat());
                            DownloadManager.Request request = new DownloadManager.Request(uri);
                            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC, mangbaihat.get(pos).getTenbaihat() + ".mp3");
                            Long reference = downloadManager.enqueue(request);
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }


//    class PlayMP3 extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            return strings[0];
//        }
//
//        @Override
//        protected void onPostExecute(String baihat) {
//            super.onPostExecute(baihat);
//            try {
//                if (mediaPlayer == null) {
//                    mediaPlayer = new MediaPlayer();
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                        @Override
//                        public void onCompletion(MediaPlayer mp) {
//                            mediaPlayer.stop();
//                            mediaPlayer.reset();
//                        }
//                    });
//                }
//                if (mediaPlayer.isPlaying()) {
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                }
//                if (!baihat.equals("")) {
//                    mediaPlayer=new MediaPlayer();
//                    mediaPlayer.setDataSource(baihat);
//                    mediaPlayer.prepare();
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            mediaPlayer.start();
////            Log.d("CCC", "init: "+mediaPlayer.getDuration());
//            TimeSong();
//            UpdateTime();
//        }
//    }

    //    private void TimeSong() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
//        tTimetong.setText(simpleDateFormat.format(PlayNhacService.mediaPlayer.getDuration()));
//        seekBartime.setMax(PlayNhacService.mediaPlayer.getDuration());
//    }
    private String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + " : " + twoDigitString(minutes) + " : " + twoDigitString(seconds);
    }

    private String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }

    public void UpdateTime() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (PlayNhacService.mediaPlayer != null) {
//                    Log.d("BBB", "run: "+PlayNhacService.mediaPlayer.getCurrentPosition());
                    seekBartime.setProgress(PlayNhacService.mediaPlayer.getCurrentPosition());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    tTimesong.setText(simpleDateFormat.format(PlayNhacService.mediaPlayer.getCurrentPosition()));
//                    if (PlayNhacService.isNext == true) {
//                        next = true;
//                        try {
//                            Thread.sleep(5000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
                    handler.postDelayed(this, 250);
                }
            }
        }, 250);
//        final Handler handler1 = new Handler();
//        handler1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                if (next == true) {
//                    if (pos < (mangbaihat.size())) {
//                        bPlay.setImageResource(pause_playnhac);
//                        pos++;
//                        if (repeat == true) {
//                            if (pos == 0) {
//                                pos = mangbaihat.size();
//                            }
//                            pos -= 1;
//
//                        }
//                        if (checkrandom == true) {
//                            Random random = new Random();
//                            int index = random.nextInt(mangbaihat.size());
//                            if (index == pos) {
//                                pos = index - 1;
//                            }
//                            pos = index;
//                        }
//                        if (pos > (mangbaihat.size() - 1)) {
//                            pos = 0;
//
//                        }
////                        new PlayMP3().execute(mangbaihat.get(pos).getLinkBaiHat());
//                        ConnectService(mangbaihat, pos);
////
////                        Log.d("CCC", "run: " + mangbaihat.get(pos).getLinkBaiHat());
////                        fragmentDiaNhac.PlayNhac(mangbaihat.get(pos).getHinhBaiHat());
////                        getSupportActionBar().setTitle(mangbaihat.get(pos).getTenBaiHat());
//                        UpdateTime();
//
//                        TenBaiHat = mangbaihat.get(pos).getTenBaiHat();
//                        LinkHinhAnh = mangbaihat.get(pos).getHinhBaiHat();
//                    }
//
//                    bSkiptostart.setClickable(false);
//                    bNext.setClickable(false);
//                    Handler handler1 = new Handler();
//                    handler1.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            bSkiptostart.setClickable(true);
//                            bNext.setClickable(true);
//                        }
//                    }, 5000);
//                    next = false;
//                    handler1.removeCallbacks(this);
//                } else {
//                    handler1.postDelayed(this, 1000);
//                }
//            }
//        }, 1000);


    }

    public void NhanDataSauClickMiniPlay() {

        Intent intent = getIntent();
        if (intent.hasExtra("miniplay")) {
            mangbaihat.clear();
            LayDulieutuPlayNhac layDulieutuPlayNhac = (LayDulieutuPlayNhac) intent.getParcelableExtra("miniplay");
//            Log.d("BBB", "GetDataFromIntent: "+layDulieutuPlayNhac.getMangbaihat().get(layDulieutuPlayNhac.getPos()).getTenBaiHat());
////           mangbaihat.clear();
            mangbaihat = layDulieutuPlayNhac.getMangbaihat();
            pos = layDulieutuPlayNhac.getPos();
            getSupportActionBar().setTitle(mangbaihat.get(pos).getTenbaihat());

            repeat = layDulieutuPlayNhac.isRepeat();
            checkrandom = layDulieutuPlayNhac.isCheckrandom();
            if (repeat == true) {

                bRepeat.setImageResource(drawable.repeat_true_playnhac);
            }
            if ((checkrandom == true)) {
                bShuffle.setImageResource(drawable.shuffle_true_playnhac);
            }

            seekBartime.setProgress(PlayNhacService.mediaPlayer.getCurrentPosition());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
            PlayNhacActivity.tTimetong.setText(simpleDateFormat.format(PlayNhacService.mediaPlayer.getDuration()));
            PlayNhacActivity.seekBartime.setMax(PlayNhacService.mediaPlayer.getDuration());
            if (PlayNhacService.mediaPlayer.isPlaying()) {
                bPlay.setImageResource(pause_playnhac);
            } else {
                bPlay.setImageResource(drawable.play_playnhac);
            }
//            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
//            mediaPlayer=Layout_main.mediaPlayerLU;
//            seekBartime.setProgress(Layout_main.du);
//            Log.d("BBB", "NhanDataSauClickMiniPlay: ");
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private PendingIntent onButtonNotificationClick(@IdRes int id) {
        Intent intent = new Intent(PlayNhacActivity.this, NotificationReceiver.class);
        intent.putExtra("EXTRA_BUTTON_CLICKED", id);
        return PendingIntent.getBroadcast(this, id, intent, 0);
    }


    public void showNotificationPlay() {
        RemoteViews notificationLayout =
                new RemoteViews(getPackageName(), layout.custom_thongbao);
        notificationLayout.setOnClickPendingIntent(R.id.im_pre_tb,
                onButtonNotificationClick(R.id.im_pre_tb));
        notificationLayout.setOnClickPendingIntent(R.id.im_play_tb,
                onButtonNotificationClick(R.id.im_play_tb));
        notificationLayout.setOnClickPendingIntent(R.id.im_next_tb,
                onButtonNotificationClick(R.id.im_next_tb));
        notificationLayout.setOnClickPendingIntent(R.id.im_close_tb,
                onButtonNotificationClick(R.id.im_close_tb));
        notificationLayout.setTextViewText(R.id.tTenCasiThongBao,mangbaihat.get(pos).getCasi());
        notificationLayout.setTextViewText(R.id.tTenBaiHatThongBao,mangbaihat.get(pos).getTenbaihat());
//        Picasso.with(PlayNhacActivity.this).load(mangbaihat.get(pos).getHinhBaiHat()).into();
        Notification
                notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(drawable.ic_library_music)
                .setCustomContentView(notificationLayout)
                .build();

        notificationManager =
                (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(mangbaihat.get(pos).getIdbaihat()), notification);

    }

    public void showNotificationPause() {
        RemoteViews notificationLayout =
                new RemoteViews(getPackageName(), layout.custom_thongbao_pause);
        notificationLayout.setOnClickPendingIntent(R.id.im_pre_tb,
                onButtonNotificationClick(R.id.im_pre_tb));
        notificationLayout.setOnClickPendingIntent(R.id.im_play_tb,
                onButtonNotificationClick(R.id.im_play_tb));
        notificationLayout.setOnClickPendingIntent(R.id.im_next_tb,
                onButtonNotificationClick(R.id.im_next_tb));
        notificationLayout.setOnClickPendingIntent(R.id.im_close_tb,
                onButtonNotificationClick(R.id.im_close_tb));
        notificationLayout.setTextViewText(R.id.tTenCasiThongBao,mangbaihat.get(pos).getCasi());
        notificationLayout.setTextViewText(R.id.tTenBaiHatThongBao,mangbaihat.get(pos).getTenbaihat());
        Notification
                notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(drawable.ic_library_music)
                .setCustomContentView(notificationLayout)
                .build();
        notificationManager =
                (android.app.NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Integer.parseInt(mangbaihat.get(pos).getIdbaihat()), notification);

    }

    private BroadcastReceiver breceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            int id = intent.getIntExtra("EXTRA_BUTTON_CLICKED", -1);
            int action = intent.getExtras().getInt("EXTRA_BUTTON_CLICKED", -1);
            switch (action) {
                case R.id.im_pre_tb: {
                    Toast.makeText(context, "pre", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.im_play_tb: {
                    Log.d("BBB", "onReceive: Play");
                    if (PlayNhacService.mediaPlayer.isPlaying() == true) {
//                        Log.d("BBB", "onReceive: Pause");
                        showNotificationPause();
                        PlayNhacService.mediaPlayer.pause();
                    } else {
//                        Log.d("BBB", "onReceive: Play");
                        showNotificationPlay();
                        PlayNhacService.mediaPlayer.start();
                    }
                    break;
                }
                case R.id.im_next_tb: {
                    Toast.makeText(context, "next", Toast.LENGTH_SHORT).show();
                    break;
                }
                case R.id.im_close_tb: {
                    Toast.makeText(context, "close", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();
//        DeleteChannel();
    }
}