package com.liuwq.base.databinding;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;

import io.reactivex.disposables.Disposable;

/**
 * 描述:
 * 作者: su
 * 日期: 2017/11/1 17:44
 */

public abstract class BaseRequestViewModel extends BaseViewModel {

    /**
     * 是否处于刷新中
     */
    public ObservableBoolean pObRefreshing = new ObservableBoolean(false);

    /**
     * 错误信息
     */
    public MutableLiveData<String> pMldError = new MutableLiveData<>();

    /**
     * 成功信息
     */
    public MutableLiveData<String> pMldSuccess = new MutableLiveData<>();

    /**
     * 用于取消订阅
     */
    protected Disposable mDisposable;

    public BaseRequestViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 使用数据绑定时刷新逻辑的回调
     *
     * @see ViewBindings#setOnRefreshListener(SwipeRefreshLayout, BaseRequestViewModel)
     */
    public void onRefresh() {
    }

    /**
     * 取消上下游间的订阅关系，上游依然发送事件，下游接收不到事件
     */
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    /**
     * 包括一个取消订阅的操作
     */
    @Override
    public void onLifecycleOwnerDestroy() {
        super.onLifecycleOwnerDestroy();
        dispose();
    }
}
