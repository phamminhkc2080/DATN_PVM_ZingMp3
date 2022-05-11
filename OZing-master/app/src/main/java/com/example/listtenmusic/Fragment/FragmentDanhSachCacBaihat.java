package com.example.listtenmusic.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.Adapter.PlayNhacAdapter;
import com.example.listtenmusic.R;

public class FragmentDanhSachCacBaihat extends Fragment {
    View view;
    RecyclerView recyclerViewPlaynhac;
    PlayNhacAdapter playNhacAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_danhsachcacbaihat,container,false);
        recyclerViewPlaynhac=view.findViewById(R.id.recylerviewPlaybaihat);
        if (PlayNhacActivity.mangbaihat.size()>0) {
            playNhacAdapter = new PlayNhacAdapter(getActivity(), PlayNhacActivity.mangbaihat);
        recyclerViewPlaynhac.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewPlaynhac.setAdapter(playNhacAdapter);
        }
        return view;
    }
}
