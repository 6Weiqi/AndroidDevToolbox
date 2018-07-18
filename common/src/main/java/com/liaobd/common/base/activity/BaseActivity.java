package com.liaobd.common.base.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.liaobd.common.base.model.Permission;
import com.liaobd.common.base.util.FragmentUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * 描述: Activity基类
 * 作者: su
 * 日期: 2017/9/21 14:34
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        // TODO: 2018/3/20 try a better way.
//        overridePendingTransition(R.anim.slide_in_right, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // TODO: 2018/3/20 try a better way.
//        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_right);
    }

    public View getContentView() {
        return this.findViewById(android.R.id.content);
    }

    protected void showFragment(Fragment fragment, @IdRes int containerId) {
        FragmentUtil.show(this, containerId, fragment, false);
    }

    @SuppressLint("CheckResult")
    protected void requestPermissions(@NonNull List<String> permissions,
                                      @NonNull final PermissionCallback callback) {
        if ((permissions == null) || (permissions.size() == 0)) {
            return;
        }

        final int count = permissions.size();   //count > 0 ;
        final List<Permission> permissionsGranted = new ArrayList<>();
        final List<Permission> permissionsDenied = new ArrayList<>();

        new RxPermissions(BaseActivity.this)
                .requestEach(permissions.toArray(new String[count]))
                .subscribe(new Consumer<com.tbruyelle.rxpermissions2.Permission>() {
                    int index = 0;

                    @Override
                    public void accept(com.tbruyelle.rxpermissions2.Permission permission)
                            throws Exception {
                        index++;

                        if (permission.granted) {
                            permissionsGranted.add(new Permission(permission.name, true,
                                    false));
                        } else {
                            permissionsDenied.add(new Permission(permission.name, false,
                                    permission.shouldShowRequestPermissionRationale));

                            //拒绝权限，并点击“不再询问”
//                            if(permission.shouldShowRequestPermissionRationale){
//                            }else {
//                            }
                        }

                        if (index == count) {
                            //所有权限请求完毕
                            int countGranted = permissionsGranted.size();

                            if (countGranted == count) {
                                //所有权限都成功获取被授予；
                                callback.onPermissionGranted(permissionsGranted, true);
                            } else if (countGranted == 0) {
                                callback.onPermissionDenied(permissionsDenied, true);
                            } else {
                                callback.onPermissionGranted(permissionsGranted, false);
                                callback.onPermissionDenied(permissionsDenied, false);
                            }
                        }
                    }
                });
    }


    public interface PermissionCallback {
        void onPermissionGranted(List<Permission> permissions, boolean allGranted);

        void onPermissionDenied(List<Permission> permissions, boolean allDenied);
    }

}
