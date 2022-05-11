package com.example.listtenmusic.Service;

import com.example.listtenmusic.Model.Album;
import com.example.listtenmusic.Model.BaiHat;
import com.example.listtenmusic.Model.ChuDe;
import com.example.listtenmusic.Model.ChuDeVaTheLoai;
import com.example.listtenmusic.Model.PlayList;
import com.example.listtenmusic.Model.QuangCao;
import com.example.listtenmusic.Model.TheLoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Dataservice {
    @GET("songbaner.php")
    Call<List<QuangCao>>GetDataQuangCao();
    @GET("playlistforcusrandom.php")
    Call<List<PlayList>>GetPlayListCurrentDay();
    @GET("chudevatheloai.php")
    Call<ChuDeVaTheLoai> GetDataChuDeVaTheLoai();
    @GET("albumhot.php")
    Call<List<Album>> GetDataAlbum();
    @GET("baihatluotthich.php")
    Call<List<BaiHat>> GetDataBaiHatHot();
    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihatquangcao(@Field("idquangcao") String idquangcao);

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihatplaylist(@Field("idplaylist") String idplaylist);

    @GET("danhcacplaylist.php")
    Call<List<PlayList>>GetDanhsachcacplaylist();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihatTheloai(@Field("idtheloai") String idtheloai);

    @GET("tatcachude.php")
    Call<List<ChuDe>> GetDataALLChuDe();

    @FormUrlEncoded
    @POST("theloaitheochude.php")
    Call<List<TheLoai>> GetTheLoaiTheoChude(@Field("idchude") String idchude);

    @GET("tatcaalbum.php")
    Call<List<Album>> GetTatCaAlbum();

    @FormUrlEncoded
    @POST("danhsachbaihat.php")
    Call<List<BaiHat>> GetDanhsachbaihatAlbum(@Field("idalbum") String idalbum);

    @FormUrlEncoded
    @POST("updateluotthich.php")
    Call<String> UpdateLuotThich(@Field("luotthich") String luotthich,@Field("idbaihat") String idbaihatt);

    @FormUrlEncoded
    @POST("searchbaihat.php")
    Call<List<BaiHat>> GetSearchBaihat(@Field("tukhoa") String tukhoa);

    @GET("videos.php")
    Call<List<BaiHat>> GetVideos();
}
