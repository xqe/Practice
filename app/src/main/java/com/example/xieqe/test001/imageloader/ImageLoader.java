package com.example.xieqe.test001.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.example.xieqe.test001.LruCache.ICache;
import com.example.xieqe.test001.LruCache.MemoryCache;


/**
 * Created by xieqe on 2018/3/28.
 * 负责图片的下载与加载逻辑
 * 图片的缓存交给imageCache处理
 * ImageLoader面向接口编程，
 * 防止每次加入新的缓存策略都需要修改ImageLoader类,通过if-else语句进行逻辑控制。
 */

public class ImageLoader {

    private static ICache imageCache;

    private ImageLoader() {
        //默认使用内存缓存
        imageCache = MemoryCache.getInstance();
    }

    /*** 用户自定义缓存策略*/
    public static void setImageCache(ICache imageCache) {
        ImageLoader.imageCache = imageCache;
    }

    public static ImageLoader getInstance(ICache cache) {
        imageCache = cache;
        return Holder.INSTANCE;
    }

    /*** 根据URL展示图片*/
    public void displayImage(String url, ImageView imageView) {
        Bitmap bitmap = imageCache.get(url);
        if (bitmap == null) {
            bitmap = loadBitmap(url);
            imageCache.put(url, bitmap);
        }
        imageView.setImageBitmap(bitmap);
    }

    /*** 异步加载图片*/
    private Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;
        return bitmap;
    }

    /**
     * 异步加载线程
     */
    private static class LruCacheWorkerTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String pathName = strings[0];
            Bitmap bitmap = BitmapFactory.decodeFile(pathName);
            return bitmap;
        }
    }

    private static class Holder {
        private static final ImageLoader INSTANCE = new ImageLoader();
    }
}
