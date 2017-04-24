package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by claris on 2017.04.24.Monday.
 */

/*
* 这里定义了一个IImageCache接口，MemoryCache、DiskCache、DoubleCache都实现了此接口。
* 而且定义了一个setImageCache方法，这个方法结合IImageCache可以使用户自定义缓存实现，也就是通常说的"依赖注入"。
* 通过setImageCahce方法注入不同吃的缓存实现。
*
* 使得ImageLoader更简单、健壮，扩展性、灵活性更高。
*
* 这就是开闭原则
*
* */

public class ImageLoader {
    IImageCache mImageCache = new MemoryCache();
    ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void setImageCache(IImageCache imageCache) {
        mImageCache = imageCache;
    }

    public void displayImage(final  String imageUrl, final ImageView imageView) {
        Bitmap bitmap = mImageCache.get(imageUrl);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }
        submitLoadRequest(imageUrl, imageView);
    }

    private void submitLoadRequest(final String imageUrl, final  ImageView imageView) {
        imageView.setTag(imageUrl);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = downloadImage(imageUrl);
                if (bitmap == null) {
                    return;
                }

                // 实际发现这样写不能更新到imageView，应该需要回到主线程，也就是下面那样
                // imageView.setImageBitmap(bitmap);

                if (imageView.getTag().equals(imageUrl)) {
                    final Bitmap bitmapFinal = bitmap;
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            // 已在主线程中，可以更新UI
                            imageView.setImageBitmap(bitmapFinal);
                        }
                    });
                }
                mImageCache.put(imageUrl, bitmap);
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
