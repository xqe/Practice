package com.example.xieqe.test001.LruCache;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.io.FileSystem;

/**
 * Created by xieqe on 2018/3/28.
 * 磁盘缓存工具类，缓存到文件系统
 */

public class DiskCache implements ICache {

    private static DiskLruCache diskLruCache;
    private File cacheFile;

    private DiskCache() {
        initDisLruCache();
    }

    private void initDisLruCache() {
        cacheFile = new File(
                Environment.getExternalStorageDirectory() + File.separator + "cache");
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        long cacheSize = (long)20 * 1024 * 1024;
        diskLruCache = DiskLruCache.create(
                FileSystem.SYSTEM,cacheFile, Build.VERSION_CODES.BASE,1,cacheSize);
    }

    @Override
    public Bitmap get(String key) {
        return null;
    }

    @Override
    public void put(String key, Bitmap bitmap) {
        try {
            DiskLruCache.Editor editor = diskLruCache.edit(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {

    }

    private static class Holder {
        private static final DiskCache INSTANCE = new DiskCache();
    }

}
