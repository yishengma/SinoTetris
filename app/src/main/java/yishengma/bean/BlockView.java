package yishengma.bean;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;


/**
 * Created by PirateHat on 18-10-4.
 */

public class BlockView extends android.support.v7.widget.AppCompatTextView {

    private int mRow;
    private int mColumn;
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
}
