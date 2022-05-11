package com.example.listtenmusic.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.listtenmusic.Adapter.MainViewPagerAdapter;
import com.example.listtenmusic.Model.QuangCao;
import com.example.listtenmusic.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class FragmengtOnline extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
ArrayList<QuangCao> quangCao=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.layout_onlinne,container,false);
        tabLayout=(TabLayout) view.findViewById(R.id.myTabLayout) ;
        viewPager=(ViewPager) view.findViewById(R.id.myViewPager);

        MainViewPagerAdapter mainViewPagerAdapter=new MainViewPagerAdapter(getFragmentManager());
        mainViewPagerAdapter.addFragment(new Fragment_trangchu(),"Trang chủ");
//        mainViewPagerAdapter.addFragment(new FragmentVideos(),"Videos");
        mainViewPagerAdapter.addFragment(new FragmengtBXH(),"BXH");
        mainViewPagerAdapter.addFragment(new FragmengtNews(),"News");
        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.home_grey);
//        tabLayout.getTabAt(1).setIcon(R.drawable.video_grey);
        tabLayout.getTabAt(1).setIcon(R.drawable.bxh_grey);
        tabLayout.getTabAt(2).setIcon(R.drawable.news_grey);
        return view;
    }

}
