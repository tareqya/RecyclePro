package com.example.recyclepro.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recyclepro.R;
import com.example.recyclepro.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment {

    private AppCompatActivity activity;
    private TabLayout fHome_TL;
    private ViewPager2 fHome_VP;
    private ViewPagerAdapter viewPagerAdapter;

    public HomeFragment(AppCompatActivity activity) {
        // Required empty public constructor
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        viewPagerAdapter = new ViewPagerAdapter(activity);

        fHome_VP.setAdapter(viewPagerAdapter);

        fHome_TL.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fHome_VP.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fHome_VP.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                fHome_TL.getTabAt(position).select();
            }
        });
    }

    private void findViews(View view) {
        fHome_TL = view.findViewById(R.id.fHome_TL);
        fHome_VP = view.findViewById(R.id.fHome_VP);
    }


}