package com.example.listtenmusic.Adapter;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listtenmusic.Activity.PlayNhacActivity;
import com.example.listtenmusic.Database;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.R;
import com.example.listtenmusic.Service.APIService;
import com.example.listtenmusic.Service.Dataservice;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchBaiHatAdapter extends RecyclerView.Adapter<SearchBaiHatAdapter.ViewHolder>{
    Context context;
    ArrayList<BaiHat> mangbaihat;

    public SearchBaiHatAdapter(Context context, ArrayList<BaiHat> mangbaihat) {
        this.context = context;
        this.mangbaihat = mangbaihat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.dong_searchbaihat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BaiHat baiHat=mangbaihat.get(position);
        holder.tTenBaihat.setText(baiHat.getTenbaihat());
        holder.tTenCasi.setText(baiHat.getCasi());
        Picasso.with(context).load(baiHat.getHinhbaihat()).into(holder.imbaihat);
    }

    @Override
    public int getItemCount() {
        return mangbaihat.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView tTenBaihat,tTenCasi;
        ImageView imbaihat,imluotthich;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tTenBaihat=itemView.findViewById(R.id.tSearchTenbaihat);
            tTenCasi=itemView.findViewById(R.id.tSearchTencasi);
            imbaihat=itemView.findViewById(R.id.imsearchbaihat);
            imluotthich=itemView.findViewById(R.id.imSearchLuotthich);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, PlayNhacActivity.class);
                    intent.putExtra("cakhuc",mangbaihat.get(getPosition()));
                    context.startActivity(intent);
                }
            });
            imluotthich.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imluotthich.setImageResource(R.drawable.icon_love_true);
                    Dataservice dataservice= APIService.getService();
                    Call<String> callback=dataservice.UpdateLuotThich("1",mangbaihat.get(getPosition()).getIdbaihat());
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            String kq=response.body();
                            if(kq.equals("ok")){
                                BaiHat baiHat=mangbaihat.get(getPosition());
                                ArrayList<BaiHat> arr=read();
                                boolean kt=true;
                                for(int i=0;i<arr.size();i++){
                                    BaiHat a=arr.get(i);
                                    if(a.getIdbaihat().trim().equals(baiHat.getIdbaihat().trim())){
                                        delete(a.getIdbaihat().trim());
                                        insert(baiHat);
                                        kt=false;
                                    }

                                }
                                if(kt==true){
                                    insert(baiHat);
                                }
                                Toast.makeText(context,"Đã thích",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(context,"Bị lỗi",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                    imluotthich.setEnabled(false);
                }
            });
        }
    }
    private void insert(BaiHat baiHat){

        ContentValues contentValues=new ContentValues();
        contentValues.put("IDBaiHat",baiHat.getIdbaihat());
        contentValues.put("tenBaiHat",baiHat.getTenbaihat());
        contentValues.put("hinhBaiHat",baiHat.getHinhbaihat());
        contentValues.put("caSi",baiHat.getCasi());
        contentValues.put("linkBaiHat",baiHat.getLinkbaihat());
        contentValues.put("luotThich",baiHat.getLuotthich());
        SQLiteDatabase sqLiteDatabase= Database.initDatabase((Activity) context,"luudanhsachnhacyeuthich1.db");
        sqLiteDatabase.insert("BaiHat",null,contentValues);
    }
    private   ArrayList<BaiHat> read(){
        SQLiteDatabase database= Database.initDatabase((Activity) context,"luudanhsachnhacyeuthich1.db");
        Cursor cursor= database.rawQuery("Select * from BaiHat",null);
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
        return arr;
    }
    private void delete(String id){
        SQLiteDatabase database=Database.initDatabase((Activity) context,"luudanhsachnhacyeuthich1.db");
        database.delete("BaiHat","IDBaiHat=?",new String[] {id+""});
    }
}
