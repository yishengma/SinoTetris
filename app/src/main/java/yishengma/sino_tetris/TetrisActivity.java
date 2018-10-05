package yishengma.sino_tetris;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;

import android.animation.ValueAnimator;
import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yishengma.bean.BlockView;
import yishengma.utils.DensityUtil;

import static yishengma.utils.ColorUtil.choseColor;

public class TetrisActivity extends AppCompatActivity {

    @BindView(R.id.tv_hold_text)
    TextView mTvHoldText;
    @BindView(R.id.tv_hold_tips)
    TextView mTvHoldTips;
    @BindView(R.id.gv_game)
    GridView mGvGame;
    @BindView(R.id.tv_next_text)
    TextView mTvNextText;
    @BindView(R.id.tv_score)
    TextView mTvScore;


    private static final String TAG = "TetrisActivity";
    @BindView(R.id.btn_left)
    Button mBtnLeft;
    @BindView(R.id.btn_right)
    Button mBtnRight;
    @BindView(R.id.rv_game)
    RelativeLayout mRvGame;
    private int[] mHeight;
    private BlockView mCurrentBlockView;
    private BlockView[][] mBlockViews;
    private String[][] mBlockText;
    private boolean mIsLeftMove;
    private boolean mIsRightMove;
    private LinearInterpolator mLinearInterpolator;
    private ObjectAnimator mObjectAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        ButterKnife.bind(this);
        init();
        // choseColor(getResources());

    }


    private void init() {

        TetrisAdapter tetrisAdapter = new TetrisAdapter(TetrisActivity.this, 11 * 6);
        mGvGame.setAdapter(tetrisAdapter);
        mBlockViews = new BlockView[11][6];
        mHeight = new int[]{0, 0, 0, 0, 0, 0};
        mLinearInterpolator = new LinearInterpolator();
        mBlockViews = new BlockView[11][6];
        mBlockText = new String[11][6];
        generateBlock();

    }

    private void generateBlock() {
        mCurrentBlockView = (BlockView) LayoutInflater.from(TetrisActivity.this)
                .inflate(R.layout.view_block, mRvGame, false);
        mCurrentBlockView.setText(String.format("%s", Math.random()));
        mRvGame.addView(mCurrentBlockView);
        mCurrentBlockView.bringToFront();
        mCurrentBlockView.setRow(0).setColumn(3);
        //到达顶部，游戏结束
        if (mCurrentBlockView.getRow() + 1 + mHeight[mCurrentBlockView.getColumn()] >= 11) {
            return;
        }
        verticalAnimation();

    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        int row = mCurrentBlockView.getRow();
        int col = mCurrentBlockView.getColumn();

        switch (view.getId()) {
            case R.id.btn_left:
                //最左边，和左边的方块比较高的情况，不能左滑
                if (mBtnLeft.isEnabled() && col != 0 && row + mHeight[col - 1] < 11) {
                    mIsLeftMove = true;
                    mBtnLeft.setEnabled(false);
                    mBtnRight.setEnabled(false);
                }
                break;
            case R.id.btn_right:
                //最右边，和右边的方块比较高的情况，不能右滑
                if (mBtnRight.isEnabled() && col != 5 && row + mHeight[col + 1] < 11) {
                    mIsRightMove = true;
                    mBtnLeft.setEnabled(false);
                    mBtnRight.setEnabled(false);
                }
                break;
        }
    }

    private void verticalAnimation() {
        mCurrentBlockView.setRow(mCurrentBlockView.getRow() + 1);
        mCurrentBlockView.setLayerType(View.LAYER_TYPE_HARDWARE, null); //硬件加速
        mObjectAnimator = ObjectAnimator.ofFloat(mCurrentBlockView, "translationY", DensityUtil.dip2px(this, (mCurrentBlockView.getRow()) * 40));
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setRepeatCount(0);
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCurrentBlockView.setLayerType(View.LAYER_TYPE_NONE, null);
                int row = mCurrentBlockView.getRow();
                int col = mCurrentBlockView.getColumn();
                //有没有按左右滑动，有就先左右滑动
                if (mIsLeftMove) {
                    mIsLeftMove = false;
                    horizonAnimation(col - 1);
                    return;
                }
                if (mIsRightMove) {
                    mIsRightMove = false;
                    horizonAnimation(col + 1);
                    return;
                }

                //没有左右滑动，且已经到达底部，记录方块并生成下一个方块
                if (row + 1 + mHeight[col] >= 11) {
                    mHeight[col]++;
                    mBlockViews[row][col] = mCurrentBlockView;
                    mBlockText[row][col] = String.valueOf(mCurrentBlockView.getText());
                    //测试
                    if (mHeight[col] > 5) {
                        removeView();
                        return;
                    }

                    generateBlock();
                    return;
                }

                //没有任何操作，直接再次下滑
                verticalAnimation();
            }
        });
        mObjectAnimator.start();

    }

    private void horizonAnimation(final int nextCol) {

        mCurrentBlockView.setColumn(nextCol);
        mObjectAnimator = ObjectAnimator.ofFloat(mCurrentBlockView, "translationX", DensityUtil.dip2px(this, (nextCol - 3) * 40));
        mObjectAnimator.setDuration(300);
        mObjectAnimator.setRepeatCount(0);
        mCurrentBlockView.setLayerType(View.LAYER_TYPE_HARDWARE, null); //硬件加速
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBtnRight.setEnabled(true);
                mBtnLeft.setEnabled(true);
                int row = mCurrentBlockView.getRow();
                int col = mCurrentBlockView.getColumn();
                mCurrentBlockView.setLayerType(View.LAYER_TYPE_NONE, null);
                //左右滑动后，如果到底了就不再继续滑动
                if (row + 1 + mHeight[col] >= 11) {
                    mHeight[col]++;
                    mBlockViews[row][col] = mCurrentBlockView;
                    mBlockText[row][col] = String.valueOf(mCurrentBlockView.getText());
                    generateBlock();
                    return;
                }
                //没有到底就继续下落
                verticalAnimation();
            }
        });

        mObjectAnimator.start();
    }


    private void removeView() {
        mBlockText[9][1] = "";
        mBlockText[10][2] = "";
        mBlockText[9][2] = "都";
        boolean isShake = false;
        boolean[][] shakeBlocks = new boolean[11][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 10; j > -1; j--) {
                BlockView blockView = mBlockViews[j][i];
                String blockText = mBlockText[j][i];
                if (blockView == null) {
                    continue;
                }
                if (TextUtils.isEmpty(blockText)) {
                    mHeight[blockView.getColumn()]--;
                    mRvGame.removeView(blockView);
                    mBlockViews[j][i] = null;

                }

                if (!TextUtils.isEmpty(blockText) && !blockText.equals(blockView.getText())) {
                    //这里有个动画效果 弹起一下的滑动...
                    //如果方块上是空白的话才允许抖动
                    int row = blockView.getRow();
                    int col = blockView.getColumn();
                    if (mBlockViews[row - 1][col] == null) {
                        shakeBlocks[row][col] = true;  // 需要抖动的方块的
                        isShake = true;
                    }
                    blockView.setText(blockText);
                }

            }
        }
        //是否需要抖动，需要就先抖动再下滑
        if (isShake) {
            shakeAnimation(shakeBlocks);
            return;
        }


        //不需要就直接下落
        dropBlocks();

    }

    private void dropBlocks() {
        int[][] dropHeight = new int[11][6];
        for (int i = 0; i < 6; i++) {
            int height = 0;
            for (int j = 10; j > -1; j--) {
                if (mBlockViews[j][i] == null) {
                    height++; //最后的方块都会null ,所以dropHeight都为默认值 0
                } else {
                    dropHeight[j][i] = height;

                }
            }
        }

        for (int i = 0; i < 6; i++) {
            for (int j = 10; j > -1; j--) {
                if (dropHeight[j][i] != 0) {
                    directlyDropAnimation(mBlockViews[j][i], mBlockViews[j][i].getRow() + dropHeight[j][i]);
                }
            }
        }


    }


    private void directlyDropAnimation(final BlockView view, final int targetRow) {
        view.setRow(view.getRow() + 1);
        ObjectAnimator animator = ObjectAnimator.ofFloat(view
                , "translationY", DensityUtil.dip2px(this, (view.getRow()) * 40));
        animator.setDuration(500);
        animator.setRepeatCount(0);
        animator.setInterpolator(mLinearInterpolator);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (view.getRow() != targetRow) {
                    directlyDropAnimation(view, targetRow);
                }
            }
        });
        animator.start();
    }

    private void shakeAnimation(boolean[][] shakeBlock) {
        Queue<ObjectAnimator> queue = new LinkedList<>();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                if (shakeBlock[i][j]) {
                    //将需要抖动的动画依次添加到队列中
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mBlockViews[i][j]
                            , "translationY", DensityUtil.dip2px(this, (mBlockViews[i][j].getRow()) * 40) - 10);
                    queue.add(animator);
                }
            }
        }
        //执行动画
        executeShakeAnimation(queue);
    }

    private void executeShakeAnimation(final Queue queue) {

        ObjectAnimator animator = (ObjectAnimator) queue.poll();
        animator.setDuration(250);
        animator.setRepeatCount(1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(mLinearInterpolator);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                   //如果还有方块没有动画就继续
                   if (!queue.isEmpty()){
                       executeShakeAnimation(queue);
                   }else {
                       //如果所有的抖动了就开始下落
                       dropBlocks();
                   }
            }
        });
    }

}
