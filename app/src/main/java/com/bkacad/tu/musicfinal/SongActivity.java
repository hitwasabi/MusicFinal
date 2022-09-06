package com.bkacad.tu.musicfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SongActivity extends AppCompatActivity {

    MediaPlayer player;
    ImageView imgPlay,imgCD;
    SeekBar sbMusic;
    TextView tvTimeRun , tvTimeAll , tvLyrics , tvName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);
        imgPlay = findViewById(R.id.imgPlay);
        sbMusic = findViewById(R.id.sbMusic);
        tvTimeRun = findViewById(R.id.tvTimeRun);
        tvTimeAll = findViewById(R.id.tvTimeAll);
        tvLyrics = findViewById(R.id.tvLyrics);
        imgCD = findViewById(R.id.imgCD);
        tvName = findViewById(R.id.tvName);
        init();
        setUp();
        setClick();


        //Lấy dữ liệu từ Main gửi sang
        Intent j = getIntent();

        arrSong = new ArrayList<>();
        Song m1 = new Song();
        m1.name="Một Bước Yêu Vạn Dặm Đau";
        m1.img = R.drawable.mbyvdd;
        m1.muSic = R.raw.mbyvdd;
        arrSong.add(m1);

        Song m2 = new Song();
        m2.name="Trời Tối";
        m2.img = R.drawable.tt;
        m2.muSic = R.raw.tt;
        arrSong.add(m2);


    }

    private boolean nhacDangChay;
    private boolean daCoNhac;
    private int thoiGianNhacDung = 0;
    int timeMax;
    int spin = 0;
    ArrayList<Song> arrSong = new ArrayList<>();
    //vi tri bai nhac dang choi
    int indexMusic = 0;
    Song music;


    private void init(){
        arrSong = new ArrayList<>();
        Song m1 = new Song();
        m1.name="Một Bước Yêu Vạn Dặm Đau";
        m1.img = R.drawable.mbyvdd;
        m1.muSic = R.raw.mbyvdd;
        arrSong.add(m1);

        Song m2 = new Song();
        m2.name="Trời Tối";
        m2.img = R.drawable.tt;
        m2.muSic = R.raw.tt;
        arrSong.add(m2);

    }
    private void setUp(){
        choiBaiNhac(1);
        dungNhacLai();
        daCoNhac = false;
        nhacDangChay = false;
        new CountDownTimer(30000,50){

            @Override
            public void onTick(long l) {
                upDate();
            }

            @Override
            public void onFinish() {
                start();
            }
        }.start();
    }
    private void setClick(){
        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (daCoNhac == false){
                    batDauChayNhac(R.raw.mbyvdd);
                }else{
                    if(nhacDangChay == true){
                        dungNhacLai();
                    }else{
                        chayTiepNhac();
                    }
                }
            }
        });
        sbMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if(daCoNhac == false || nhacDangChay == false){
                    return;
                }
                thoiGianNhacDung = seekBar.getProgress();
                player.pause();
                player.seekTo(thoiGianNhacDung);
                player.start();
            }
        });
    }

    private void batDauChayNhac(int bannhac){
        if(player == null){
            player=MediaPlayer.create(this,bannhac);

        }else{
            player.stop();
            player.release();
            player=MediaPlayer.create(this,bannhac);
        }
        player.start();
        //doi image play thanh pause
        imgPlay.setImageResource(R.drawable.p);


        daCoNhac = true;
        nhacDangChay = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            sbMusic.setMin(0);
        }
        timeMax = player.getDuration();
        sbMusic.setMax(timeMax);
        tvTimeAll.setText(militoString(timeMax));
        spin = 0;
    }

    private void dungNhacLai(){
        nhacDangChay = false;
        imgPlay.setImageResource(R.drawable.play);
        player.pause();
        thoiGianNhacDung = player.getCurrentPosition();

    }

    private void chayTiepNhac(){
        nhacDangChay = true;
        imgPlay.setImageResource(R.drawable.p);
        player.seekTo(thoiGianNhacDung);
        player.start();
    }

    private void upDate(){
        if(daCoNhac == false || nhacDangChay == false){
            return;
        }
        sbMusic.setProgress(player.getCurrentPosition());
        tvTimeRun.setText(militoString(player.getCurrentPosition()));
        spin++;
        if(spin == 360){
            spin = 0;
        }
        imgCD.setRotation(spin);
    }
    private String militoString(int t){
        //t = giây, p = phút , s = giây dư (61s = 1:01s)
        t = t /1000;
        int p = t / 60;
        int s = t % 60;
        return checkNum10(p)+":"+checkNum10(s);
    }
    private String checkNum10(int i){
        if(i<10){
            return "0"+i;
        }
        return ""+i;
    }

    private void choiBaiNhac(int i ){

        indexMusic = indexMusic + i;

        if(indexMusic == arrSong.size()){
            indexMusic = 0;
        }
        if(indexMusic == -1){
            indexMusic = arrSong.size() - 1;
        }
        music = arrSong.get(indexMusic);
        batDauChayNhac(music.muSic);
        tvName.setText(music.name);
        imgCD.setImageResource(music.img);
    }

    public void playBefore(View view) {
        choiBaiNhac(-1);
    }

    public void playAfter(View view) {
        choiBaiNhac(1);
    }
}