package com.example.mymusic2.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mymusic2.Adapter.HomeAdapter;
import com.example.mymusic2.Adapter.CategoryHorizonalAdapter;
import com.example.mymusic2.R;
import com.example.mymusic2.model.response.SongResponse;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private BottomNavigationView bottomNavigation;
    private ViewPager viewPager;
    private RecyclerView rev_theloais;
    private CategoryHorizonalAdapter theLoaiHorizonalAdapter;
    private List<SongResponse> favoriteSongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();


//tạm
//        rev_theloais.findViewById(R.id.rev_theloais);
//        theLoaisAdapter = new TheLoaisAdapter(this);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
//        rev_theloais.setLayoutManager(linearLayoutManager);
//
//        theLoaisAdapter.setData(getListTheLoais());
//        rev_theloais.setAdapter(theLoaisAdapter);
    }
//tạm
//    private List<TheLoais> getListTheLoais() {
//        List<TheLoais> list = new ArrayList<>();
//
//        List<TheLoai> lst = new ArrayList<>();
//        lst.add(new TheLoai(R.drawable.i,"Nhạc"));
//        lst.add(new TheLoai(R.drawable.i1,"Nhạc"));
//        lst.add(new TheLoai(R.drawable.i2,"Nhạc"));
//        lst.add(new TheLoai(R.drawable.i3,"Nhạc"));
//        lst.add(new TheLoai(R.drawable.i4,"Nhạc"));
//
//        list.add(new TheLoais("TheLoais1",lst));
    //        return list;
    //    }


    private void homeAdapter() {
        HomeAdapter home = new HomeAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(home);
// Vuốt ngang view pager
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigation.getMenu().findItem(R.id.homepage).setChecked(true);
                        break;
                    case 1:
                        bottomNavigation.getMenu().findItem(R.id.farvorite).setChecked(true);
                        break;
                    case 2:
                        bottomNavigation.getMenu().findItem(R.id.library).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homepage: {
                        viewPager.setCurrentItem(0);
                        break;
                    }
                    case R.id.farvorite: {
                        viewPager.setCurrentItem(1);
                        break;
                    }
                    case R.id.library: {
                        viewPager.setCurrentItem(2);
                        break;
                    }
                }
                return true;
            }
        });
    }



    private void initView() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        viewPager = findViewById(R.id.viewPager);
        homeAdapter();
    }

    @Override
    public void onClick(View v) {

    }
}