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

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>{
    private List<SongResponse> songs;
//
    private Context context;

    private View view;


    public void setData(Context context,List<SongResponse> list){
        this.songs = list;
        this.context=context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_loai_item_ngang,parent,false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        SongResponse song = songs.get(position);
        if(song == null){
            return;
        }
        Picasso.get().load(song.getAvatarLink()).into(holder.imgtheloai);
        holder.texttheloai.setText(song.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicPlayActivity.class);
                intent.putExtra("pos1", song.getAvatarLink());
                intent.putExtra("pos",song.getName());
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

    public class FavoriteHolder extends RecyclerView.ViewHolder{
        private ImageView imgtheloai;
        private TextView texttheloai;
        public FavoriteHolder(@NonNull View itemView) {
            super(itemView);

            initView();
        }

        private void initView() {
            imgtheloai = itemView.findViewById(R.id.imgtheloai);
            texttheloai = itemView.findViewById(R.id.texttheloai);
        }

    }
    public interface ItemListener{
        void onItemClick(View view,int position);
    }
}
