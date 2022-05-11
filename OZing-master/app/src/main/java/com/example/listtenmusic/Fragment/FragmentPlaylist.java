package com.example.listtenmusic.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.listtenmusic.Activity.DanhSachCacPlayListActivity;
import com.example.listtenmusic.Activity.DanhsachBaihatActivity;
import com.example.listtenmusic.Adapter.PlaylistAdapter;
import com.example.listtenmusic.Model.PlayList;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPlaylist extends Fragment {
    View view;
    ListView lvPlaylist;
    TextView tTitlePlaylist,tXemThemPlaylist;
    PlaylistAdapter playlistAdapter;
    ArrayList<PlayList> arrayListPlaylist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_playlist,container,false);
        lvPlaylist=view.findViewById(R.id.lvPlaylist);
        tTitlePlaylist=view.findViewById(R.id.tTitlePlaylist);
        tXemThemPlaylist=view.findViewById(R.id.tMorePlaylist);
        GetData();
        tXemThemPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), DanhSachCacPlayListActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void GetData() {
        Dataservice dataservice= APIService.getService();
        Call<List<PlayList>>callback=dataservice.GetPlayListCurrentDay();
        callback.enqueue(new Callback<List<PlayList>>() {
            @Override
            public void onResponse(Call<List<PlayList>> call, Response<List<PlayList>> response) {
               arrayListPlaylist= (ArrayList<PlayList>) response.body();
               playlistAdapter=new PlaylistAdapter(getActivity(),android.R.layout.simple_list_item_1,arrayListPlaylist);
               lvPlaylist.setAdapter(playlistAdapter);
               setListViewHeightBasedOnChildren(lvPlaylist);
               lvPlaylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                   @Override
                   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                       Intent intent=new Intent(getActivity(), DanhsachBaihatActivity.class);
                       intent.putExtra("itemplaylist",arrayListPlaylist.get(position));
                       startActivity(intent);
                   }
               });
            }

            @Override
            public void onFailure(Call<List<PlayList>> call, Throwable t) {

            }
        });
    }
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);

            if(listItem != null){
                // This next line is needed before you call measure or else you won't get measured height at all. The listitem needs to be drawn first to know the height.
                listItem.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                totalHeight += listItem.getMeasuredHeight();

            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}
