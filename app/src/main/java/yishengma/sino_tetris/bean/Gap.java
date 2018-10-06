package yishengma.sino_tetris.bean;

import android.content.Context;
import android.util.Log;

import java.util.Arrays;

import yishengma.sino_tetris.utils.DensityUtil;
import yishengma.sino_tetris.widget.BlockView;

/**
 * Created by PirateHat on 18-10-6.
 */

public class Gap {
    private float[] mX;
    private float[] mY;
    private Color mColor;

    private static final String TAG = "Gap";
    public Gap(Color color, boolean isVertical, BlockView blockViewOne, BlockView blockViewTwo,Context mContext) {
        mColor = color;
        mX = new float[4];
        mY = new float[4];
        //垂直两个方块之间,第一个是上，第二个是下
        if (isVertical) {
            mX[0] = blockViewTwo.getLeft() + blockViewTwo.getTranslationX()+DensityUtil.dip2px(mContext,5);
            mY[0] = blockViewTwo.getTop() + blockViewTwo.getTranslationY();

            mX[1] = blockViewOne.getLeft() + blockViewOne.getTranslationX()+DensityUtil.dip2px(mContext,5);
            mY[1] = blockViewOne.getBottom() + blockViewOne.getTranslationY();

            mX[2] = blockViewOne.getRight() + blockViewOne.getTranslationX()-DensityUtil.dip2px(mContext,5);
            mY[2] = blockViewOne.getBottom() + blockViewOne.getTranslationY();

            mX[3] = blockViewTwo.getRight() + blockViewTwo.getTranslationX()-DensityUtil.dip2px(mContext,5);
            mY[3] = blockViewTwo.getTop() + blockViewTwo.getTranslationY();

        } else {//第一个是左，第二个是右

            mX[0] = blockViewOne.getRight() + blockViewOne.getTranslationX();
            mY[0] = blockViewOne.getTop() + blockViewOne.getTranslationY()+DensityUtil.dip2px(mContext,5);
            
            mX[1] = blockViewTwo.getLeft() + blockViewTwo.getTranslationX();
            mY[1] = blockViewTwo.getTop() + blockViewTwo.getTranslationY()+DensityUtil.dip2px(mContext,5);

            mX[2] = blockViewTwo.getLeft() + blockViewTwo.getTranslationX();
            mY[2] = blockViewTwo.getBottom() + blockViewTwo.getTranslationY()-DensityUtil.dip2px(mContext,5);

            mX[3] = blockViewOne.getRight() + blockViewOne.getTranslationX();
            mY[3] = blockViewOne.getBottom() + blockViewOne.getTranslationY()-DensityUtil.dip2px(mContext,5);

        }


        Log.e(TAG, "Gap: "+ Arrays.toString(mX));
        Log.e(TAG, "Gap: "+ Arrays.toString(mY));
    }

    public float[] getX() {
        return mX;
    }

    public float[] getY() {
        return mY;
    }

    public Color getColor() {
        return mColor;
    }


}
