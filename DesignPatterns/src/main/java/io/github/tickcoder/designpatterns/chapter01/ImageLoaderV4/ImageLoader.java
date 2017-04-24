package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by claris on 2017.04.24.Monday.
 */


/*
* 不过这个版本的也有问题：
* 每次加新的缓存方法时都要修改原来的代码，这样很可能引入Bug，而且会使原来的代码逻辑越来越复杂。
* 而且用户无法自定义缓存实现。
* */
public class ImageLoader {
    ImageCache imageCache = new ImageCache();
    DiskCache diskCache = new DiskCache();
    DoubleCache doubleCache = new DoubleCache();
    boolean isUseDiskCache = false;
    boolean isUseDoubleCache = false;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setUseDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    public void setUseDoubleCache(boolean useDoubleCache) {
        isUseDoubleCache = useDoubleCache;
    }

    public void displayImage(final  String url, final ImageView imageView) {
        Bitmap bitmap = null;
        if (isUseDoubleCache) {
            bitmap = doubleCache.get(url);
        } else if (isUseDiskCache) {
            bitmap = diskCache.get(url);
        } else {
            bitmap = imageCache.get(url);
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap1 = downloadImage(url);
                if (bitmap1 == null) {
                    return;
                }
                if (imageView.getTag().equals(url)) {
                    imageView.setImageBitmap(bitmap1);
                }
                imageCache.put(url, bitmap1);
            }
        });
    }

    public Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            final HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
