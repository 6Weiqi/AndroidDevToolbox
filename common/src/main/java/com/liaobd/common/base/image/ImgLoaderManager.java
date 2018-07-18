package com.liaobd.common.base.image;

import android.widget.ImageView;

/**
 * Created by Administrator on 2017/11/27.
 */

public class ImgLoaderManager implements ImgLoaderStrategy {

    private static ImgLoaderManager sInstance;
    private ImgLoaderStrategy mImgLoader;

    private ImgLoaderManager() {
    }

    public static ImgLoaderManager get() {
        if (sInstance == null) {
            sInstance = new ImgLoaderManager();
        }
        return sInstance;
    }

    public void setImgLoader(ImgLoaderStrategy imgLoader) {
        mImgLoader = imgLoader;
    }

    @Override
    public void load(ImageView iv, Object src, ImgLoaderOption option) {
        checkImgLoaderStrategy();
        mImgLoader.load(iv, src, option);
    }

    @Override
    public void load(ImageView iv, Object src, Object fallback, Object error) {
        checkImgLoaderStrategy();
        mImgLoader.load(iv, src, fallback, error);
    }

    @Override
    public void load(ImageView iv, Object src, Object error) {
        checkImgLoaderStrategy();
        mImgLoader.load(iv, src, error);
    }

    @Override
    public void load(ImageView iv, Object src, int placeholder) {
        checkImgLoaderStrategy();
        mImgLoader.load(iv, src, placeholder);
    }

    @Override
    public void loadDrawable(ImageView iv, Object src) {
        checkImgLoaderStrategy();
        mImgLoader.loadDrawable(iv, src);
    }

    private void checkImgLoaderStrategy() {
        if (mImgLoader == null) {
            throw new IllegalStateException("ImgLoader not found. Call setImgLoader() first.");
        }
    }
}
