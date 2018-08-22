package com.liuwq.base.image;

import android.support.annotation.DrawableRes;

import com.liuwq.base.R;

/**
 * 图片加载选项
 *
 * <p>Created by Administrator on 2017/11/27.
 */
public class ImgLoaderOption {

    /**
     * Sets a resource to display if the model provided to RequestBuilder.load(Object) is null.
     */
    @DrawableRes
    private int fallback = R.drawable.ic_placeholder_image_error;

    @DrawableRes
    private int error = R.drawable.ic_placeholder_image_error;
    @DrawableRes
    private int placeholder = R.color.disable;

    private ImgSize imgSize;

    private ImgLoaderOption(int fallback, int error, int placeholder, ImgSize imgSize) {
        if (fallback > 0) {
            this.fallback = fallback;
        }
        if (error > 0) {
            this.error = error;
        }
        if (placeholder > 0) {
            this.placeholder = placeholder;
        }

        this.imgSize = imgSize;
    }

    /** @return default {@link R.color#disable} */
    public int getPlaceholder() {
        return placeholder;
    }

    /** @return default {@link R.drawable#ic_placeholder_image_error} */
    public int getError() {
        return error;
    }

    public ImgSize getImgSize() {
        return imgSize;
    }

    public int getFallback() {
        return fallback;
    }

    public static class ImgSize {
        public int width;

        public int height;

        public ImgSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    public static class Builder {
        private int fallback;
        private int error;
        private int placeholder;
        private ImgSize imgSize;

        public Builder setPlaceholder(@DrawableRes int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder setError(@DrawableRes int error) {
            this.error = error;
            return this;
        }

        public Builder setImgSize(ImgSize imgSize) {
            this.imgSize = imgSize;
            return this;
        }

        public Builder setFallback(@DrawableRes int fallback) {
            this.fallback = fallback;
            return this;
        }

        public ImgLoaderOption build() {
            return new ImgLoaderOption(fallback, error, placeholder, imgSize);
        }
    }
}
