package com.liuwq.base.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;

import com.liuwq.base.image.ImgLoaderManager;

/**
 * 描述: 在 dataBinding 布局下可用
 * 作者: liuwq
 * 日期: 2017/12/13 15:34
 */

public class ViewBindings {

    @BindingAdapter("selected")
    public static void setViewSelected(View view, boolean selected) {
        view.setSelected(selected);
    }

    @BindingAdapter("android:onRefresh")
    public static void setOnRefreshListener(SwipeRefreshLayout view,
                                            final BaseRequestViewModel viewModel) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.onRefresh();
            }
        });
    }

    @BindingAdapter({"url", "error"})
    public static void bindImageUrl(ImageView imageView, Object url, Object error) {
        ImgLoaderManager.get().load(imageView, url, error);
    }

}
