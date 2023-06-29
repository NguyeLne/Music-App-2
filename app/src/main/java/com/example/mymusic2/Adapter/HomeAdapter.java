package com.example.mymusic2.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.mymusic2.Fragment.FavoriteFragment;
import com.example.mymusic2.Fragment.HomeFragment;
import com.example.mymusic2.Fragment.MenuFragment;

public class HomeAdapter extends FragmentStatePagerAdapter {
    public HomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
         switch (position){
             case 0: return new HomeFragment();
             case 1: return  new FavoriteFragment();
             case 2: return new MenuFragment();
             default: return new HomeFragment();
         }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
