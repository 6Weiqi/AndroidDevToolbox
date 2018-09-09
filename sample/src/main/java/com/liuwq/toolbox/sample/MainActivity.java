package com.liuwq.toolbox.sample;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.liuwq.base.activity.BaseActivity;

import static com.google.common.base.Preconditions.checkNotNull;

public class MainActivity extends BaseActivity {

    private ViewPager mVp;
    private TabLayout mTl;

    public static TestVm getViewModel(FragmentActivity activity) {
        checkNotNull(activity);
        return ViewModelProviders.of(activity).get(TestVm.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVp = findViewById(R.id.vp_test);
        TestPagerAdapter adapter = new TestPagerAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
        mTl = findViewById(R.id.tl_test);
        mTl.setupWithViewPager(mVp, true);
    }

    class TestPagerAdapter extends FragmentPagerAdapter {

        public TestPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TestFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return String.valueOf(position);
        }
    }
}
