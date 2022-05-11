package com.example.listtenmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.listtenmusic.Activity.DanhsachBaihatActivity;
import com.example.listtenmusic.Activity.DanhsachtatcachudeActivity;
import com.example.listtenmusic.Activity.DanhsachtheloaitheochudeActivity;
import com.example.listtenmusic.Adapter.PlaylistAdapter;
import com.example.listtenmusic.Model.ChuDe;
import com.example.listtenmusic.Model.ChuDeVaTheLoai;
import com.example.listtenmusic.Model.PlayList;
import com.example.listtenmusic.Model.TheLoai;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentChudeTheLoai  extends Fragment {
View view;
HorizontalScrollView horizontalScrollView;
TextView tXemThem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragmentchudetheloai,container,false);
        horizontalScrollView=view.findViewById(R.id.horizontalScrollview);
        tXemThem=view.findViewById(R.id.tXemthem);
        tXemThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DanhsachtatcachudeActivity.class);
                startActivity(intent);
            }
        });
        GetData();

        return view;
    }

    private void GetData() {
        Dataservice dataservice= APIService.getService();
        Call<ChuDeVaTheLoai> callback=dataservice.GetDataChuDeVaTheLoai();
        callback.enqueue(new Callback<ChuDeVaTheLoai>() {
            @Override
            public void onResponse(Call<ChuDeVaTheLoai> call, Response<ChuDeVaTheLoai> response) {
                ChuDeVaTheLoai chuDeVaTheLoai=response.body();
                final ArrayList<ChuDe> chuDeArrayList = new ArrayList<>();
                chuDeArrayList.addAll(chuDeVaTheLoai.getChuDe());

                final ArrayList<TheLoai> theloaiArrayList = new ArrayList<>();
                theloaiArrayList.addAll(chuDeVaTheLoai.getTheLoai());

                LinearLayout linearLayout=new LinearLayout(getActivity());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(580,250);
                layoutParams.setMargins(10,20,10,30);

                for (int i=0;i<(chuDeArrayList.size());i++){
                    CardView cardView=new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView=new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(chuDeArrayList.get(i).getTenChuDe()!=null){
                        Picasso.with(getActivity()).load(chuDeArrayList.get(i).getHinhChuDe()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalI = i;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(), DanhsachtheloaitheochudeActivity.class);
                            intent.putExtra("theloaitheochude",chuDeArrayList.get(finalI));
                            startActivity(intent);
                        }
                    });
                }
                for (int j=0;j<(theloaiArrayList.size());j++){
                    CardView cardView=new CardView(getActivity());
                    cardView.setRadius(10);
                    ImageView imageView=new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    if(theloaiArrayList.get(j).getTenTheLoai()!=null){
                        Picasso.with(getActivity()).load(theloaiArrayList.get(j).getHinhTheLoai()).into(imageView);
                    }
                    cardView.setLayoutParams(layoutParams);
                    cardView.addView(imageView);
                    linearLayout.addView(cardView);
                    final int finalJ = j;
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(getActivity(), DanhsachBaihatActivity.class);
                            intent.putExtra("idtheloai",theloaiArrayList.get(finalJ));

                            startActivity(intent);
                        }
                    });
                }
                horizontalScrollView.addView(linearLayout);
            }

            @Override
            public void onFailure(Call<ChuDeVaTheLoai> call, Throwable t) {
                Log.d("BBB","faill");
            }
        });
    }
}
