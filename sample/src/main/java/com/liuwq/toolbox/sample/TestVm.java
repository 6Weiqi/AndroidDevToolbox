package com.liuwq.toolbox.sample;

import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.liuwq.base.databinding.BaseRequestViewModel;

public class TestVm extends BaseRequestViewModel {

    private MediatorLiveData<Integer> mMld1 = new MediatorLiveData<>();
    private MediatorLiveData<Boolean> mMld2 = new MediatorLiveData<>();
    public MediatorLiveData<String> pResultMld = new MediatorLiveData<>();

    public TestVm(@NonNull Application application) {
        super(application);
    }

    public void mld1(int incomingArg) {
        MutableLiveData<Integer> tmp = new MutableLiveData<>();
        tmp.setValue(incomingArg);
        mMld1.addSource(tmp, integer -> mMld1.setValue(100 * integer));

        pResultMld.addSource(
                mMld1,
                integer -> {
                    pResultMld.removeSource(mMld1);
                    String value = pResultMld.getValue();
                    pResultMld.setValue(value + "\nstr " + integer);
                });
    }

    public void mld2(int incomingArg) {
        MutableLiveData<Integer> tmp = new MutableLiveData<>();
        tmp.setValue(incomingArg);
        mMld2.addSource(tmp, integer -> mMld2.setValue(integer == 0));

        pResultMld.addSource(
                mMld2,
                b -> {
                    pResultMld.removeSource(mMld2);
                    String value = pResultMld.getValue();
                    pResultMld.setValue(value + "\nstr " + b);
                });
    }
}
