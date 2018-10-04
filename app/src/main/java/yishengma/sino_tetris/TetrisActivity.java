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
    private TetrisHandler mHandler;
    private volatile boolean mIsVertical;
    private volatile boolean mIsThreadCancel;
    private volatile boolean mIsHorizon;
    private int mHorizon;



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
        mHandler = new TetrisHandler();
        mIsVertical = true;
        mIsHorizon = false;
        mIsThreadCancel = false;
        mHeight = new int[]{0, 0, 0, 0, 0, 0};
        generateBlock();
    }

    private void generateBlock() {
        mCurrentBlockView = (BlockView) LayoutInflater.from(TetrisActivity.this)
                .inflate(R.layout.view_block, mRvGame, false);
        mRvGame.addView(mCurrentBlockView);
        mCurrentBlockView.bringToFront();
        mCurrentBlockView.setRow(0).setColumn(3);
        if (mHeight[3]==10){
            return;
        }
        mIsVertical = true;
        mIsHorizon = false;
        mIsThreadCancel = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsThreadCancel) {
                    if (mIsVertical&&!mIsHorizon) {
                        mIsVertical = false;
                        mHandler.sendEmptyMessage(0);
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!mIsThreadCancel) {
                    if (!mIsVertical&& mIsHorizon) {
                        mIsHorizon = false;
                        mHandler.sendEmptyMessage(1);
                    }
                }
            }
        }).start();

    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        int row = mCurrentBlockView.getRow();
        int col = mCurrentBlockView.getColumn();
        switch (view.getId()) {
            case R.id.btn_left:
                if (row!=0&&row + mHeight[row-1] < 11){
                    mHorizon = row-1;
                    mIsVertical = false;
                    mIsHorizon = true;
                }
            case R.id.btn_right:
                if (row!=5&&row + mHeight[row+1] < 11){
                    mHorizon = row+1;
                    mIsVertical = false;
                    mIsHorizon = true;
                }
                break;
        }
    }

    private ObjectAnimator verticalAnimation(BlockView view) {
        int i = view.getRow(); // i 是这位置的
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", DensityUtil.dip2px(this, (i + 1) * 40));
        animator.setDuration(300);
        animator.setRepeatCount(0);
        animator.setInterpolator(new LinearInterpolator());
        return animator;


    }

    private ObjectAnimator horizonAnimation(BlockView view, int i) {  // i 是下一个位置的
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationX", DensityUtil.dip2px(this, (i - 3) * 40));
        animator.setDuration(100);
        animator.setRepeatCount(0);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    private class TetrisHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    ObjectAnimator verticalAnimator = verticalAnimation(mCurrentBlockView);
                    verticalAnimator.start();
                    verticalAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mCurrentBlockView.setRow(mCurrentBlockView.getRow() + 1);
                            if (mCurrentBlockView.getRow()+ 1 + mHeight[mCurrentBlockView.getColumn()] ==11){
                                mHeight[mCurrentBlockView.getColumn()]++;
                                mIsThreadCancel = true;
                                generateBlock();
                            }else if (mIsHorizon&&!mIsVertical){


                            }else {
                                mIsVertical = true;
                                mIsHorizon = false;
                            }


                        }
                    });
                    break;
                case 1:
                    Log.e(TAG, "handleMessage: 1" );
                    ObjectAnimator animator = horizonAnimation(mCurrentBlockView,mHorizon);
                    animator.start();
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mCurrentBlockView.setColumn(mCurrentBlockView.getRow() + 1);
                            if (mCurrentBlockView.getRow()+ 1 + mHeight[mCurrentBlockView.getColumn()] ==11){
                                mHeight[mCurrentBlockView.getColumn()]++;
                                mIsThreadCancel = true;
                                generateBlock();
                            }else {
                                mIsVertical = true;
                                mIsHorizon = false;
                            }
                        }
                    });
                    break;


            }
        }

    }
}
