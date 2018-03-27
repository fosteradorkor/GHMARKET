package com.mirka.app.ghmarket.misc;

import android.graphics.Rect;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by root on 6/18/17.
 */

public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration {

    public static final int HORIZONTAL = OrientationHelper.HORIZONTAL;

    public static final int VERTICAL = OrientationHelper.VERTICAL;

    int spacing;
    int orientation;
    boolean includeSides = false;

    /**
     * @param spacing     Current context, will be used to access resources.
     * @param orientation Layout orientation. Should be {@link #HORIZONTAL} or {@link
     *                    #VERTICAL}.
     */

    public LinearSpacingItemDecoration(int spacing, int orientation, boolean includeSides) {
        this.spacing = spacing;
        this.orientation = orientation;
        this.includeSides = includeSides;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        if (includeSides) {
            if (this.orientation == HORIZONTAL) {
                outRect.top = spacing / 2;
                outRect.bottom = spacing / 2;

            } else {
                outRect.left = spacing / 2;
                outRect.right = spacing / 2;
            }
        }

        if (position == 0) {      //first or last

            if (this.orientation == HORIZONTAL) {
                outRect.left = spacing;
                outRect.right = spacing / 2;
            } else {
                outRect.top = spacing;
                outRect.bottom = spacing / 2;
            }


        }
        if (position == parent.getAdapter().getItemCount() - 1) {
            if (this.orientation == HORIZONTAL) {
                outRect.right = spacing;
                outRect.left = spacing / 2;
            } else {
                outRect.bottom = spacing;
                outRect.top = spacing / 2;
            }

        }
        if (position > 0 && position < parent.getAdapter().getItemCount() - 1) {
            if (this.orientation == HORIZONTAL) {
                outRect.left = spacing / 2;
                outRect.right = spacing / 2;
            } else {
                outRect.top = spacing / 2;
                outRect.bottom = spacing / 2;
            }
        }

        if (!includeSides) {
            if (position == 0) {
                if (this.orientation == HORIZONTAL) {
//                     outRect.right = spacing;
                    outRect.left = 0;
//                    outRect.right = 0;
                }
            }
            if (position == parent.getAdapter().getItemCount() - 1)
                outRect.right = 0;
        }

    }

}
