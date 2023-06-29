package com.example.mymusic2.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mymusic2.Adapter.SongAdapter;
import com.example.mymusic2.Adapter.CategoryHorizonalAdapter;
import com.example.mymusic2.R;
import com.example.mymusic2.api.ApiConfig;
import com.example.mymusic2.api.API;
import com.example.mymusic2.model.TheLoai;
import com.example.mymusic2.model.TheLoais;
import com.example.mymusic2.model.response.ResponseObject;
import com.example.mymusic2.model.response.SongResponse;
import com.example.mymusic2.sharedpreferences.SharedPreferencesManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private RecyclerView rev_theloais;
    private RecyclerView rev_theloai;
    private SongAdapter songAdapter;
    private CategoryHorizonalAdapter theLoaiHorizonalAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNewSong();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rev_theloais = view.findViewById(R.id.rev_theloais);
        theLoaiHorizonalAdapter = new CategoryHorizonalAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rev_theloais.setLayoutManager(linearLayoutManager);
        theLoaiHorizonalAdapter.setData(getContext(),getList());
        rev_theloais.setAdapter(theLoaiHorizonalAdapter);

        rev_theloai =view.findViewById(R.id.rev_theloai);
        songAdapter = new SongAdapter();
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rev_theloai.setLayoutManager(linearLayoutManager1);
        rev_theloai.setAdapter(songAdapter);

    }
    private List<TheLoais> getList() {
        List<TheLoais> list = new ArrayList<>();
        List<TheLoai> lst = new ArrayList<>();
        lst.add(new TheLoai(R.drawable.i,"Edm"));
        lst.add(new TheLoai(R.drawable.i1,"Pop"));
        lst.add(new TheLoai(R.drawable.i2,"Rock"));
        lst.add(new TheLoai(R.drawable.i3,"Folk"));
        lst.add(new TheLoai(R.drawable.i4,"Jazz"));
        lst.add(new TheLoai(R.drawable.i4,"Blue"));
        list.add(new TheLoais("Thể loại",lst));
        return list;
    }
    private void loadNewSong(){
        API api = ApiConfig.getClient(SharedPreferencesManager.getBaseURL(),SharedPreferencesManager.getAccessToken()).create(API.class);
        api.findNewSong("").enqueue(new Callback<ResponseObject<SongResponse>>() {
            @Override
            public void onResponse(Call<ResponseObject<SongResponse>> call, Response<ResponseObject<SongResponse>> response) {
                if(response.isSuccessful()){
                    songAdapter.setData(getContext(), response.body().getContent());
                    System.out.println("success");
                }
            }

            @Override
            public void onFailure(Call<ResponseObject<SongResponse>> call, Throwable t) {

            }
        });
    }
}