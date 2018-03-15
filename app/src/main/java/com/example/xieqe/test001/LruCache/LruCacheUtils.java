package com.example.xieqe.test001.LruCache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.xieqe.test001.R;

/**
 * Created by xieqe on 2018/3/15.
 */

public class LruCacheUtils {

    private static LruCache<String, Bitmap> memoryCache;

    public static void initLruCache() {
        if (memoryCache != null) {
            return;
        }
        //初始化
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8; //缓存空间取内存的1/8
        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                //返回bitmap大小 单位：kb
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    public static void addBitmapToMemoryCache(String key,Bitmap bitmap){
        if (memoryCache.get(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public static Bitmap getBitmapFromMemoryCache(String key) {
        return memoryCache.get(key);
    }

    public void clear(String key) {
        memoryCache.remove(key);
    }

    public static void loadBitmap(String pathName,ImageView imageView) {
        String imageKey = String.valueOf(pathName);
        Bitmap bitmap = getBitmapFromMemoryCache(imageKey);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            bitmap = BitmapFactory.decodeFile(pathName);
            imageView.setImageBitmap(bitmap);
            new LruCacheWorkerTask().execute(pathName);
        }
    }

    private static class LruCacheWorkerTask extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String pathName = strings[0];
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            addBitmapToMemoryCache(pathName,bitmap);
            return bitmap;
        }
    }
}
