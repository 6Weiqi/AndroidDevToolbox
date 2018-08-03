package com.liuwq.base.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/9/11.
 */
public class GridSpacesItemDecoration extends RecyclerView.ItemDecoration {

    private int spacePx;

    public GridSpacesItemDecoration(int spacePx) {
        this.spacePx = spacePx;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.left = spacePx;
        outRect.right = spacePx;
//        outRect.bottom = spacePx;
//        if (parent.getChildAdapterPosition(view) == 0) {
        outRect.top = spacePx;
//        }
    }
}
