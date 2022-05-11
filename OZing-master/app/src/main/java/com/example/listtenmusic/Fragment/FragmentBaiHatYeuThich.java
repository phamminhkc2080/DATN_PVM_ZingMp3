package com.example.listtenmusic.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.DanhsachyeuthichActivity;
import com.example.listtenmusic.Adapter.BaiHatHotAdapter;
import com.example.listtenmusic.Database;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentBaiHatYeuThich extends Fragment {
    View view;
    String databasename="luudanhsachnhacyeuthich1.db";
    SQLiteDatabase database;
    ArrayList<BaiHat> arrmp3=new ArrayList<>();
    BaiHatHotAdapter baiHatHotAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_baihatyeuthich,container,false);
        RecyclerView lv=(RecyclerView) view.findViewById(R.id.lvbaihatyeuthich);
        read();
        ArrayList<BaiHat> tmp=new ArrayList<>();
        for (int i=arrmp3.size()-1;i>=0;i--){
            tmp.add(arrmp3.get(i));
        }

         baiHatHotAdapter=new BaiHatHotAdapter(getContext(),tmp);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        lv.setLayoutManager(linearLayoutManager);
        lv.setAdapter(baiHatHotAdapter);
        return view;
    }
    public  void read(){
        database= Database.initDatabase(getActivity(),databasename);
        Cursor cursor= database.rawQuery("Select * from BaiHat",null);
        arrmp3.clear();
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
            if (a.getLinkbaihat().trim().contains(".mp3")){
                arrmp3.add(a);
            }
        }
    }
}
