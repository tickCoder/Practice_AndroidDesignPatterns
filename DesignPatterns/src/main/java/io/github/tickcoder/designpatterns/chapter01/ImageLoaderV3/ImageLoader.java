package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV3;

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
* 开闭原则：
* 软件中的对象（类、模块、函数等）应该对于扩展是开放的，但是对于修改是封闭的。
* 一个类的实现只应该因错误而被修改，新的或者改变的特性应该通过新建不同的类实现，新建的类可以通过继承来重用原类的代码。
*
* V2的缺点是：
* android应用的内存有线，而且具有易失性，当应用重新启动之后，原来加载过的图片将丢失，会导致加载缓慢、耗费流量问题。
* 因此可以考虑引入文件缓存。
*
* 但是此版本的问题是：
* 使用内存缓存与文件缓存是互斥的。
*
* 好的方式应该是：
* 优先使用内存缓存，如果内存中没有，则去SD文件中查找，如果SD文件中也没有，才去网络下载。
* */
public class ImageLoader {

    ImageCache imageCache = new ImageCache();
    DiskCache diskCache = new DiskCache();
    boolean isUseDiskCache = false;
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setUseDiskCache(boolean useDiskCache) {
        isUseDiskCache = useDiskCache;
    }

    public void displayImage(final  String url, final ImageView imageView) {
        Bitmap bitmap = isUseDiskCache ? diskCache.get(url) : imageCache.get(url);
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
