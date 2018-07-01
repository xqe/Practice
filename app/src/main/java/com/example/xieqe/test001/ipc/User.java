package com.example.xieqe.test001.ipc;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.xieqe.test001.Bean.Bean;

/**
 * Created by xieqe on 2017/8/16.
 *
 */

public class User implements Parcelable {



    private int userId;
    private String userName;
    private boolean isMale;
    private Bean bean;//已实现Parcelable接口的可序列化对象

    @Override
    public int describeContents() {
        return 0;
    }

    //writeToParcel方法实现序列化功能
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeInt(isMale ? 1 : 0);
        dest.writeParcelable(bean,0);
    }

    //Creator实现反序列化功能
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    //从Parcel中读取数据
    private User(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
        bean = in.readParcelable(Bean.class.getClassLoader());
    }
}
