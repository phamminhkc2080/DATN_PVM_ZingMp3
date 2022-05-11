package com.example.listtenmusic.Model;

//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class BaiHat implements Parcelable {
//
//@SerializedName("IDBaiHat")
//@Expose
//private String iDBaiHat;
//@SerializedName("TenBaiHat")
//@Expose
//private String tenBaiHat;
//@SerializedName("HinhBaiHat")
//@Expose
//private String hinhBaiHat;
//@SerializedName("CaSi")
//@Expose
//private String caSi;
//@SerializedName("LinkBaiHat")
//@Expose
//private String linkBaiHat;
//@SerializedName("LuotThich")
//@Expose
//private String luotThich;
//
//    protected BaiHat(Parcel in) {
//        iDBaiHat = in.readString();
//        tenBaiHat = in.readString();
//        hinhBaiHat = in.readString();
//        caSi = in.readString();
//        linkBaiHat = in.readString();
//        luotThich = in.readString();
//    }
//    public BaiHat(String tenBaiHat, String caSi, String linkBaiHat) {
//        this.tenBaiHat = tenBaiHat;
//        this.caSi = caSi;
//        this.linkBaiHat = linkBaiHat;
//    }
//
//    public BaiHat(String iDBaiHat, String tenBaiHat, String hinhBaiHat, String caSi, String linkBaiHat, String luotThich) {
//        this.iDBaiHat = iDBaiHat;
//        this.tenBaiHat = tenBaiHat;
//        this.hinhBaiHat = hinhBaiHat;
//        this.caSi = caSi;
//        this.linkBaiHat = linkBaiHat;
//        this.luotThich = luotThich;
//    }
//
//    public static final Creator<BaiHat> CREATOR = new Creator<BaiHat>() {
//        @Override
//        public BaiHat createFromParcel(Parcel in) {
//            return new BaiHat(in);
//        }
//
//        @Override
//        public BaiHat[] newArray(int size) {
//            return new BaiHat[size];
//        }
//    };
//
//    public BaiHat() {
//
//    }
//
//    public String getIDBaiHat() {
//return iDBaiHat;
//}
//
//public void setIDBaiHat(String iDBaiHat) {
//this.iDBaiHat = iDBaiHat;
//}
//
//public String getTenBaiHat() {
//return tenBaiHat;
//}
//
//public void setTenBaiHat(String tenBaiHat) {
//this.tenBaiHat = tenBaiHat;
//}
//
//public String getHinhBaiHat() {
//return hinhBaiHat;
//}
//
//public void setHinhBaiHat(String hinhBaiHat) {
//this.hinhBaiHat = hinhBaiHat;
//}
//
//public String getCaSi() {
//return caSi;
//}
//
//public void setCaSi(String caSi) {
//this.caSi = caSi;
//}
//
//public String getLinkBaiHat() {
//return linkBaiHat;
//}
//
//public void setLinkBaiHat(String linkBaiHat) {
//this.linkBaiHat = linkBaiHat;
//}
//
//public String getLuotThich() {
//return luotThich;
//}
//
//public void setLuotThich(String luotThich) {
//this.luotThich = luotThich;
//}
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(iDBaiHat);
//        dest.writeString(tenBaiHat);
//        dest.writeString(hinhBaiHat);
//        dest.writeString(caSi);
//        dest.writeString(linkBaiHat);
//        dest.writeString(luotThich);
//    }
//}

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.lang.annotation.Annotation;

public class BaiHat implements Parcelable {

    @SerializedName("Idbaihat")
    @Expose
    private String idbaihat;
    @SerializedName("Tenbaihat")
    @Expose
    private String tenbaihat;
    @SerializedName("Hinhbaihat")
    @Expose
    private String hinhbaihat;
    @SerializedName("Casi")
    @Expose
    private String casi;
    @SerializedName("Linkbaihat")
    @Expose
    private String linkbaihat;
    @SerializedName("Luotthich")
    @Expose
    private String luotthich;

    protected BaiHat(Parcel in) {
        idbaihat = in.readString();
        tenbaihat = in.readString();
        hinhbaihat = in.readString();
        casi = in.readString();
        linkbaihat = in.readString();
        luotthich = in.readString();
    }

    public static final Creator<BaiHat> CREATOR = new Creator<BaiHat>() {
        @Override
        public BaiHat createFromParcel(Parcel in) {
            return new BaiHat(in);
        }

        @Override
        public BaiHat[] newArray(int size) {
            return new BaiHat[size];
        }
    };

    public BaiHat(String tenbaihat, String casi, String linkbaihat) {
        this.tenbaihat = tenbaihat;
        this.casi = casi;
        this.linkbaihat = linkbaihat;
    }

    public String getIdbaihat() {
        return idbaihat;
    }

    public void setIdbaihat(String idbaihat) {
        this.idbaihat = idbaihat;
    }

    public String getTenbaihat() {
        return tenbaihat;
    }

    public void setTenbaihat(String tenbaihat) {
        this.tenbaihat = tenbaihat;
    }

    public String getHinhbaihat() {
        return hinhbaihat;
    }

    public void setHinhbaihat(String hinhbaihat) {
        this.hinhbaihat = hinhbaihat;
    }

    public String getCasi() {
        return casi;
    }

    public void setCasi(String casi) {
        this.casi = casi;
    }

    public String getLinkbaihat() {
        return linkbaihat;
    }

    public void setLinkbaihat(String linkbaihat) {
        this.linkbaihat = linkbaihat;
    }

    public String getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(String luotthich) {
        this.luotthich = luotthich;
    }

    public BaiHat(String idbaihat, String tenbaihat, String hinhbaihat, String casi, String linkbaihat, String luotthich) {
        this.idbaihat = idbaihat;
        this.tenbaihat = tenbaihat;
        this.hinhbaihat = hinhbaihat;
        this.casi = casi;
        this.linkbaihat = linkbaihat;
        this.luotthich = luotthich;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idbaihat);
        parcel.writeString(tenbaihat);
        parcel.writeString(hinhbaihat);
        parcel.writeString(casi);
        parcel.writeString(linkbaihat);
        parcel.writeString(luotthich);
    }
}