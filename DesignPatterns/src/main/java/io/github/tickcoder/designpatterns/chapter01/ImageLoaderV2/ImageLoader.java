package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV2;

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
* 根据V1的经验：
* 基于单一职责原则，将cache与loader拆开
* 好处是便于修改，当与缓存相关的逻辑需要改变时，仅需要更改ImageCache即可。图片加载逻辑修改时，仅需要修改ImageLoader即可。
*
* 但是此版本的问题是：
* 可扩展性仍然较差，例如只有内存缓存这一种逻辑，而且不可以修改。
*
* */
public class ImageLoader {

    ImageCache imageCache = new ImageCache();
    ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void displayImage(final String url, final ImageView imageView) {
        final Bitmap bitmap = imageCache.get(url);
        if (bitmap!=null) {
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
