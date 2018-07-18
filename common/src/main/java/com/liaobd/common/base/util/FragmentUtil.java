package com.liaobd.common.base.util;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 描述:
 * 作者: su
 * 日期: 2017/9/26 9:58
 */

public class FragmentUtil {
    public static void show(@NonNull FragmentActivity activity, @IdRes int containerViewId,
                            Fragment fragmentToShow, boolean anim) {
        show(activity.getSupportFragmentManager(), containerViewId, fragmentToShow,
                false, anim);
    }

    public static void show(@NonNull FragmentManager fragmentManager, @IdRes int containerViewId,
                            Fragment fragmentToShow, boolean anim) {
        show(fragmentManager, containerViewId, fragmentToShow, false, anim);
    }

    public static void show(@NonNull FragmentActivity fragmentActivity, @IdRes int containerViewId,
                            Fragment fragmentToShow, boolean addToBackStack, boolean anim) {
        show(fragmentActivity.getSupportFragmentManager(), containerViewId, fragmentToShow,
                addToBackStack, anim);
    }

    public static void show(@NonNull FragmentManager fm, @IdRes int containerViewId,
                            Fragment fragmentToShow, boolean addToBackStack, boolean anim) {
        FragmentTransaction ftx = fm.beginTransaction();
        uncommittedShowTransaction(ftx, containerViewId, fragmentToShow, addToBackStack, anim);
        if (ftx.isAddToBackStackAllowed()) {
            ftx.commit();
        } else {
            ftx.commitNow();
        }
    }

    /**
     * @param ftx
     * @param containerViewId
     * @param fragmentToShow
     * @param addToBackStack
     * @param anim
     * @return this {@code ftx}
     */
    @NonNull
    private static FragmentTransaction uncommittedShowTransaction(@NonNull FragmentTransaction ftx,
                                                                  @IdRes int containerViewId,
                                                                  Fragment fragmentToShow,
                                                                  boolean addToBackStack,
                                                                  boolean anim) {
        if (null == fragmentToShow) {
            return ftx;
        }
//        Fragment curFrag = fragmentManager.findFragmentById(containerViewId);
//        hide(fragmentManager, curFrag, anim);

        if (!fragmentToShow.isAdded()) {
            if (anim) {
                ftx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            }
            ftx.add(containerViewId, fragmentToShow);
            if (addToBackStack) {
                // 将事务添加到回退栈
                ftx.addToBackStack(null);
            }
        } else {
            if (fragmentToShow.isHidden()) {
                if (anim) {
                    ftx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                }
                ftx.show(fragmentToShow);
                if (addToBackStack && ftx.isAddToBackStackAllowed()) {
                    ftx.addToBackStack(null);
                }
            }
        }
        return ftx;
    }

    public static void hide(@NonNull FragmentActivity activity, Fragment fragmentToHide,
                            boolean anim) {
        hide(activity.getSupportFragmentManager(), fragmentToHide, false, anim);
    }

    public static void hide(@NonNull FragmentManager fragmentManager, Fragment fragmentToHide,
                            boolean anim) {
        hide(fragmentManager, fragmentToHide, false, anim);
    }

    public static void hide(@NonNull FragmentActivity fragmentActivity, Fragment fragmentToHide,
                            boolean addToBackStack, boolean anim) {
        hide(fragmentActivity.getSupportFragmentManager(), fragmentToHide, addToBackStack, anim);
    }

    public static void hide(@NonNull FragmentManager fm, Fragment fragmentToHide,
                            boolean addToBackStack, boolean anim) {
        FragmentTransaction ftx = fm.beginTransaction();
        uncommittedHideTransaction(ftx, fragmentToHide, addToBackStack, anim);
        if (ftx.isAddToBackStackAllowed()) {
            ftx.commit();
        } else {
            ftx.commitNow();
        }
    }

    /**
     * @param ftx
     * @param fragmentToHide
     * @param addToBackStack
     * @param anim
     * @return this {@code ftx}
     */
    @NonNull
    private static FragmentTransaction uncommittedHideTransaction(@NonNull FragmentTransaction ftx,
                                                                  Fragment fragmentToHide,
                                                                  boolean addToBackStack,
                                                                  boolean anim) {
        if (null == fragmentToHide) {
            return ftx;
        }

        if (/*fragmentToHide.isVisible()*/fragmentToHide.isAdded() && !fragmentToHide.isHidden()) {

            if (anim) {
                ftx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            }

            ftx.hide(fragmentToHide);
            if (addToBackStack) {
                ftx.addToBackStack(null);
            }
        }
        return ftx;
    }

    public static void showAndHideFragment(@NonNull FragmentActivity activity,
                                           @IdRes int containerId, Fragment fragmentToShow,
                                           Fragment fragmentToHide, boolean anim) {
        showAndHideFragment(activity.getSupportFragmentManager(), containerId, fragmentToShow,
                fragmentToHide, anim);
    }

    public static void showAndHideFragment(@NonNull FragmentManager fragmentManager,
                                           @IdRes int containerId, Fragment fragmentToShow,
                                           Fragment fragmentToHide, boolean anim) {
//        if (fragmentToShow == fragmentToHide) {
//            return;
//        }
        FragmentTransaction ftx = fragmentManager.beginTransaction();
        uncommittedShowTransaction(ftx, containerId, fragmentToShow, false, anim);
        uncommittedHideTransaction(ftx, fragmentToHide, false, anim);
        if (ftx.isAddToBackStackAllowed()) {
            ftx.commit();
        } else {
            ftx.commitNow();
        }
    }

    /**
     * @param fragmentActivity
     * @param containerId
     * @param shownFragment
     * @param shownAddToBack
     * @param hiddenFragment
     * @param hiddenAddToBack
     * @param anim
     */
    public static void showAndHideFragment(@NonNull FragmentActivity fragmentActivity,
                                           @IdRes int containerId, Fragment shownFragment,
                                           boolean shownAddToBack, Fragment hiddenFragment,
                                           boolean hiddenAddToBack, boolean anim) {
        if (shownFragment == hiddenFragment) {
            return;
        }

        FragmentTransaction ftx = fragmentActivity.getSupportFragmentManager().beginTransaction();
        uncommittedShowTransaction(ftx, containerId, shownFragment, shownAddToBack, anim);
        uncommittedHideTransaction(ftx, hiddenFragment, hiddenAddToBack, anim);
        if (ftx.isAddToBackStackAllowed()) {
            ftx.commit();
        } else {
            ftx.commitNow();
        }
    }
}
