package com.example.xieqe.test001.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.xieqe.test001.R;

/**
 * Created by xieqe on 2017/11/25.
 */

public class LetterView_wx extends View {

    private final static String TAG = "LetterView_wx";
    private String[] letters = {"@","A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};
    private float textSize = 10;
    private int textColor = Color.BLUE;
    private int selectedColor = Color.CYAN;
    private Paint textPaint;
    private Paint selectPaint;
    private Path path;
    private int width;
    private int height;
    private float itemH;
    private float textH;
    private float drawX;
    private float drawY;
    private float selectY;
    private int selectIndex = -1;
    private Matrix matrix;
    private Bitmap searchBitmap;
    private float touchY;
    private boolean isSelectMode;
    private float controlX,controlY;
    private float endX,endY;

    private SelectListener selectListener;

    public LetterView_wx(Context context) {
        this(context,null,0);
    }

    public LetterView_wx(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LetterView_wx(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs){
        if (attrs != null){
            TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.LetterView);
            textSize = typedArray.getDimension(R.styleable.LetterView_textSize,getResources().getDimension(R.dimen.xxhdpi_textsize_10));
            textColor = typedArray.getColor(R.styleable.LetterView_textColor,Color.CYAN);
            selectedColor = typedArray.getColor(R.styleable.LetterView_selectedColor,Color.GREEN);
        }
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setStrokeWidth(2f);
        textH = textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent; //result = 0
        Log.e(TAG, "init textH: " + textH + ",textSize:" + textSize );

        selectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectPaint.setColor(selectedColor);
        selectPaint.setTextSize(textSize);
        selectPaint.setTextAlign(Paint.Align.CENTER);

        matrix = new Matrix();
        path = new Path();
        searchBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.search);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > drawX - textSize/2 || isSelectMode){
                    isSelectMode = true;
                    touchY = event.getY();
                    calculateSelect();
                    invalidate();
                    return true;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (event.getX() > drawX - textSize/2 || isSelectMode){
                    isSelectMode = false;
                    path.reset();
                    invalidate();
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        itemH = (height - getPaddingBottom() - getPaddingTop())/(letters.length + 1);
        drawX = width - getPaddingRight() - textSize/2;//text draw start from the centerX
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLetter(canvas);
        drawSelect(canvas);
    }

    private void calculateSelect(){
        for (int i = 0; i < letters.length; i++) {
            if (touchY >= itemH * i && touchY < itemH * (i + 1)  && selectIndex != i){
                Log.e(TAG, "calculateSelect: " + letters[i]);
                selectY = getPaddingTop() + itemH * i + itemH / 2;
                selectIndex = i;
                if (selectListener != null) {
                    selectListener.onSelected(letters[i]);
                }
            }
        }
    }

    private void drawSelect(Canvas canvas){
        if (selectIndex == -1 || selectIndex == 0) {
            return;
        }

        //draw red circle
        selectPaint.setColor(Color.RED);
        canvas.drawCircle(drawX,selectY - textH / 3,textH / 2,selectPaint);

        //draw white text in red circle
        selectPaint.setColor(Color.WHITE);
        selectPaint.setTextSize(textSize);
        canvas.drawText(letters[selectIndex],drawX,selectY,selectPaint);

        if (isSelectMode){
            selectPaint.setColor(selectedColor);
            path.reset();
            controlX = drawX - 5 * textSize;
            controlY = selectY - 3 * textSize;
            endX = drawX - textSize;
            endY = selectY - textH / 3;

            //draw bezier 1
            path.moveTo(controlX,selectY - textH / 3);
            path.quadTo(controlX,controlY,endX,endY);
            canvas.drawPath(path,selectPaint);

            //draw bezier 2
            controlY = selectY + 3 * textSize;
            path.moveTo(controlX,selectY - textH / 3);
            path.quadTo(controlX,controlY,endX,endY);
            canvas.drawPath(path,selectPaint);

            //draw big text
            selectPaint.setColor(Color.WHITE);
            selectPaint.setTextSize(2 * textSize);
            canvas.drawText(letters[selectIndex],controlX + 3 *textSize/2,endY + textSize,selectPaint);

            //draw red circle
            selectPaint.setColor(Color.RED);
            canvas.drawCircle(drawX,selectY - textH / 3,textH / 2,selectPaint);

            //draw white text in red circle
            selectPaint.setColor(Color.WHITE);
            selectPaint.setTextSize(textSize);
            canvas.drawText(letters[selectIndex],drawX,selectY,selectPaint);
        }
    }

    private void drawLetter(Canvas canvas){
        drawSearchIcon(canvas);
        for (int i = 1; i < letters.length; i++) {
            drawY = getPaddingTop() + itemH * i + itemH / 2;
            canvas.drawText(letters[i],drawX,drawY,textPaint);
        }
    }

    private void drawSearchIcon(Canvas canvas){
        canvas.save();
        //cause text draw start from the centerX,bottomY
        canvas.translate(drawX - textSize/2,getPaddingTop());
        canvas.scale(textSize/searchBitmap.getWidth(),textSize/searchBitmap.getWidth());
        canvas.drawBitmap(searchBitmap,matrix,textPaint);
        canvas.restore();
    }

    public void setSelect(int selectIndex){
        this.selectIndex = selectIndex;
        postInvalidate();
    }

    public void setSelectListener(SelectListener selectListener) {
        this.selectListener = selectListener;
    }

    public interface SelectListener{
        void onSelected(String letter);
    }
}
