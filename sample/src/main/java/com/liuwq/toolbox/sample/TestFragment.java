package com.liuwq.toolbox.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuwq.base.fragment.BaseTitleFragment;

import timber.log.Timber;

public class TestFragment extends BaseTitleFragment {

    public static TestFragment newInstance(int position) {

        Bundle args = new Bundle();
        args.putInt("KEY_POSITION", position);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View provideContentView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_test, container, false);
    }

    @Override
    protected void onInit() {}

    @Override
    protected void onLazyLoadingData() {
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        int position = args.getInt("KEY_POSITION");
        TextView tv = (TextView) findViewById(R.id.tv_test);
        tv.setText("Fragment " + position);
        Timber.d("TestFragment %d onLazyLoadingData", position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle args = getArguments();
        if (args == null) {
            return;
        }
        int position = args.getInt("KEY_POSITION");
        Timber.d("TestFragment %d onDestroyView", position);
    }
}
