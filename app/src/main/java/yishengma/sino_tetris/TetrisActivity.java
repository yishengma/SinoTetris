package yishengma.sino_tetris;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yishengma.bean.BlockView;
import yishengma.utils.DensityUtil;

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
    private TetrisAdapter mTetrisAdapter;
    private int[] mHeight;
    private BlockView mCurrentBlockView;
    private BlockView[][] mBlockViews;
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


    }


    private void init() {


        mTetrisAdapter = new TetrisAdapter(TetrisActivity.this, 11 * 6);
        mGvGame.setAdapter(mTetrisAdapter);
        mBlockViews = new BlockView[11][6];
        mHeight = new int[]{0, 0, 0, 0, 0, 0};
        mLinearInterpolator = new LinearInterpolator();
        generateBlock();
    }

    private void generateBlock() {
        mCurrentBlockView = (BlockView) LayoutInflater.from(TetrisActivity.this)
                .inflate(R.layout.view_block, mRvGame, false);
        mRvGame.addView(mCurrentBlockView);
        mCurrentBlockView.bringToFront();
        mCurrentBlockView.setRow(0).setColumn(3);
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
                if (mBtnLeft.isEnabled() && col != 0 && row + mHeight[col - 1] < 11) {
                    mIsLeftMove = true;
                    mBtnLeft.setEnabled(false);
                    mBtnRight.setEnabled(false);
                }
                break;
            case R.id.btn_right:

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
        mCurrentBlockView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mObjectAnimator = ObjectAnimator.ofFloat(mCurrentBlockView, "translationY", DensityUtil.dip2px(this, (mCurrentBlockView.getRow()) * 40));
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setRepeatCount(0);
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mCurrentBlockView.setLayerType(View.LAYER_TYPE_NONE, null);

                if (mIsLeftMove) {
                    mIsLeftMove = false;
                    horizonAnimation(mCurrentBlockView.getColumn() - 1);
                    return;
                }
                if (mIsRightMove) {
                    mIsRightMove = false;
                    horizonAnimation(mCurrentBlockView.getColumn() + 1);
                    return;
                }

                if (mCurrentBlockView.getRow() + 1 + mHeight[mCurrentBlockView.getColumn()] >= 11) {
                    mHeight[mCurrentBlockView.getColumn()]++;
                    generateBlock();
                    return;
                }



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
        mCurrentBlockView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mBtnRight.setEnabled(true);
                mBtnLeft.setEnabled(true);
                mCurrentBlockView.setLayerType(View.LAYER_TYPE_NONE, null);
                if (mCurrentBlockView.getRow() + 1 + mHeight[mCurrentBlockView.getColumn()] >= 11) {
                    mHeight[mCurrentBlockView.getColumn()]++;
                    generateBlock();
                    return;
                }

                verticalAnimation();
            }
        });

        mObjectAnimator.start();
    }

}
