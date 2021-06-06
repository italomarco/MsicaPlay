package com.example.msicaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import static com.example.msicaplay.MainActivity.arquivosMusicas;

public class PlayerActivity extends AppCompatActivity {

    TextView nome_musica, nome_artista, duracao_tocando, duracao_total;
    ImageView cover_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
    FloatingActionButton playPausebtn;
    SeekBar seekBar;
    int position = 1;
    static ArrayList<ArquivosMusicas> listasons = new ArrayList<>();
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        getIntentMetodo();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaPlayer != null && b){

                    mediaPlayer.seekTo(i * 1000);

                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer!= null){

                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    duracao_tocando.setText(formattedTime(mCurrentPosition));
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private String formattedTime(int mCurrentPosition) {

        String totalout = "";
        String totalnew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalout = minutes + ":" + seconds;
        totalnew = minutes +":" + "0" + seconds;
        if (seconds.length() == 1){
            return totalnew;
        }
        else
        {
            return totalout;
        }

    }

    private void getIntentMetodo() {

        position = getIntent().getIntExtra("position",-1);
        listasons = arquivosMusicas;

        if (listasons != null){

            playPausebtn.setImageResource(R.drawable.ic_pause);
            uri = Uri.parse(listasons.get(position).getCaminho());


        }

        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();

        }
        else
        {
            mediaPlayer = MediaPlayer.create(getApplicationContext(),uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);

    }

    private void initViews() {
        nome_musica = findViewById(R.id.nome_musica);
        nome_artista = findViewById(R.id.nome_artista);
        duracao_tocando = findViewById(R.id.duracao_tocando);
        duracao_total =  findViewById(R.id.duracao_total);
        cover_art = findViewById(R.id.cover_art);
        nextBtn = findViewById(R.id.id_next);
        prevBtn = findViewById(R.id.id_prev);
        backBtn = findViewById(R.id.back_bpn);
        shuffleBtn = findViewById(R.id.id_shuffle);
        repeatBtn = findViewById(R.id.id_repeat);
        playPausebtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekBar);

    }
}