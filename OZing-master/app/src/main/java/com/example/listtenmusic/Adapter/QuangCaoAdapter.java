package com.example.listtenmusic.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.listtenmusic.Activity.DanhsachBaihatActivity;
import com.example.listtenmusic.Model.PlayList;
import com.example.listtenmusic.Model.QuangCao;
import com.example.listtenmusic.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class QuangCaoAdapter extends PagerAdapter {
    Context context;
    ArrayList<QuangCao> arrayListQuangCao;

    public QuangCaoAdapter(Context context, ArrayList<QuangCao> arrayListQuangCao) {
        this.context = context;
        this.arrayListQuangCao = arrayListQuangCao;
    }
    @Override
    public int getCount() {
        return arrayListQuangCao.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_quangcao,null);
        ImageView imbackgroudQuangcao=(ImageView) view.findViewById(R.id.imBackgroundQuangCao);
        ImageView imviewQuangCao=(ImageView) view.findViewById(R.id.imviewQuangCao);
        TextView tTitleQuangcao=(TextView)view.findViewById(R.id.txtTitleQuangCaobaihat);
        TextView tNoiDung=(TextView)view.findViewById(R.id.tNoiDung);
        Picasso.with(context).load(arrayListQuangCao.get(position).getHinhanh()).into(imbackgroudQuangcao);
        Picasso.with(context).load(arrayListQuangCao.get(position).getHinhBaiHat()).into(imviewQuangCao);
        tTitleQuangcao.setText(arrayListQuangCao.get(position).getTenBaiHat());
        tNoiDung.setText(arrayListQuangCao.get(position).getNoidung());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DanhsachBaihatActivity.class);
                intent.putExtra("QuangCao", (Serializable) arrayListQuangCao.get(position));
                context.startActivity(intent);
            }
        });

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
