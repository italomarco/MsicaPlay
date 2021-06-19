package com.example.msicaplay;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private  Thread playThread,prevThread,nextThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        getIntentMetodo();
        nome_musica.setText(listasons.get(position).getTitulo());
        nome_artista.setText(listasons.get(position).getArtista());
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

    @Override
    protected void onResume() {
        playThreadBtn();
        nextThreadBtn();
        prevThreadBtn();
        super.onResume();
    }

    private void prevThreadBtn() {

        prevThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prevBtnClicked();

                    }
                });
            }
        };
        prevThread.start();

    }

    private void prevBtnClicked() {

        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listasons.size() - 1) : (position -1));
            uri = Uri.parse(listasons.get(position).getCaminho());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            nome_musica.setText(listasons.get(position).getTitulo());
            nome_artista.setText(listasons.get(position).getArtista());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!= null){

                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPausebtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position - 1) < 0 ? (listasons.size() - 1) : (position -1));
            uri = Uri.parse(listasons.get(position).getCaminho());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            nome_musica.setText(listasons.get(position).getTitulo());
            nome_artista.setText(listasons.get(position).getArtista());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!= null){

                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPausebtn.setImageResource(R.drawable.ic_play);
        }



    }

    private void nextThreadBtn() {

        nextThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextBtnClicked();

                    }
                });
            }
        };
        nextThread.start();

    }

    private void nextBtnClicked() {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listasons.size());
            uri = Uri.parse(listasons.get(position).getCaminho());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            nome_musica.setText(listasons.get(position).getTitulo());
            nome_artista.setText(listasons.get(position).getArtista());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!= null){

                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPausebtn.setImageResource(R.drawable.ic_pause);
            mediaPlayer.start();
        }
        else
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            position = ((position + 1) % listasons.size());
            uri = Uri.parse(listasons.get(position).getCaminho());
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            nome_musica.setText(listasons.get(position).getTitulo());
            nome_artista.setText(listasons.get(position).getArtista());
            seekBar.setMax(mediaPlayer.getDuration() / 1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer!= null){

                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);

                    }
                    handler.postDelayed(this, 1000);
                }
            });
            playPausebtn.setImageResource(R.drawable.ic_play);
        }


    }

    private void playThreadBtn() {
        playThread = new Thread()
        {
            @Override
            public void run() {
                super.run();
                playPausebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       playPausebtnClicked();

                    }
                });
            }
        };
        playThread.start();
    }

    private void playPausebtnClicked() {
       if (mediaPlayer.isPlaying())
       {
           playPausebtn.setImageResource(R.drawable.ic_play);
           mediaPlayer.pause();
           seekBar.setMax(mediaPlayer.getDuration() / 1000);
           PlayerActivity.this.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   if (mediaPlayer!= null){

                       int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                       seekBar.setProgress(mCurrentPosition);

                   }
                   handler.postDelayed(this, 1000);
               }
           });


       }
       else
       {
           playPausebtn.setImageResource(R.drawable.ic_pause);
           mediaPlayer.start();
           seekBar.setMax(mediaPlayer.getDuration() / 1000);
           PlayerActivity.this.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   if (mediaPlayer!= null){

                       int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                       seekBar.setProgress(mCurrentPosition);

                   }
                   handler.postDelayed(this, 1000);
               }
           });
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
        metaData(uri);

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

    private void metaData (Uri uri)
    {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int duracaoTotal = Integer.parseInt(listasons.get(position).getDuracao()) / 1000;
        duracao_total.setText(formattedTime(duracaoTotal));
        byte [] art = retriever.getEmbeddedPicture();
        if (art != null)
        {
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(cover_art);

        }
        else
        {
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.music)
                    .into(cover_art);

        }
    }
}