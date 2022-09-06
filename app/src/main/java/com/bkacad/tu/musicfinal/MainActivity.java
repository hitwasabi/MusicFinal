package com.bkacad.tu.musicfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    protected ListView lvSong;
    protected List<Song> songList;
    protected SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvSong = findViewById(R.id.lvSong);
        songList = new ArrayList<>();
        songList.add(new Song("Một Bước Yêu Vạn Dặm Đau","Mr.Siro"));
        songList.add(new Song("Trời Tối","LK"));
        //Adapter
        songAdapter = new SongAdapter(this,songList);
        //Set Adapter cho listview
        lvSong.setAdapter(songAdapter);


        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent j = new Intent(MainActivity.this,SongActivity.class);
                j.putExtra("music",i);

                //Start Activity
                startActivity(j);


            }
        });
    }


}