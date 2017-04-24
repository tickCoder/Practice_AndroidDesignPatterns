package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by claris on 2017.04.24.Monday.
 */

public class MemoryCache implements IImageCache {

    private LruCache<String, Bitmap> mMemoryCache;

    public MemoryCache() {
        initImageCache();
    }

    private void initImageCache() {
        final int maxMemory = (int)(Runtime.getRuntime().maxMemory()/1024);
        final int cacheSize = maxMemory/4;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(String url) {
        return mMemoryCache.get(url);
    }
}
