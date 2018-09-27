package com.liuwq.base.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ObjectsCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 描述: 一般 RecyclerView 列表适配器基类 <br>
 * 作者: su <br>
 * 日期: 2017/10/31 15:39
 */
public abstract class BaseListAdapter<E, ViewHolder extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<ViewHolder> {

    private Context mContext;
    @NonNull private List<E> mDataList = Collections.emptyList();
    private RecyclerView mRecyclerView;
    private LayoutInflater mLayoutInflater;
    @Nullable protected OnItemClickListener<E> mItemClickListener;
    private Disposable mSubscribe;

    public interface OnItemClickListener<E> {
        void onItemClick(View itemView, int pos, E data);
    }

    protected Context getContext() {
        return mContext;
    }

    public void setOnItemClickListener(OnItemClickListener<E> listener) {
        mItemClickListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        this.mRecyclerView = recyclerView;
        this.mContext = recyclerView.getContext();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.mRecyclerView = null;
        if (mSubscribe != null && !mSubscribe.isDisposed()) {
            mSubscribe.dispose();
        }
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    protected boolean autoNotify() {
        return true;
    }

    public int getCount() {
        return getData().size();
    }

    @NonNull
    public List<E> getData() {
        return mDataList;
    }

    public E getData(int position) {
        if (!isLegalPositionForGetting(position)) {
            return null;
        }
        return getData().get(position);
    }

    protected void setList(@NonNull List<E> list) {
        checkNotNull(list);
        mDataList = list;
    }

    public void setData(@NonNull final List<E> dataList) {
        checkNotNull(dataList);
        mSubscribe =
                Single.fromCallable(
                                () -> {
                                    BaseDiffCallback diffCallback =
                                            new BaseDiffCallback(mDataList, dataList);
                                    return DiffUtil.calculateDiff(diffCallback);
                                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                diffResult -> {
                                    setList(dataList);
                                    diffResult.dispatchUpdatesTo(this);
                                });
        //        if (dataList.isEmpty()) {
        //            return;
        //        }
        //
        //        getData().clear();
        //        getData().addAll(dataList);
        //        notifyDataSetChanged();
        //
        //        if (autoNotify()) {
        //            notifyDataSetChanged();
        //            int prevSize = getData().size();
        //            //            notifyItemRangeRemoved(0, prevSize);
        //            //            notifyItemRangeInserted(0, dataList.size());
        //        }
    }

    protected void addList(@NonNull List<E> list) {
        checkNotNull(list);
        mDataList.addAll(list);
    }

    public void addData(@NonNull List<E> dataList) {
        int positionStart = mDataList.size();
        addList(dataList);
        int insertedCount = dataList.size();
        notifyItemRangeInserted(positionStart, insertedCount);
        //        if (dataList.isEmpty()) {
        //            return;
        //        }
        //        int prevSize = getData().size();
        //        getData().addAll(dataList);
        //        notifyDataSetChanged();
        //
        //        if (autoNotify()) {
        //            //            notifyDataSetChanged();
        //            notifyItemRangeInserted(prevSize, dataList.size());
        //        }
    }

    public boolean addData(@NonNull E data) {
        return addData(data, getItemCount());
    }

    public boolean addData(@NonNull E data, int position) {
        checkNotNull(data);
        if (!isLegalPositionForAdding(position)) {
            return false;
        }
        mDataList.add(position, data);
        notifyItemInserted(position);
        //        getData().add(position, data);
        //
        //        if (autoNotify() && isRecyclerViewAttached()) {
        //            //            notifyItemInserted(position);
        //            //            scrollToPosition(position);
        //            notifyDataSetChanged();
        //        }
        return true;
    }

    public boolean updateData(@NonNull E data, int position) {
        checkNotNull(data);
        if (!isLegalPositionForGetting(position)) {
            return false;
        }
        mDataList.set(position, data);
        notifyItemChanged(position);

        //        getData().set(position, data);
        //
        //        if (autoNotify() && isRecyclerViewAttached()) {
        //            notifyItemChanged(position);
        //            scrollToPosition(position);
        //        }

        return true;
    }

    public boolean removeData(@NonNull E data) {
        return removeData(getDataPosition(data));
    }

    public boolean removeData(int position) {
        if (!isLegalPositionForGetting(position)) {
            return false;
        }
        mDataList.remove(position);
        notifyItemRemoved(position);
        //        getData().remove(position);
        //
        //        if (autoNotify() && isRecyclerViewAttached()) {
        //            //            notifyItemRemoved(position);
        //            //            notifyItemRangeChanged(position, getCount() - position);
        //            // TODO: 2017/11/8
        //            notifyDataSetChanged();
        //        }

        return true;
    }

    public boolean removeData(int positionStart, int itemCount) {
        if (!isLegalPositionForGetting(positionStart) || (positionStart + itemCount) > getCount()) {
            return false;
        }

        List<E> removeList = new ArrayList<>();

        for (int i = itemCount - 1; i >= 0; i--) {
            removeList.add(getData(positionStart + i));
        }
        mDataList.removeAll(removeList);
        notifyItemRangeRemoved(positionStart, itemCount);
        //        getData().removeAll(removeList);
        //
        //        if (autoNotify() && isRecyclerViewAttached()) {
        //            //            notifyItemRangeChanged(positionStart, itemCount);
        //            notifyDataSetChanged();
        //        }

        return true;
    }

    public boolean isExist(@NonNull Judgment<E> judgment) {
        if (isEmpty()) {
            return false;
        }

        return query(judgment).size() > 0;
    }

    public ArrayList<E> query(@NonNull Judgment<E> judgment) {
        ArrayList<E> result = new ArrayList<>();

        if (isEmpty()) {
            return result;
        }

        int n = getItemCount();
        for (int i = 0; i < n; i++) {
            E entity = getData(i);
            if (judgment.test(entity)) {
                result.add(entity);
            }
        }
        return result;
    }

    public void clear() {
        if (isEmpty()) {
            return;
        }

        int count = getItemCount();
        mDataList.clear();
        notifyItemRangeRemoved(0, count);
        //        getData().clear();
        //
        //        if (autoNotify() && isRecyclerViewAttached()) {
        //            notifyItemRangeRemoved(0, count);
        //        }
    }

    public int getDataPosition(@NonNull E data) {
        checkNotNull(data);
        if (isEmpty()) {
            return RecyclerView.NO_POSITION;
        }

        int n = getItemCount();
        for (int i = 0; i < n; i++) {
            E entity = getData().get(i);
            if (entity == data) {
                return i;
            }
        }

        return RecyclerView.NO_POSITION;
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public boolean scrollToPosition(int position) {
        if (!isLegalPositionForGetting(position)) {
            return false;
        }

        if (!isRecyclerViewAttached()) {
            return false;
        }

        getRecyclerView().smoothScrollToPosition(position);
        return true;
    }

    public boolean isRecyclerViewAttached() {
        return getRecyclerView() != null;
    }

    public boolean isLegalPositionForGetting(int position) {
        if (isEmpty()) {
            return position == 0;
        } else {
            return (position >= 0 && position < getItemCount());
        }
    }

    public boolean isLegalPositionForAdding(int position) {
        if (isEmpty()) {
            return position == 0;
        } else {
            return (position >= 0 && position <= getItemCount());
        }
    }

    public int getLastPosition() {
        return isEmpty() ? 0 : getItemCount() - 1;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @NonNull
    public LayoutInflater getLayoutInflater() {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(mContext);
        }
        return mLayoutInflater;
    }

    class BaseDiffCallback extends DiffCallback<E> {

        public BaseDiffCallback(List<E> oldList, List<E> newList) {
            super(oldList, newList);
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            E oldItem = mOldList.get(oldItemPosition);
            E newItem = mNewList.get(newItemPosition);
            return ObjectsCompat.equals(oldItem, newItem);
        }
    }
}
