package com.liuwq.common.base.image;

import android.support.annotation.DrawableRes;
import android.widget.ImageView;

/**
 * 常用图片加载策略，由各 app 模块实现
 * Created by Administrator on 2017/11/24.
 */

public interface ImgLoaderStrategy {

    void load(ImageView iv, Object src, ImgLoaderOption option);

    /**
     * @param iv
     * @param src
     * @param fallback Drawable or id to display if {@code src} is null.
     * @param error    Drawable or id to display if loading {@code src} fails.
     */
    void load(ImageView iv, Object src, Object fallback, Object error);

    /**
     * @param iv
     * @param src
     * @param error {@link ImgLoaderStrategy#load(ImageView, Object, Object, Object)}
     */
    void load(ImageView iv, Object src, Object error);

    /**
     * @param iv
     * @param src
     * @param placeholder Res to display while loading {@code src}.
     */
    void load(ImageView iv, Object src, @DrawableRes int placeholder);

    /**
     * @param iv
     * @param src Drawable or id.
     */
    void loadDrawable(ImageView iv, Object src);


}
