package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV4;

import android.graphics.Bitmap;

/**
 * Created by claris on 2017.04.24.Monday.
 */

public class DoubleCache {
    ImageCache memoryCache = new ImageCache();
    DiskCache diskCache = new DiskCache();

    public Bitmap get(String url) {
        Bitmap bitmap = memoryCache.get(url);
        if (bitmap == null) {
            bitmap = diskCache.get(url);
        }
        return bitmap;
    }

    public void put(String url, Bitmap bitmap) {
        memoryCache.put(url, bitmap);
        diskCache.put(url, bitmap);
    }
}
