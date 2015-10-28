package com.sk.woodpecker.util.image;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by luffy on 15/4/2.
 */
public class ImageLoadUtil {

    /**
     * 通用获取图片方法
     *
     * @param imageView item图片imageview
     * @param url       图片的url
     */
    public static void getCommonImage(final ImageView imageView, String url) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(new ColorDrawable(Color.WHITE))
                .showImageForEmptyUri(new ColorDrawable(Color.WHITE))
                .showImageOnFail(new ColorDrawable(Color.WHITE))
                .cacheInMemory(true)
                .resetViewBeforeLoading(true)
                .cacheOnDisk(true)
                .considerExifParams(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageLoader.displayImage(url, imageView, options);
    }

    /**
     * 获取圆形头像
     *
     * @param imageView 头像imageview
     * @param url       图片的url
     */
    public static void getCircleAvatarImage(ImageView imageView, int id, String url) {
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(id)
                .showImageForEmptyUri(id)
                .showImageOnFail(id)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .resetViewBeforeLoading(true)
                .considerExifParams(true)
                .displayer(new CircleBitmapDisplayer())
                .build();
        imageLoader.displayImage(url, imageView, options);
    }

}
