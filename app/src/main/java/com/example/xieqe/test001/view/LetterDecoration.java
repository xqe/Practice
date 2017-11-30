package com.example.xieqe.test001.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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

    public LetterDecoration(Context context){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(context.getResources().getColor(R.color.gray_bac));
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(context.getResources().getDimension(R.dimen.xxhdpi_textsize_16));
        textPaint.setColor(context.getResources().getColor(R.color.colorTextNormal));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textHeight = (int) (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent);
    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        left = parent.getPaddingLeft();
        right = parent.getRight() - parent.getPaddingRight();
        for (int i = 0; i < parent.getChildCount(); i++) {
            bottom = parent.getChildAt(i).getBottom();
            top = bottom - dividerHeight;
            canvas.drawRect(left + 10,top,right - 10,bottom,paint);
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            bottom = parent.getChildAt(i).getBottom();
            top = bottom - textHeight;
            canvas.drawRect(left,top,right,bottom,paint);
            canvas.drawText("SS",left + textHeight,bottom - textHeight/3,textPaint);

            String tag = (String) parent.getChildAt(i).getTag();
        }
    }


}
