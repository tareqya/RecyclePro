package com.example.recyclepro.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.recyclepro.home.EcoFragment;
import com.example.recyclepro.home.EffectsFragment;
import com.example.recyclepro.home.RecyclingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new RecyclingFragment();
            case 1: return new EffectsFragment();
            case 2: return new EcoFragment();
            default: return new RecyclingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
