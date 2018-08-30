package com.liuwq;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuwq.base.fragment.BaseTitleFragment;

public class MainFrag extends BaseTitleFragment {

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
    protected void onInit() {
        addMenuItemText(1, "menuItem1");
        showTitleTextCenter("title");
    }
}
