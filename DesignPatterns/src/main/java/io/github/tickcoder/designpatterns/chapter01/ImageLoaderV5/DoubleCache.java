package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5;

import android.graphics.Bitmap;

/**
 * Created by claris on 2017.04.24.Monday.
 */

public class DoubleCache implements IImageCache {

    IImageCache mMemoryCache = new MemoryCache();
    IImageCache mDiskCache = new DiskCache();

    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url, bitmap);
        mDiskCache.put(url, bitmap);
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }
}
