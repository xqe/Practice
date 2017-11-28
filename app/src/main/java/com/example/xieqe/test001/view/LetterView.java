package com.example.xieqe.test001.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.xieqe.test001.R;

/**
 * Created by xieqe on 2017/11/23.
 */

public class LetterView extends View {

    private final static String TAG = "LetterView";
    private float textSize = 10;
    private int textColor = Color.BLUE;
    private int selectedColor = Color.CYAN;
    private float scaleTime = 3f;
    private Paint textPaint;
    private Paint bigTextPaint;
    private Paint scaleTextPaint;
    private float touchY = -1;
    private String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private int w;
    private int h;
    private int itemH;
    private float textH;
    private int selectedIndex = -1;
    private int selectItemDistance = 250;
    private int scaleCount = 3;
    private SelectListener selectListener;
    private RectF backgroudRectF;
    private float drawLetterX;
    private float drawLetterY;
    private Path path;
    private ValueAnimator animator;
    private float ratio;


    public LetterView(Context context) {
        super(context);
        init(null);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public LetterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LetterView);
            textSize = typedArray.getDimension(R.styleable.LetterView_textSize,10);
            textColor = typedArray.getColor(R.styleable.LetterView_textColor, Color.GRAY);
            selectedColor = typedArray.getColor(R.styleable.LetterView_selectedColor,Color.CYAN);
            typedArray.recycle();
        }
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textH = textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent;

        bigTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigTextPaint.setTextSize(textSize * scaleTime);
        bigTextPaint.setColor(selectedColor);
        bigTextPaint.setTextAlign(Paint.Align.CENTER);

        scaleTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        scaleTextPaint.setTextSize(textSize * scaleTime);
        scaleTextPaint.setColor(textColor);
        scaleTextPaint.setTextAlign(Paint.Align.CENTER);

        backgroudRectF = new RectF();
        path = new Path();
        animator = new ValueAnimator();
    }

    public void setSelectListener(SelectListener selectListener){
        this.selectListener = selectListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > w - getPaddingRight() - textH) {
                    touchY = event.getY();
                    startAnimator(ratio,1);
                    return true;
                }
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                selectedIndex = -1;
                startAnimator(ratio,0);
                return event.getX() > w - getPaddingRight() - textH;

        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        w = getMeasuredWidth();
        h = getMeasuredHeight();
        itemH = h / letters.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBackground(canvas);
        drawLetters(canvas);
        drawWave(canvas);
    }

    private void drawBackground(Canvas canvas){
        //draw background
        backgroudRectF.left = w - getPaddingRight() - textSize - 2;
        backgroudRectF.right = w - getPaddingRight() + 2;
        backgroudRectF.top = getPaddingTop() - 2;
        backgroudRectF.bottom = h - getPaddingTop() + 2;

        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(getResources().getColor(R.color.ksw_md_solid_normal1));
        canvas.drawRoundRect(backgroudRectF,textSize,textSize,textPaint);
    }

    private void drawLetters(Canvas canvas){
        //find the selected one
        for (int i = 0; i < letters.length; i++) {
            if (touchY > itemH * i && touchY < itemH * (i + 1)) {
                selectedIndex = i;
            }
        }

        //draw letters
        for (int i = 0; i < letters.length; i++) {

            drawLetterY = itemH * i + getPaddingTop() + textH;

            if (i == selectedIndex)
            {
                drawLetterX = w - getPaddingRight() - textSize/2 - selectItemDistance;
                canvas.drawText(letters[i],drawLetterX,drawLetterY,bigTextPaint);
            }
            else if (selectedIndex > i && selectedIndex - i < scaleCount)
            {
                int offset = scaleCount - (selectedIndex - i);
                drawLetterX = w - getPaddingRight() - textSize/2 - selectItemDistance*offset/scaleCount;
                scaleTextPaint.setTextSize(textSize + (scaleTime - 1) * textSize * offset/scaleCount);
                canvas.drawText(letters[i],drawLetterX,drawLetterY,scaleTextPaint);
            }
            else if (selectedIndex < i && i - selectedIndex < scaleCount && selectedIndex != -1)
            {
                int offset = scaleCount - (i - selectedIndex);
                scaleTextPaint.setTextSize(textSize + (scaleTime - 1) * textSize * offset/scaleCount);
                drawLetterX = w - getPaddingRight() - textSize/2 - selectItemDistance*offset/scaleCount;
                canvas.drawText(letters[i],drawLetterX,drawLetterY,scaleTextPaint);
            }
            else
            {
                drawLetterX = w - getPaddingRight() - textSize/2;
                textPaint.setStyle(Paint.Style.FILL);
                textPaint.setColor(textColor);
                canvas.drawText(letters[i],drawLetterX,drawLetterY,textPaint);
            }
        }
    }

    private void drawWave(Canvas canvas){

        path.reset();
        path.moveTo(w,touchY - 100);
        path.quadTo(w - ratio * 200,touchY,w,touchY + 100);
        canvas.drawPath(path,bigTextPaint);
        Log.e(TAG, "drawWave: " + (w - ratio) );
    }

    private void startAnimator(float...value){
        animator.cancel();
        animator.setFloatValues(value);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ratio = (float) animation.getAnimatedValue();
                if (ratio == 1f && selectListener != null && selectedIndex != -1) {
                    //Log.e(TAG, "onAnimationUpdate: onSelected: " + letters[selectedIndex]);
                    selectListener.onSelected(letters[selectedIndex]);
                }
                postInvalidate();
            }
        });
        animator.start();
    }

    public interface SelectListener{
        void onSelected(String letter);
    }
}
