package com.example.xieqe.test001.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xieqe.test001.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xieqe on 2017/11/27.
 */

public class HeaderView extends FrameLayout {
    @Bind(R.id.edit_text)
    TextView editText;
    @Bind(R.id.search_mention)
    LinearLayout searchMention;
    @Bind(R.id.speaker)
    ImageView speaker;

    public HeaderView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.header, this, true);
        setLayoutParams(new RecyclerView.LayoutParams(LayoutParams.MATCH_PARENT,(int)getResources().getDimension(R.dimen.xxhdpi_60)));
        ButterKnife.bind(this);
    }

}
