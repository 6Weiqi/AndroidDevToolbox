package com.liuwq.common.base.databinding;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 描述:
 * 作者: su
 * 日期: 2017/11/3 14:29
 */

public abstract class BaseViewModel extends AndroidViewModel implements LifecycleObserver {

    protected Context mContext;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        mContext = application.getApplicationContext();
    }

    /**
     * Called when creating lifecycleOwner.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onLifecycleOwnerCreate() {
    }

    /**
     * Super implementation is empty.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onLifecycleOwnerStart() {
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onLifecycleOwnerResume() {
    }

    /**
     * Called when destroying lifecycleOwner.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onLifecycleOwnerDestroy() {
    }
}
