package com.liuwq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuwq.base.fragment.BaseTitleFragment;

public class MainFrag extends BaseTitleFragment {

    private View mFragView;

    public static MainFrag newInstance() {

        Bundle args = new Bundle();

        MainFrag fragment = new MainFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View provideContentView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragView = view;
    }

    @Override
    protected void onInit() {
        addMenuItemText(1, "menuItem1");
        showTitleTextCenter("title");
    }

    @Override
    protected void onLazyLoadingData() {
        super.onLazyLoadingData();
        Log.d("lwq", "single Frag lazy loading data");
        TextView tv = (TextView) findViewById(R.id.tv_test);
        tv.setText("hello toolbox");
    }
}
