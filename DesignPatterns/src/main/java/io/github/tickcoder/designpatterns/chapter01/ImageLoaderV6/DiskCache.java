package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV6;

//import io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5.IImageCache;
import android.graphics.Bitmap;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.OutputStream;

import io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5.*;

/**
 * Created by claris on 2017.04.25.Tuesday.
 */

/*
* 用户只知道get、put，而不用关心，内部sd存储过程是否被替换为DiskLruCache或相反。
* */

public class DiskCache implements IImageCache {

    DiskLruCache mDiskLruCache;

    @Override
    public Bitmap get(String url) {
        return null;
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(url);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                if (writeBitmapToDisk(url, outputStream)) {
                    editor.commit();
                } else {
                    editor.abort();
                }
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean writeBitmapToDisk(String value, OutputStream outputStream) {
        return true;
    }
}
