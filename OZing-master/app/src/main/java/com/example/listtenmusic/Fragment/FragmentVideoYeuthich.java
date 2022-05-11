package com.example.listtenmusic.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Adapter.BaiHatHotAdapter;
import com.example.listtenmusic.Adapter.VideosAdapter;
import com.example.listtenmusic.Database;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;

import java.util.ArrayList;

public class FragmentVideoYeuthich extends Fragment {
    View view;
    String databasename="luudanhsachnhacyeuthich1.db";
    SQLiteDatabase database;
    ArrayList<BaiHat> arrmp4=new ArrayList<>();
    VideosAdapter videosAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_videoyeuthich,container,false);
        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recylerviewvideosyeuthich);
        read();
        ArrayList<BaiHat> tmp=new ArrayList<>();
        for (int i=arrmp4.size()-1;i>=0;i--){
            tmp.add(arrmp4.get(i));
        }

        videosAdapter=new VideosAdapter(getContext(),tmp);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videosAdapter);
        return view;
    }
    public  void read(){
        database= Database.initDatabase(getActivity(),databasename);
        Cursor cursor= database.rawQuery("Select * from BaiHat",null);
        arrmp4.clear();
        ArrayList<BaiHat> arr=new ArrayList<>();
        for(int i=0;i<cursor.getCount();i++){
            cursor.moveToPosition(i);
            String idbh=cursor.getString(0);
            String tenbh=cursor.getString(1);
            String hinhbh=cursor.getString(2);
            String casi=cursor.getString(3);
            String link=cursor.getString(4);
            String luotthich=cursor.getString(5);
            arr.add(new BaiHat(idbh,tenbh,hinhbh,casi,link,luotthich));
        }
        for(BaiHat a:arr){
            if (a.getLinkbaihat().trim().contains(".mp4")){
                arrmp4.add(a);
            }
        }
    }
}
