package com.example.listtenmusic.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Adapter.SearchBaiHatAdapter;
import com.example.listtenmusic.Adapter.SearchBaiHatVideoAdapter;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentTimKiem extends Fragment {
    View view;
    Toolbar toolbar;
    RecyclerView recyclerViewSearch;
    ListView recyclerViewSearchVideo;
    TextView tKhongcodulieu;
    SearchBaiHatAdapter searchBaiHatAdapter;
    SearchBaiHatVideoAdapter searchBaiHatVideoAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_timkiem,container,false);
        toolbar=(Toolbar) view.findViewById(R.id.toolbarSearchBaihat);
        recyclerViewSearch=(RecyclerView) view.findViewById(R.id.recylerviewSearchBaihat);
        recyclerViewSearchVideo=(ListView) view.findViewById(R.id.recylerviewSearchBaihatVideo);
        tKhongcodulieu=(TextView) view.findViewById(R.id.tKhongcodulieu);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("");
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_view,menu);
        MenuItem menuItem=menu.findItem(R.id.menuSearch);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchBaiHat(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }
    private void SearchBaiHat(String tukhoa){
        Dataservice dataservice= APIService.getService();
        Call<List<BaiHat>> callback=dataservice.GetSearchBaihat(tukhoa);
        callback.enqueue(new Callback<List<BaiHat>>() {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response) {
                ArrayList<BaiHat> mangbaihat= (ArrayList<BaiHat>) response.body();
                ArrayList<BaiHat> mangmp3=new ArrayList<>();
                ArrayList<BaiHat> mangmp4=new ArrayList<>();
                if(mangbaihat.size()>0){
                    for (int i=0;i<mangbaihat.size();i++){
                        if(mangbaihat.get(i).getLinkbaihat().contains(".mp4")){
                            mangmp4.add(mangbaihat.get(i));
                        }
                        else {
                            mangmp3.add(mangbaihat.get(i));
                        }
                    }
                    if (mangmp3.size()>0) {
                        searchBaiHatAdapter = new SearchBaiHatAdapter(getActivity(), mangmp3);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerViewSearch.setLayoutManager(linearLayoutManager);
                        recyclerViewSearch.setAdapter(searchBaiHatAdapter);
                        recyclerViewSearch.setVisibility(View.VISIBLE);
                    }
                    if (mangmp4.size()>0){
                        searchBaiHatVideoAdapter=new SearchBaiHatVideoAdapter(getActivity(),mangmp4);
                        recyclerViewSearchVideo.setAdapter(searchBaiHatVideoAdapter);

                        recyclerViewSearchVideo.setVisibility(View.VISIBLE);
                        setListViewHeightBasedOnChildren(recyclerViewSearchVideo);
                    }
                    tKhongcodulieu.setVisibility(View.GONE);
                }
                else{
                    tKhongcodulieu.setVisibility(View.VISIBLE);
                    recyclerViewSearch.setVisibility(View.GONE);
                    recyclerViewSearchVideo.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t) {

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
