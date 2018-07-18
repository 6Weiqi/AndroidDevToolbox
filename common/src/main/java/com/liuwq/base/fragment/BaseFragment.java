package com.liuwq.common.base.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.ArrayRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.liuwq.common.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 描述: Fragment基类
 * 作者: su
 * 日期: 2017/10/16 18:11
 */

public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    private boolean mFirstTimeResume = true;
    private ProgressDialog mLoadingDialog;
    private AlertDialog mConfirmDialog;

    protected abstract View provideContentView(LayoutInflater inflater,
                                               @Nullable ViewGroup container,
                                               @Nullable Bundle savedInstanceState);

    /**
     * You can observe something here.
     */
    protected abstract void onInit();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = provideContentView(inflater, container, savedInstanceState);
        }

        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mFirstTimeResume) {
            onInit();
            mFirstTimeResume = false;

            if (subscribeEvents()) {
                EventBus.getDefault()
                        .register(this);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (subscribeEvents()) {
            EventBus.getDefault()
                    .unregister(this);
        }
    }

    /**
     * 子类重写
     *
     * @return
     */
    protected boolean subscribeEvents() {
        return false;
    }

    protected void postEvent(Object event) {
        EventBus.getDefault()
                .post(event);
    }

    protected View findViewById(@IdRes int id) {
        if (mRootView == null) {
            return null;
        }
        return mRootView.findViewById(id);
    }

    protected void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
    }

    protected void showSnackbar(String text) {
        showSnackbar(getView(), text);
    }

    protected void showSnackbar(@NonNull View parent, String text) {
        Snackbar.make(parent, text, Snackbar.LENGTH_SHORT).show();
    }

    protected void showLoadingDialog() {
        showLoadingDialog(getString(R.string.pls_wait), false);
    }

    protected void showLoadingDialog(String msg) {
        showLoadingDialog(msg, false);
    }

    protected void showLoadingDialog(String msg, boolean cancelable) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(getActivity());
        }

        hideLoadingDialog();

        mLoadingDialog.setCancelable(cancelable);

        if (!TextUtils.isEmpty(msg)) {
            mLoadingDialog.setMessage(msg);
        }

        mLoadingDialog.show();
    }

    protected void hideLoadingDialog() {
        if ((mLoadingDialog != null) && (mLoadingDialog.isShowing())) {
            mLoadingDialog.dismiss();
        }
    }

    protected void hideConfirmDialog() {
        if (mConfirmDialog != null && mConfirmDialog.isShowing()) {
            mConfirmDialog.dismiss();
        }
    }

    protected void showConfirmDialog(@Nullable String msg,
                                     @Nullable String actionPositive,
                                     @Nullable DialogInterface.OnClickListener listenerPositive) {
        showConfirmDialog(true, msg, actionPositive, listenerPositive, null,
                null);
    }

    protected void showConfirmDialog(boolean cancelable, @Nullable String msg,
                                     @Nullable String actionPositive,
                                     @Nullable DialogInterface.OnClickListener listenerPositive,
                                     @Nullable String actionNegative,
                                     @Nullable DialogInterface.OnClickListener listenerNegative) {
        hideConfirmDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(cancelable)
                .setMessage(msg);

        if (!TextUtils.isEmpty(actionNegative)) {
            builder.setNegativeButton(actionNegative, listenerNegative);
        }

        if (!TextUtils.isEmpty(actionPositive)) {
            builder.setPositiveButton(actionPositive, listenerPositive);
        }
        mConfirmDialog = builder.create();
        mConfirmDialog.show();
    }

    @SuppressLint("ResourceType")
    protected void showConfirmDialog(boolean cancelable, @StringRes int msg,
                                     @StringRes int positiveId,
                                     @Nullable DialogInterface.OnClickListener listenerPositive,
                                     @StringRes int negativeId,
                                     @Nullable DialogInterface.OnClickListener listenerNegative,
                                     @Nullable DialogInterface.OnDismissListener dismissListener) {
        hideConfirmDialog();

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setCancelable(cancelable)
                .setMessage(msg);
        if (positiveId > 0) {
            builder.setPositiveButton(positiveId, listenerPositive);
        }
        if (negativeId > 0) {
            builder.setNegativeButton(negativeId, listenerNegative);
        }
        builder.setOnDismissListener(dismissListener);
        mConfirmDialog = builder.create();

        mConfirmDialog.show();
    }

    protected void showConfirmDialog(@StringRes int msg,
                                     @StringRes int positiveId,
                                     @Nullable DialogInterface.OnClickListener listenerPositive) {
        hideConfirmDialog();

        mConfirmDialog = new AlertDialog.Builder(getContext())
                .setMessage(msg)
                .setPositiveButton(positiveId, listenerPositive)
                .create();

        mConfirmDialog.show();
    }

    protected void showItemsDialog(@ArrayRes int itemsId,
                                   DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(getContext())
                .setItems(itemsId, listener)
                .show();
    }

    protected void showSingleChoiceItemsDialog(@StringRes int titleId, String[] items,
                                               int checkedPos,
                                               DialogInterface.OnClickListener listener) {
        new AlertDialog.Builder(getContext())
                .setTitle(titleId)
                .setSingleChoiceItems(items, checkedPos, listener)
                .show();
    }

    /**
     * 关闭系统软键盘
     */
    protected void closeKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            IBinder token = getActivity().getWindow().getDecorView().getWindowToken();
            imm.hideSoftInputFromWindow(token, 0);
        }
    }

}
