package com.example.user.offplayer;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private final static int LOADER_ID = 0x001;

    private RecyclerView mRecyclerView;
    private AudioAdapter mAdapter;

    /*
    private MediaPlayer mediaPlayer;
    */

    private ImageView mImgAlbumArt;
    private ImageView appImage;

    private TextView mTxtTitle;
    private ImageButton mBtnPlayPause;


    // 음악 재생 바
    private SeekBar seekbar;
    private Handler myHandler = new Handler();
    boolean isPlaying = false;

    /*
    private Runnable mRunnable;
    private Handler mHandler;
    */

    // UI 가져오기
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI();
        }
    };


    /*
    class MyThread extends Thread {
        @Override
        public void run() { // 쓰레드가 시작되면 콜백되는 메서드
            // 씨크바 막대기 조금씩 움직이기 (노래 끝날 때까지 반복)
            while(AudioApplication.getInstance().getServiceInterface().isPlaying()) {
                seekbar.setProgress(mediaPlayer.getCurrentPosition());
            }
        }
    }
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // 안드로이드 저장소 사용 - Marshmallow 이상 권한 체크
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1000);
            } else {
                // READ_EXTERNAL_STORAGE 권한
                getAudioListFromMediaDatabase();
            }
        }
        // 권한 체크X
        else {
            getAudioListFromMediaDatabase();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mAdapter = new AudioAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);


        mImgAlbumArt = (ImageView) findViewById(R.id.img_albumart);
        appImage = (ImageView) findViewById(R.id.appImage);

        mTxtTitle = (TextView) findViewById(R.id.txt_title);
        mBtnPlayPause = (ImageButton) findViewById(R.id.btn_play_pause);
        findViewById(R.id.lin_miniplayer).setOnClickListener(this);
        findViewById(R.id.btn_rewind).setOnClickListener(this);
        mBtnPlayPause.setOnClickListener(this);
        findViewById(R.id.btn_forward).setOnClickListener(this);

        registerBroadcast();

        updateUI();

        /*
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {
                isPlaying= true;
                int ttt = seekBar.getProgress(); // 사용자가 움직여놓은 위치
                mediaPlayer.seekTo(ttt);
                mediaPlayer.start();
                new MyThread().start();
            }
            public void onStartTrackingTouch(SeekBar seekBar) {
                isPlaying = false;
                mediaPlayer.pause();
            }
            public void onProgressChanged(SeekBar seekBar,int progress,boolean fromUser) {
                if (seekBar.getMax()==progress) {
                    isPlaying = false;
                    mediaPlayer.stop();
                }
            }
        });
        */


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        // 네비게이션
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void registerBroadcast() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AudioService.BroadcastActions.PREPARED);
        filter.addAction(AudioService.BroadcastActions.PLAY_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver, filter);
    }

    public void unregisterBroadcast(){
        unregisterReceiver(mBroadcastReceiver);
    }

    private void updateUI() {
        if (AudioApplication.getInstance().getServiceInterface().isPlaying()) {
            mBtnPlayPause.setImageResource(R.drawable.pause);
        } else {
            mBtnPlayPause.setImageResource(R.drawable.play);
        }
        AudioAdapter.AudioItem audioItem = AudioApplication.getInstance().getServiceInterface().getAudioItem();
        if (audioItem != null) {
            Uri albumArtUri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), audioItem.mAlbumId);
            Picasso.with(getApplicationContext()).load(albumArtUri).error(R.drawable.intro).into(mImgAlbumArt);
            Picasso.with(getApplicationContext()).load(albumArtUri).error(R.drawable.intro).into(appImage);
            mTxtTitle.setText(audioItem.mTitle);

            //imageView1.setImageURI(mImgAlbumArt);
        } else {
            mImgAlbumArt.setImageResource(R.drawable.intro);
            mTxtTitle.setText("재생중인 음악이 없습니다.");
        }
    }

    // Android Media Database - Query
    private void getAudioListFromMediaDatabase() {
        getSupportLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            // Database 조회
            @Override
            public Loader<Cursor> onCreateLoader(int id, Bundle args) {
                Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                String[] projection = new String[]{
                        MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.DATA
                };
                // 조건문
                String selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
                //
                String sortOrder = MediaStore.Audio.Media.TITLE + " COLLATE LOCALIZED ASC";
                return new CursorLoader(getApplicationContext(), uri, projection, selection, null, sortOrder);
            }

            // 조회 리턴
            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor data) { mAdapter.swapCursor(data); }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {
                mAdapter.swapCursor(null);
            }
        });
    }

    // 안드로이드 저장소 사용
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // READ_EXTERNAL_STORAGE 에 대한 권한 획득.
            getAudioListFromMediaDatabase();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lin_miniplayer:
                // 플레이어 화면 이동
                break;
            case R.id.btn_rewind:
                // 이전 곡 이동
                AudioApplication.getInstance().getServiceInterface().rewind();
                break;
            case R.id.btn_play_pause:
                // 재생 일시정지
                AudioApplication.getInstance().getServiceInterface().togglePlay();
                break;
            case R.id.btn_forward:
                // 다음 곡 이동
                AudioApplication.getInstance().getServiceInterface().forward();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterBroadcast();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
