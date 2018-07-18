package com.liaobd.common.base.util;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 描述: 基于RxJava的倒计时逻辑封装
 * 作者: su
 * 日期: 2017/11/8 14:24
 */

public class RxCountDownTimer {
    private Disposable mDisposable;


    public RxCountDownTimer() {
    }

    public void start(final long seconds, @NonNull final Callback callback) {
        start(seconds, false, callback);
    }

    public void start(final long seconds, boolean initDelay, @NonNull final Callback callback) {
        stop();

        Observable.interval(initDelay ? 1 : 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .take(seconds)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        return aLong + 1;
                    }
                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;
                        callback.onStartCountDown();
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull Long aLong) {
                        callback.onTik(aLong, seconds);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        callback.onCompleteCountDown();
                    }
                });
    }

    public void stop() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }

    public boolean isStarted() {
        return mDisposable != null;
    }

    public interface Callback {
        void onStartCountDown();

        void onTik(long current, long total);

        void onCompleteCountDown();
    }

}
