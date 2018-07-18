package com.liaobd.common.base.fragment;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.liaobd.common.R;

/**
 * 描述: 带有标题栏fragment基类
 * 作者: su
 * 日期: 2017/10/17 14:35
 */

public abstract class BaseTitleFragment extends BaseFragment {
    private static int sDefaultLeftIconRes = R.drawable.ic_arrow_back_white_24dp;
    private View mInflatedTitleView;

    private final View.OnClickListener mDefaultLeftIconClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getActivity().onBackPressed();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = inflater.inflate(R.layout.frag_base_title, container, false);

            ViewStub stub = mRootView.findViewById(R.id.vs_title);
            stub.setLayoutResource(getTitleLayoutRes());
            mInflatedTitleView = stub.inflate();

            FrameLayout frameLayout = mRootView.findViewById(R.id.fl_content_base_title);
            View contentView = provideContentView(inflater, container, savedInstanceState);
            if (contentView != null) {
                frameLayout.addView(provideContentView(inflater, container, savedInstanceState),
                        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
            }

            if (defTitleLayout()) {
                Toolbar defToolbar = (Toolbar) mInflatedTitleView;
                defToolbar.setTitle("");
                defToolbar.inflateMenu(R.menu.empty);
                defToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return BaseTitleFragment.this.onMenuItemClick(item.getItemId(),
                                (String) item.getTitle());
                    }
                });
            }
            if (dividerLineLayoutRes() != 0) {
                stub = mRootView.findViewById(R.id.vs_divider_line);
                stub.setLayoutResource(dividerLineLayoutRes());
                stub.inflate();
            }

        }
        return mRootView;
    }

    /**
     * Subclass to override if you want a divider line.
     *
     * @return
     */
    @LayoutRes
    protected int dividerLineLayoutRes() {
        return 0;
    }

    /**
     * 子类不重写使用默认布局：{@link R.layout#stub_def_tool_bar}
     *
     * @return
     */
    @LayoutRes

    protected int getTitleLayoutRes() {
        return R.layout.stub_def_tool_bar;
    }

    private boolean defTitleLayout() {
        return getTitleLayoutRes() == R.layout.stub_def_tool_bar;
    }

    /**
     * @param <T> {@link #getTitleLayoutRes()} 的根部局类型
     * @return 根部局
     */
    @Nullable
    protected final <T extends View> T getTitleView() {
        return (T) mInflatedTitleView;
    }

    public static void setDefaultLeftIconRes(@DrawableRes int res) {
        sDefaultLeftIconRes = res;
    }

    /**
     * 子类重写，处理菜单项点击事件
     *
     * @param id
     * @param text
     * @return
     */
    protected boolean onMenuItemClick(int id, String text) {
        return false;
    }

    /**
     * 居左显示标题文本
     *
     * @param title
     */
    protected void showTitleTextLeft(String title) {
        if (defTitleLayout()) {
            TextView tvTitle = mInflatedTitleView.findViewById(R.id.tv_title);
            tvTitle.setText(title);

            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) tvTitle.getLayoutParams();
            layoutParams.gravity = Gravity.START;
        }
    }

    /**
     * 居中显示标题文本
     *
     * @param title
     */
    protected void showTitleTextCenter(String title) {
        if (defTitleLayout()) {
            TextView tvTitle = mInflatedTitleView.findViewById(R.id.tv_title);
            tvTitle.setText(title);

            Toolbar.LayoutParams layoutParams = (Toolbar.LayoutParams) tvTitle.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
        }
    }

    /**
     * 显示左侧图标
     */
    protected void showTitleIconLeft() {
        showTitleIconLeft(sDefaultLeftIconRes, mDefaultLeftIconClickListener);
    }

    /**
     * 显示左侧图标
     *
     * @param icon
     * @param listener
     */
    protected void showTitleIconLeft(@Nullable Integer icon,
                                     @Nullable View.OnClickListener listener) {
        if (!defTitleLayout()) {
            return;
        }
        Toolbar toolbar = (Toolbar) mInflatedTitleView;
        toolbar.setNavigationIcon(icon == null ? sDefaultLeftIconRes : icon);
        toolbar.setNavigationOnClickListener(listener == null ? mDefaultLeftIconClickListener
                : listener);
    }

    /**
     * 添加右侧菜单项(文字）
     *
     * @param id
     * @param text
     * @return
     */
    protected boolean addMenuItemText(int id, String text) {
        return addMenuItem(id, text, null, true, true);
    }

    /**
     * 添加右侧菜单项(图标）
     *
     * @param id
     * @param icon
     * @return
     */
    protected boolean addMenuItemIcon(int id, @DrawableRes int icon) {
        return addMenuItem(id, "", icon, true, true);
    }

    /**
     * 添加右侧菜单项
     *
     * @param id             菜单项id
     * @param text           菜单项文本
     * @param icon           null表示不显示图标，显示text
     * @param showAsAction   false将收缩至“更多”
     * @param replaceIfExist id已存在时是否覆盖
     * @return
     */
    protected boolean addMenuItem(int id, String text, @Nullable @DrawableRes Integer icon,
                                  boolean showAsAction, boolean replaceIfExist) {
        if (!defTitleLayout()) {
            return false;
        }
        Toolbar toolbar = (Toolbar) mInflatedTitleView;
        Menu menu = toolbar.getMenu();

        if (isMenuItemExist(id)) {
            if (replaceIfExist) {
                menu.removeItem(id);
            } else {
                return false;
            }
        }

        MenuItem item = menu.add(0, id, menu.size(), text);
        if (icon != null) {
            item.setIcon(icon);
        }

        item.setShowAsAction(showAsAction ? MenuItem.SHOW_AS_ACTION_ALWAYS : MenuItem.SHOW_AS_ACTION_NEVER);

        return true;
    }

    private boolean isMenuItemExist(int id) {
        if (!defTitleLayout()) {
            return false;
        }
        Toolbar toolbar = (Toolbar) mInflatedTitleView;
        Menu menu = toolbar.getMenu();

        int n = menu.size();
        if (n == 0) {
            return false;
        }

        for (int i = 0; i < n; i++) {
            if (menu.getItem(i).getItemId() == id) {
                return true;
            }
        }

        return false;
    }


}
