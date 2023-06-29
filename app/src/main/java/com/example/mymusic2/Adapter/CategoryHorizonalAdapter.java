package com.example.mymusic2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic2.Activity.ListSongPlayActivity;
import com.example.mymusic2.R;
import com.example.mymusic2.model.TheLoais;

import java.util.List;

public class CategoryHorizonalAdapter extends RecyclerView.Adapter<CategoryHorizonalAdapter.TheLoaisHolder>{
    private Context mcontext;
    private List<TheLoais> mTheLoais;

    private  View view;

    public void setData(Context mcontext,List<TheLoais> list){
        this.mTheLoais= list;
        this.mcontext = mcontext;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TheLoaisHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.the_loais_item,parent,false);
        return new TheLoaisHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaisHolder holder, int position) {
        TheLoais theLoais = mTheLoais.get(position);
        if(theLoais == null){
            return;
        }
        holder.name_theloai.setText(theLoais.getCategory());

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(mcontext,RecyclerView.HORIZONTAL,false);
        holder.rev_theloai.setLayoutManager(linearLayoutManager);

        CategoryAdapter categoryAdapter = new CategoryAdapter();
        categoryAdapter.setData(mcontext,theLoais.getTheLoais());
        holder.rev_theloai.setAdapter(categoryAdapter);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mcontext, ListSongPlayActivity.class);
                mcontext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mTheLoais != null){
            return mTheLoais.size();
        }
        return 0;
    }

    public class TheLoaisHolder extends RecyclerView.ViewHolder{
        private TextView name_theloai;
        private RecyclerView rev_theloai;
        public TheLoaisHolder(@NonNull View itemView) {
            super(itemView);
            initView();
        }

        private void initView() {
            name_theloai = itemView.findViewById(R.id.name_theloai);
            rev_theloai = itemView.findViewById(R.id.rev_theloai);
        }
    }
}
