package yishengma.sino_tetris.widget;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import java.util.List;

import yishengma.sino_tetris.bean.Color;
import yishengma.sino_tetris.bean.Gap;
import yishengma.sino_tetris.utils.DensityUtil;

/**
 * Created by PirateHat on 18-10-6.
 */

public class DrawRelativeLayout extends RelativeLayout {
    private Paint mPaint;
    private List<Gap> mGaps;
    private boolean mIsDraw;

    public DrawRelativeLayout(Context context) {
        this(context, null);
    }

    public DrawRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//设置是否抗锯齿;
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(2);//设置画笔宽度
        setWillNotDraw(false);
        mIsDraw = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIsDraw) {
            int size = mGaps.size();
            for (int i = 0; i < size; i++) {
                Gap gap = mGaps.get(i);
                drawPath(canvas, gap.getColor(), gap.getX(), gap.getY());
            }
        }
        super.onDraw(canvas);
    }


    public void update(List<Gap> gaps) {
        mGaps = gaps;
        mIsDraw = true;
        invalidate();
    }

    public void restore() {
        mIsDraw = false;
        mGaps.clear();
        invalidate();
    }

    private void drawPath(Canvas canvas, Color color, float[] x, float y[]) {

        float controlX = (x[2] + x[0])/2;
        float controlY = (y[3] + y[1])/2;

        mPaint.setColor(android.graphics.Color.parseColor(color.getColor()));
        Path mPath = new Path();
        mPath.reset();
        mPath.moveTo(x[0], y[0]);
        mPath.quadTo(controlX, controlY,
                x[1], y[1]);
        mPath.lineTo(x[2], y[2]);
        mPath.quadTo(controlX, controlY,
                x[3], y[3]);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
