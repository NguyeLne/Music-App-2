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

import com.example.mymusic2.Activity.ListSongPlayActivity;
import com.example.mymusic2.R;
import com.example.mymusic2.api.API;
import com.example.mymusic2.api.ApiConfig;
import com.example.mymusic2.model.response.PlaylistResponse;
import com.example.mymusic2.model.response.SongResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumHolder>{
    private List<PlaylistResponse> albums;
    //
    private ItemListener itemListener;
    private Context context;
    private View view;

    private API api;
    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    public void setData(Context context,List<PlaylistResponse> list){
        this.context = context;
        this.albums= list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist,parent,false);
        return new AlbumHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
        PlaylistResponse album = albums.get(position);
        if(album == null){
            return;
        }
        holder.imageView.setImageResource(R.drawable.i3);
        holder.textView.setText(album.getName());
        if(position == 0){
            holder.deleteView.setVisibility(View.GONE);
        }
        else {
            holder.deleteView.setOnClickListener(view -> {
                api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(), SharedPreferencesManager.getAccessToken()).create(API.class);
                api.deleteAlbum(album.getId()).enqueue(new Callback<PlaylistResponse>() {
                    @Override
                    public void onResponse(Call<PlaylistResponse> call, Response<PlaylistResponse> response) {
                        if (response.isSuccessful()) {
                            int removedPosition = holder.getAdapterPosition();
                            albums.remove(removedPosition);
                            notifyItemRemoved(removedPosition);
                            notifyItemRangeChanged(removedPosition, getItemCount());
                            SharedPreferencesManager.setAlbums(albums);
                        }
                    }

                    @Override
                    public void onFailure(Call<PlaylistResponse> call, Throwable t) {

                    }
                });
            });
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListSongPlayActivity.class);

                api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
                api.findSongInAlbum(album.getId()).enqueue(new Callback<List<SongResponse>>() {
                    @Override
                    public void onResponse(Call<List<SongResponse>> call, Response<List<SongResponse>> response) {
                        if(response.isSuccessful()){
                            intent.putExtra("pos",album.getName());
                            intent.putExtra("listSong", (Serializable) response.body());
                            context.startActivity(intent);
                        }

                    }

                    @Override
                    public void onFailure(Call<List<SongResponse>> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });

            }
        });

    }

    @Override
    public int getItemCount() {
        if(albums != null){
            return albums.size();
        }
        return 0;
    }

    public class AlbumHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView textView;

        private ImageView deleteView;
        public AlbumHolder(@NonNull View itemView) {
            super(itemView);

            initView();
        }

        private void initView() {
            imageView = itemView.findViewById(R.id.imgtheloai);
            textView = itemView.findViewById(R.id.texttheloai);
            deleteView = itemView.findViewById(R.id.delete_item);
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