package com.liuwq.base.widget;

import android.app.Dialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liuwq.common.R;
import com.liuwq.common.databinding.FragCustomDialogBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * 不自定义布局时使用 Compact 风格的对话框
 * Created by android2 on 2018/3/19.
 */

public class CustomDialogFragCompat extends DialogFragment {

    public static final String KEY_BOOLEAN_TRANSPARENT = "KEY_BOOLEAN_TRANSPARENT";
    public static final String KEY_BOOLEAN_ALERT_DIALOG = "KEY_BOOLEAN_ALERT_DIALOG";
    public static final String KEY_BOOLEAN_CANCELED_ON_TOUCH_OUTSIDE =
            "KEY_BOOLEAN_CANCELED_ON_TOUCH_OUTSIDE";
    public static final String KEY_INT_LAYOUT_RES = "KEY_INT_LAYOUT_RES";
    public static final String KEY_STRING_TITLE = "KEY_STRING_TITLE";
    public static final String KEY_STRING_MSG = "KEY_STRING_MSG";
    public static final String KEY_STRING_POSITIVE_TEXT = "KEY_STRING_POSITIVE_TEXT";
    public static final String KEY_STRING_NEGATIVE_TEXT = "KEY_STRING_NEGATIVE_TEXT";
    public static final String TAG = "CustomDialogFragCompat";

    private FragCustomDialogBinding mBinding;
    private List<Pair<Integer, Object>> mVariablePairs;

    public static class Builder {

        private String title;

        /**
         * Useless if using your own custom layout
         *
         * @return {@code null} if don't need msg
         */
        private String msg;

        private boolean windowTransparent;

        private boolean alertDialog;

        public boolean isAlertDialog() {
            return alertDialog;
        }

        public Builder setAlertDialog(boolean alertDialog) {
            this.alertDialog = alertDialog;
            return this;
        }

        private boolean canceledOnTouchOutside;

        public boolean isCanceledOnTouchOutside() {
            return canceledOnTouchOutside;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        private String positiveText;

        private String negativeText;

        private DialogInterface.OnClickListener onClickListener;

        private Dialog dialog;

        public Dialog getDialog() {
            return dialog;
        }

        public void setDialog(Dialog dialog) {
            this.dialog = dialog;
        }

        @LayoutRes
        private int layoutRes = R.layout.stub_loading_dialog;

        public String getTitle() {
            return title;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getPositiveText() {
            return positiveText;
        }

        public Builder setPositiveText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public String getNegativeText() {
            return negativeText;
        }

        public Builder setNegativeText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public DialogInterface.OnClickListener getOnClickListener() {
            return onClickListener;
        }

        public void setOnClickListener(DialogInterface.OnClickListener clickListener) {
            this.onClickListener = clickListener;
        }

        public int getLayoutRes() {
            return layoutRes;
        }

        public Builder setLayoutRes(int layoutRes) {
            this.layoutRes = layoutRes;
            return this;
        }

        public boolean isWindowTransparent() {
            return windowTransparent;
        }

        public Builder setWindowTransparent(boolean windowTransparent) {
            this.windowTransparent = windowTransparent;
            return this;
        }

        public String getMsg() {
            return msg;
        }

        public Builder setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Builder setPositiveButton(String text, DialogInterface.OnClickListener listener) {
            positiveText = text;
            onClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String text, DialogInterface.OnClickListener listener) {
            negativeText = text;
            onClickListener = listener;
            return this;
        }

        public CustomDialogFragCompat build() {
            Bundle args = new Bundle();
            args.putString(KEY_STRING_MSG, msg);
            args.putBoolean(KEY_BOOLEAN_TRANSPARENT, windowTransparent);
            args.putBoolean(KEY_BOOLEAN_ALERT_DIALOG, alertDialog);
            args.putInt(KEY_INT_LAYOUT_RES, layoutRes);
            args.putString(KEY_STRING_TITLE, title);
            args.putString(KEY_STRING_POSITIVE_TEXT, positiveText);
            args.putString(KEY_STRING_NEGATIVE_TEXT, negativeText);
            args.putBoolean(KEY_BOOLEAN_CANCELED_ON_TOUCH_OUTSIDE, canceledOnTouchOutside);
            CustomDialogFragCompat fragment = new CustomDialogFragCompat();
            fragment.setArguments(args);
            return fragment;
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }


    /**
     * @param loadingMsg
     * @return
     */
    public static CustomDialogFragCompat newTransparentLoadingDialog(String loadingMsg) {
        return newBuilder()
                .setMsg(loadingMsg)
                .setLayoutRes(R.layout.stub_loading_dialog)
                .setWindowTransparent(true)
                .build();
    }

    public static CustomDialogFragCompat newAlertDialog(String title, String msg,
                                                        String positiveText) {
        return newBuilder()
                .setAlertDialog(true)
                .setMsg(msg)
                .setTitle(title)
                .setPositiveText(positiveText)
                .build();
    }

    public static CustomDialogFragCompat newAlertDialog(boolean canceledOnTouchOutside,
                                                        String title, String msg,
                                                        String positiveText) {
        return newBuilder()
                .setAlertDialog(true)
                .setCanceledOnTouchOutside(canceledOnTouchOutside)
                .setMsg(msg)
                .setTitle(title)
                .setPositiveText(positiveText)
                .build();
    }

    /**
     * 自定义布局需要传入自定义变量时使用
     *
     * @param id
     * @param value
     */
    public void addStubBindingVariable(int id, Object value) {
        if (mVariablePairs == null) {
            mVariablePairs = new ArrayList<>();
        }
        mVariablePairs.add(Pair.create(id, value));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean transparent = getArguments().getBoolean(KEY_BOOLEAN_TRANSPARENT);
        if (transparent) {
            setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog_Transparent);
        } else {
            setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.frag_custom_dialog, container,
                false);
//        mBinding =
//                FragCustomDialogBinding.inflate(inflater, container, false);
        int layoutRes = getArguments().getInt(KEY_INT_LAYOUT_RES);
        mBinding.vsDialog.getViewStub().setLayoutResource(layoutRes);
        mBinding.vsDialog.getViewStub().inflate();
//        mBinding.vsDialog.setOnInflateListener(new ViewStub.OnInflateListener() {
//            @Override
//            public void onInflate(ViewStub stub, View inflated) {
//                String msg = getContext().getString(R.string.loading);
//                msg = getArguments().getString(KEY_STRING_MSG, msg);
//                mBinding.setLoadingMsg(msg);
//            }
//        });
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        boolean flag = getArguments().getBoolean(KEY_BOOLEAN_CANCELED_ON_TOUCH_OUTSIDE);
        getDialog().setCanceledOnTouchOutside(flag);

        String msg = getContext().getString(R.string.pls_wait);
        msg = getArguments().getString(KEY_STRING_MSG, msg);
        Builder builder = newBuilder().setMsg(msg);

        String positiveTxt = getArguments().getString(KEY_STRING_POSITIVE_TEXT);
        String negativeTxt = getArguments().getString(KEY_STRING_NEGATIVE_TEXT);
        builder.setPositiveText(positiveTxt)
                .setNegativeText(negativeTxt);
        builder.setDialog(getDialog());
        if (getActivity() instanceof DialogInterface.OnClickListener) {
            builder.setOnClickListener(
                    (DialogInterface.OnClickListener) getActivity()
            );
        } else if (getTargetFragment() instanceof DialogInterface.OnClickListener) {
            builder.setOnClickListener(
                    (DialogInterface.OnClickListener) getTargetFragment()
            );
        } else {
            builder.setOnClickListener(null);
        }
        mBinding.setBuilder(builder);
        if (mVariablePairs == null) {
            return;
        }
        for (Pair<Integer, Object> pair : mVariablePairs) {
            if (pair != null) {
                mBinding.vsDialog.getBinding().setVariable(pair.first, pair.second);
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        boolean alertDialog = getArguments().getBoolean(KEY_BOOLEAN_ALERT_DIALOG);
        if (alertDialog) {
            String title = getArguments().getString(KEY_STRING_TITLE);
            String msg = getArguments().getString(KEY_STRING_MSG);
            String positiveText = getArguments().getString(KEY_STRING_POSITIVE_TEXT);
            String negativeText = getArguments().getString(KEY_STRING_NEGATIVE_TEXT);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setTitle(title)
                    .setMessage(msg);
            if (getActivity() instanceof DialogInterface.OnClickListener) {
                builder.setPositiveButton(positiveText,
                        (DialogInterface.OnClickListener) getActivity())
                        .setNegativeButton(negativeText,
                                (DialogInterface.OnClickListener) getActivity());
            } else if (getTargetFragment() instanceof DialogInterface.OnClickListener) {
                builder.setPositiveButton(positiveText,
                        (DialogInterface.OnClickListener) getTargetFragment())
                        .setNegativeButton(negativeText,
                                (DialogInterface.OnClickListener) getTargetFragment());
            } else {
                builder.setPositiveButton(positiveText, null)
                        .setNegativeButton(negativeText, null);
            }
            return builder.create();
        } else {
            return super.onCreateDialog(savedInstanceState);
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        if (getActivity() instanceof DialogInterface.OnCancelListener) {
            ((DialogInterface.OnCancelListener) getActivity()).onCancel(dialog);
        } else if (getTargetFragment() instanceof DialogInterface.OnCancelListener) {
            ((DialogInterface.OnCancelListener) getTargetFragment()).onCancel(dialog);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (getActivity() instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) getActivity()).onDismiss(dialog);
        } else if (getTargetFragment() instanceof DialogInterface.OnDismissListener) {
            ((DialogInterface.OnDismissListener) getTargetFragment()).onDismiss(dialog);
        }
    }
}
