package io.github.tickcoder.designpatterns.chapter01;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import io.github.tickcoder.designpatterns.R;
import io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5.DiskCache;
import io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5.DoubleCache;
import io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5.IImageCache;
import io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5.ImageLoader;
import io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5.MemoryCache;

public class Chapter01Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String IMG_URL = "https://a-ssl.duitang.com/uploads/item/201510/10/20151010211325_ZdA4R.jpeg";
    private Button mLoadImageBtn;
    private ImageView mImageViewLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter01);

        mLoadImageBtn = (Button)findViewById(R.id.btn_load_image);
        mLoadImageBtn.setOnClickListener(this);
        mImageViewLoad = (ImageView) findViewById(R.id.imageview_loadit);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_image: {
                ImageLoader imageLoader = new ImageLoader();
                //imageLoader.setImageCache(new MemoryCache());
                imageLoader.setImageCache(new DiskCache());
                //imageLoader.setImageCache(new DoubleCache());

                // 自定义方式
                //imageLoader.setImageCache(new IImageCache() {
                //    @Override
                //    public Bitmap get(String url) {
                //        return null;
                //    }
                //
                //    @Override
                //    public void put(String url, Bitmap bitmap) {
                //
                //    }
                //});

                imageLoader.displayImage(IMG_URL, mImageViewLoad);

                break;
            }
            default:break;
        }
    }
}
