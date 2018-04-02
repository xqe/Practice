package com.example.xieqe.test001.LruCache;

import android.graphics.Bitmap;

/**
 * Created by xieqe on 2018/3/28.
 * 缓存工具的抽象接口
 * 内存缓存、磁盘缓存、双缓存工具类都实现这个接口，
 * 外部使用缓存的统一接口，隐藏内部缓存策略的具体实现细节
 */

public interface ICache {

    Bitmap get(String key);

    void put(String key,Bitmap bitmap);

    void clear();
}
