package com.example.xieqe.test001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xieqe.test001.R;

/**
 * Created by xieqe on 2017/11/29.
 */

public class LetterDecoration extends RecyclerView.ItemDecoration {

    private Paint paint;
    private Paint textPaint;
    private int left,right;
    private int top,bottom;
    private int dividerHeight = 2;
    private int textHeight;
    private Rect tagRect;
    private String tag;

    public LetterDecoration(Context context){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(context.getResources().getColor(R.color.gray_bac));
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.xxhdpi_textsize_16));
        textPaint.setColor(Color.RED);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textHeight = (int) (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent);
        tagRect = new Rect();
    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        left = parent.getPaddingLeft();
        right = parent.getRight() - parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            bottom = parent.getChildAt(i).getBottom();
            top = bottom - dividerHeight;
            canvas.drawRect(left,top,right,bottom,paint);
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        int position = ((LinearLayoutManager)parent.getLayoutManager()).findFirstVisibleItemPosition();
        Log.i("===", "onDrawOver: " + position);
        //recyclerView.getChildAt(p)，获得的是当前界面第p个view的位置
        tag = (String) parent.getChildAt(0).getTag();

        if (tag != null){
            Log.i("===", "onDrawOver: " + tag + "--" + position);
            tagRect.set(0,0,right,textHeight);
            canvas.drawRect(tagRect,paint);
            float baseline = (tagRect.top + tagRect.bottom - textPaint.getFontMetrics().top - textPaint.getFontMetrics().bottom)/2;
            canvas.drawText(tag,textHeight,baseline,textPaint);
            tag = null;
        }
    }
}
