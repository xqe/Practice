package com.example.xieqe.test001.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xieqe.test001.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xieqe on 2017/11/27.
 */

public class PhoneBookItem extends ViewGroup {


    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.name)
    TextView name;

    public PhoneBookItem(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.person_item, this, true);
        ButterKnife.bind(this);
    }

    public void setImage(Drawable drawable){
        image.setBackground(drawable);
    }

    public void setName(String name){
        this.name.setText(name);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
    }


}
