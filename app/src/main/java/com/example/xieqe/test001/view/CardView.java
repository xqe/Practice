package com.example.xieqe.test001.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xieqe on 2017/9/19.
 */

public class CardView extends View {

    private float scale = 1.2f;

    public CardView(Context context) {
        super(context);
    }

    public CardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void onTranslate(int distance){

    }
}
