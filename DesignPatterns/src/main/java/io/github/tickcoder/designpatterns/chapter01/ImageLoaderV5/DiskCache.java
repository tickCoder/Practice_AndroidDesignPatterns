package io.github.tickcoder.designpatterns.chapter01.ImageLoaderV5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by claris on 2017.04.24.Monday.
 */

public class DiskCache implements IImageCache {

    static String cacheDir = getSDPath() + "/_cache/";

    @Override
    public void put(String url, Bitmap bitmap) {
        File dir = new File(cacheDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir+getFileName(url)+"."+getFileSuffix(url));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Bitmap get(String url) {
        return BitmapFactory.decodeFile(cacheDir + getFileName(url)+"."+getFileSuffix(url));
    }

    // 注：这里是方便测试加入的
    private static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        if (sdDir == null) {
            return "";
        }
        return sdDir.toString();
    }

    // 注：这里是方便测试加入的
    private String getFileName(String pathandname){
        int start=pathandname.lastIndexOf("/");
        int end=pathandname.lastIndexOf(".");
        if(start!=-1 && end!=-1){
            return pathandname.substring(start+1,end);
        }else{
            return null;
        }
    }

    // 注：这里是方便测试加入的
    private String getFileSuffix(String path) {
        String suffix = path.substring(path.lastIndexOf(".")+1);
        return suffix;
    }
}
