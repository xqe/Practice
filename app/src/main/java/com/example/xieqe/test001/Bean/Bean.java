package com.example.xieqe.test001.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by xieqe on 2016/12/9.
 */
public class Bean  implements Parcelable{

    private String name;
    private int id;
    private String address;
    public String testStr;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
        dest.writeString(address);
        dest.writeString(testStr);
    }

    protected Bean(Parcel in) {
        name = in.readString();
        id = in.readInt();
        address = in.readString();
        testStr = in.readString();
    }

    public static final Creator<Bean> CREATOR = new Creator<Bean>() {
        @Override
        public Bean createFromParcel(Parcel in) {
            return new Bean(in);
        }

        @Override
        public Bean[] newArray(int size) {
            return new Bean[size];
        }
    };
}
