package com.example.listtenmusic.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.listtenmusic.Adapter.QuangCaoAdapter;
import com.example.listtenmusic.Model.QuangCao;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.getIntent;

public class FragmentQuangCao extends Fragment {
    View view;
    ViewPager  viewPager;
    CircleIndicator circleIndicator;
    QuangCaoAdapter quangCaoAdapter;
    Runnable runnable;
    Handler handler;
    int currentItem=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_quangcao,container,false);
        init();
//        Bundle bundle=getArguments();
//        if(bundle!=null){
//            ArrayList<QuangCao> quangCaos= (ArrayList<QuangCao>) bundle.getSerializable("DataQuangCao");
//            Toast.makeText(getActivity(),"a:"+quangCaos.get(0).getTenBaiHat(),Toast.LENGTH_LONG).show();
//        }else Toast.makeText(getActivity(),"k nhân dc dl",Toast.LENGTH_LONG).show();
        GetData();
        return view;
    }

    private void init() {
        viewPager=view.findViewById(R.id.viewpager);
         circleIndicator=view.findViewById(R.id.indicatordefaut);
    }

    private void GetData() {
        Dataservice dataservice= APIService.getService();
        Call<List<QuangCao>> callback=dataservice.GetDataQuangCao();
        callback.enqueue(new Callback<List<QuangCao>>() {
            @Override
            public void onResponse(Call<List<QuangCao>> call, Response<List<QuangCao>> response) {
                ArrayList<QuangCao> quangCaos= (ArrayList<QuangCao>) response.body();
                quangCaoAdapter=new QuangCaoAdapter(getActivity(),quangCaos);
                viewPager.setAdapter(quangCaoAdapter);
                circleIndicator.setViewPager(viewPager);
                handler=new Handler();
                runnable=new Runnable() {
                    @Override
                    public void run() {
                        currentItem=viewPager.getCurrentItem();
                        currentItem++;
                        if(viewPager.getAdapter()!=null){
                        if(currentItem>=viewPager.getAdapter().getCount()){
                            currentItem=0;
                        }}
                        viewPager.setCurrentItem(currentItem,true);
                        handler.postDelayed(runnable,4500);
                    }
                };
                handler.postDelayed(runnable,4500);
            }

            @Override
            public void onFailure(Call<List<QuangCao>> call, Throwable t) {
                Log.d("ccc", " loi:"+t);
            }
        });
    }
}
