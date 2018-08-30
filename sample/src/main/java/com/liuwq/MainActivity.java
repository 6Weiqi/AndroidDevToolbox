package com.liuwq;

import android.os.Bundle;

import com.liuwq.base.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        showFragment(MainFrag.newInstance(), R.id.fl_container);
    }
}
