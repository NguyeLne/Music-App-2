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
import com.example.mymusic2.model.BaiHat;

import java.util.List;

public class TheLoaiMusicAdapter extends RecyclerView.Adapter<TheLoaiMusicAdapter.TheLoaiMusicHolder>{
    private List<BaiHat> baiHats;
    //
    private Context context;

    private View view;

    public void setData(Context context,List<BaiHat> list){
        this.baiHats= list;
        this.context=context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TheLoaiMusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_loai_item_ngang,parent,false);
        return new TheLoaiMusicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiMusicHolder holder, int position) {
        BaiHat baiHat = baiHats.get(position);
        if(baiHat == null){
            return;
        }
        holder.imgtheloai.setImageResource(baiHat.getMusicrsc());
        holder.texttheloai.setText(baiHat.getName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MusicPlayActivity.class);
                intent.putExtra("pos1", baiHat.getMusicrsc());
                intent.putExtra("pos",baiHat.getName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(baiHats != null){
            return baiHats.size();
        }
        return 0;
    }

    public class TheLoaiMusicHolder extends RecyclerView.ViewHolder{
        private ImageView imgtheloai;
        private TextView texttheloai;
        public TheLoaiMusicHolder(@NonNull View itemView) {
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
