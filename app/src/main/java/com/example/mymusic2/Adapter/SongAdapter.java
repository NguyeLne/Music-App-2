package com.example.mymusic2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic2.Activity.MusicPlayActivity;
import com.example.mymusic2.R;
import com.example.mymusic2.model.response.SongResponse;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.BaiHatHolder>{
    private List<SongResponse> songs;
    //
    private ItemListener itemListener;
    private Context context;
    private View view;
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setData(Context context,List<SongResponse> list){
        this.songs = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaiHatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_loai_item_ngang,parent,false);
        return new BaiHatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaiHatHolder holder, int position) {
        SongResponse song = songs.get(position);
        if(song == null){
            return;
        }
        Picasso.get().load(song.getAvatarLink()).into(holder.imageView);
        holder.textView.setText(song.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicPlayActivity.class);
                intent.putExtra("name", song.getName());
                intent.putExtra("musicAvatarUrl",song.getAvatarLink());
                intent.putExtra("musicUrl", song.getMusicLink());
                intent.putExtra("musicId", song.getId());
                intent.putExtra("listSong", (Serializable) songs);
                intent.putExtra("song", song);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(songs != null){
            return songs.size();
        }
        return 0;
    }

    public class BaiHatHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView textView;
        public BaiHatHolder(@NonNull View itemView) {
            super(itemView);

            initView();
        }

        private void initView() {
            imageView = itemView.findViewById(R.id.imgtheloai);
            textView = itemView.findViewById(R.id.texttheloai);
        }

        @Override
        public void onClick(View v) {
            if(itemListener !=null){
                itemListener.onItemClick(itemView,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view,int position);
    }
}
