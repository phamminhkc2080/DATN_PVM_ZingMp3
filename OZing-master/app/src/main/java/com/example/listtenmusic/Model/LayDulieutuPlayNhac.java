package com.example.listtenmusic.Model;

import android.media.MediaPlayer;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class LayDulieutuPlayNhac  implements Parcelable{
    ArrayList<BaiHat> mangbaihat;
    int pos;
    boolean repeat;
    boolean checkrandom;

    public LayDulieutuPlayNhac(ArrayList<BaiHat> mangbaihat, int pos, boolean repeat, boolean checkrandom) {
        this.mangbaihat = mangbaihat;
        this.pos = pos;
        this.repeat = repeat;
        this.checkrandom = checkrandom;
    }

    protected LayDulieutuPlayNhac(Parcel in) {
        mangbaihat = in.createTypedArrayList(BaiHat.CREATOR);
        pos = in.readInt();
        repeat = in.readByte() != 0;
        checkrandom = in.readByte() != 0;
    }

    public static final Creator<LayDulieutuPlayNhac> CREATOR = new Creator<LayDulieutuPlayNhac>() {
        @Override
        public LayDulieutuPlayNhac createFromParcel(Parcel in) {
            return new LayDulieutuPlayNhac(in);
        }

        @Override
        public LayDulieutuPlayNhac[] newArray(int size) {
            return new LayDulieutuPlayNhac[size];
        }
    };

    public ArrayList<BaiHat> getMangbaihat() {
        return mangbaihat;
    }

    public void setMangbaihat(ArrayList<BaiHat> mangbaihat) {
        this.mangbaihat = mangbaihat;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    public boolean isCheckrandom() {
        return checkrandom;
    }

    public void setCheckrandom(boolean checkrandom) {
        this.checkrandom = checkrandom;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(mangbaihat);
        dest.writeInt(pos);
        dest.writeByte((byte) (repeat ? 1 : 0));
        dest.writeByte((byte) (checkrandom ? 1 : 0));
    }
}
