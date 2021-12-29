package com.tokyonth.mlkitocr.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDividerDrawable;
    private int mDividerHeight = 1;
    private Paint mColorPaint;

    public final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public GridDividerItemDecoration(Context context) {
        final TypedArray ta = context.obtainStyledAttributes(ATTRS);
        mDividerDrawable = ta.getDrawable(0);
        ta.recycle();
    }

    public GridDividerItemDecoration(Context context, int dividerHeight, int dividerColor) {
        this(context);
        mDividerHeight = dividerHeight;
        mColorPaint = new Paint();
        mColorPaint.setColor(dividerColor);
    }

    public GridDividerItemDecoration(Context context, int dividerHeight, Drawable dividerDrawable) {
        this(context);
        mDividerHeight = dividerHeight;
        mDividerDrawable = dividerDrawable;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawHorizontalDivider(c, parent);
        drawVerticalDivider(c, parent);
    }

    public void drawVerticalDivider(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            final int top = child.getTop() - params.topMargin;
            final int bottom = child.getBottom() + params.bottomMargin;

            int left;
            int right;

            //左边第一列
            if ((i % 3) == 0) {
                //item左边分割线
                left = child.getLeft();
                right = left + mDividerHeight;
                mDividerDrawable.setBounds(left, top, right, bottom);
                mDividerDrawable.draw(c);
                if (mColorPaint != null) {
                    c.drawRect(left, top, right, bottom, mColorPaint);
                }
            }
            left = child.getRight() + params.rightMargin - mDividerHeight;
            right = left + mDividerHeight;
            //画分割线
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
            if (mColorPaint != null) {
                c.drawRect(left, top, right, bottom, mColorPaint);
            }
        }
    }

    public void drawHorizontalDivider(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            final int left = child.getLeft() - params.leftMargin - mDividerHeight;
            final int right = child.getRight() + params.rightMargin;

            int top;
            int bottom;

            // 最上面一行
            if ((i / 3) == 0) {
                //当前item最上面的分割线
                top = child.getTop();
                //当前item下面的分割线
                bottom = top + mDividerHeight;
                mDividerDrawable.setBounds(left, top, right, bottom);
                mDividerDrawable.draw(c);
                if (mColorPaint != null) {
                    c.drawRect(left, top, right, bottom, mColorPaint);
                }
            }
            top = child.getBottom() + params.bottomMargin;
            bottom = top + mDividerHeight;
            //画分割线
            mDividerDrawable.setBounds(left, top, right, bottom);
            mDividerDrawable.draw(c);
            if (mColorPaint != null) {
                c.drawRect(left, top, right, bottom, mColorPaint);
            }
        }
    }

}
