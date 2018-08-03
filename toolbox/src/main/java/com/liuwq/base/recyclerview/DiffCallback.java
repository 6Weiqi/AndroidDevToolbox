package com.liuwq.base.recyclerview;

import android.support.v4.util.ObjectsCompat;
import android.support.v7.util.DiffUtil;

import java.util.List;

/**
 * Created by liuwq on 2018/4/3.
 *
 * @see <a href="https://github.com/drakeet/MultiType/issues/56"/>详解</a>
 * <p>
 * <a href="https://blog.csdn.net/zxt0601/article/details/52562770">教程</a>
 */

public abstract class DiffCallback<T> extends DiffUtil.Callback {

    protected List<T> mOldList, mNewList;

    public DiffCallback(List<T> oldList, List<T> newList) {
        mOldList = oldList;
        mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList != null ? mOldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return mNewList != null ? mNewList.size() : 0;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        T oldItem = mOldList.get(oldItemPosition);
        T newItem = mNewList.get(newItemPosition);
        return ObjectsCompat.equals(oldItem, newItem);
    }
}
