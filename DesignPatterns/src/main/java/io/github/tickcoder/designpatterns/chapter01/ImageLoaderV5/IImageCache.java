package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5;

import android.graphics.Bitmap;

/**
 * Created by claris on 2017.04.24.Monday.
 */

public interface IImageCache {
    public Bitmap get(String url);
    public void put(String url, Bitmap bitmap);
}
