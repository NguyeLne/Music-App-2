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
import com.example.mymusic2.model.TheLoai;
import com.example.mymusic2.model.response.ResponseObject;
import com.example.mymusic2.model.response.SongResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.TheLoaiHolder>{
    private List<TheLoai> theLoais;
    private Context context;
    private View view;

    private API api;
    public void setData(Context context,List<TheLoai> list){
        this.context = context;
        this.theLoais= list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TheLoaiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_loai_item,parent,false);
        return new TheLoaiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiHolder holder, int position) {
        TheLoai theLoai = theLoais.get(position);
        if(theLoai == null){
            return;
        }
        holder.imgtheloai.setImageResource(theLoai.getResourceId());
        holder.texttheloai.setText(theLoai.getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListSongPlayActivity.class);

                api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL()).create(API.class);
                api.findSongByCategory(theLoai.getTitle()).enqueue(new Callback<ResponseObject<SongResponse>>() {
                    @Override
                    public void onResponse(Call<ResponseObject<SongResponse>> call, Response<ResponseObject<SongResponse>> response) {
                        if(response.isSuccessful()){
                            List<SongResponse> list = response.body().getContent();
                            intent.putExtra("listSong", (Serializable) list);
                            intent.putExtra("pos", theLoai.getTitle());
                            context.startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseObject<SongResponse>> call, Throwable t) {
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if(theLoais != null){
            return theLoais.size();
        }
        return 0;
    }

    public class TheLoaiHolder extends RecyclerView.ViewHolder {
        private ImageView imgtheloai;
        private TextView texttheloai;
        public TheLoaiHolder(@NonNull View itemView) {
            super(itemView);

            initView();
        }

        private void initView() {
            imgtheloai = itemView.findViewById(R.id.imgtheloai);
            texttheloai = itemView.findViewById(R.id.texttheloai);
        }
    }
}
