package yishengma.sino_tetris.widget;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import yishengma.sino_tetris.bean.Color;
import yishengma.sino_tetris.utils.DensityUtil;


/**
 *
 * Created by PirateHat on 18-10-4.
 */

public class BlockView extends android.support.v7.widget.AppCompatTextView {

    private int mRow;
    private int mColumn;
    private Color mColor;
    public BlockView(Context context) {
        super(context);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BlockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getRow() {
        return mRow;
    }

    public BlockView setRow(int row) {
        mRow = row;
        return this;
    }

    public int getColumn() {
        return mColumn;
    }

    public BlockView setColumn(int column) {
        mColumn = column;
        return this;
    }

    public Color getColor() {
        return mColor;
    }

    public BlockView setColor(Color color) {
        mColor = color;
        GradientDrawable gd = new GradientDrawable();//创建drawable
        gd.setColor(android.graphics.Color.parseColor(color.getColor()));
        gd.setCornerRadius(DensityUtil.dip2px(this.getContext(),5));
        this.setBackground(gd);
        return this;
    }
}
