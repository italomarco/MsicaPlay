package com.example.msicaplay;

import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.util.ArrayList;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MyvieHolder> {

    private Context mContext;
    private ArrayList <ArquivosMusicas> mFiles;


    MusicAdapter(Context mContext, ArrayList<ArquivosMusicas> mFiles)
    {
        this.mFiles = mFiles;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyvieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.itens_musica, parent, false);
        return new MyvieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  MusicAdapter.MyvieHolder holder, int position) {

        holder.file_name.setText(mFiles.get(position).getTitulo());
        byte [] image = getAlbumArt(mFiles.get(position).getCaminho());
        if (image != null)
        {
            Glide.with(mContext).asBitmap()
                    .load(image)
                    .into(holder.album_art);
        }
        else {
            Glide.with(mContext)
                    .load(R.drawable.music)
                    .into(holder.album_art);

        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,PlayerActivity.class);
                intent.putExtra("position",position);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFiles.size();
    }

    public class MyvieHolder extends RecyclerView.ViewHolder
    {
        TextView file_name;
        ImageView album_art;
        public MyvieHolder(@NonNull View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.music_file_name);
            album_art = itemView.findViewById(R.id.music_img);
        }
    }

   private byte[] getAlbumArt (String uri)
   {
       MediaMetadataRetriever retriever = new MediaMetadataRetriever();
       retriever.setDataSource(uri);
       byte[] art = retriever.getEmbeddedPicture();
       retriever.release();
       return art;
   }
}
