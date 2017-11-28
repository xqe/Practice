package com.example.xieqe.test001.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xieqe.test001.R;


/**
 * Created by xieqe on 2017/11/27.
 */

public class FooterView extends RelativeLayout {

    public FooterView(Context context,int total) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.footer,this,true);
        TextView textView = (TextView) findViewById(R.id.total);
        textView.setText(total + "个联系人");
    }

}
