package yishengma.sino_tetris.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yishengma.sino_tetris.R;
import yishengma.sino_tetris.adapter.TetrisAdapter;

import yishengma.sino_tetris.bean.Gap;
import yishengma.sino_tetris.utils.ColorUtil;
import yishengma.sino_tetris.utils.DensityUtil;
import yishengma.sino_tetris.utils.MatchUtil;
import yishengma.sino_tetris.utils.WordGenerateUtil;
import yishengma.sino_tetris.widget.BlockView;
import yishengma.sino_tetris.widget.DrawRelativeLayout;


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
    @BindView(R.id.drv_game)
    DrawRelativeLayout mDrvGame;
    @BindView(R.id.btn_pause)
    Button mBtnPause;
    @BindView(R.id.btn_drop)
    Button mBtnDrop;


    private int[] mHeight;
    private BlockView mCurrentBlockView;
    private BlockView[][] mBlockViews;
    private String[][] mBlockText;
    private boolean mIsLeftMove;
    private boolean mIsRightMove;
    private LinearInterpolator mLinearInterpolator;
    private ObjectAnimator mObjectAnimator;
    private SoundPool mSoundPool;
    private HashMap<String, Integer> mMusicMap;
    private boolean mIsDropMove;
    private float mVolume;
    private TetrisDialogFragment mDialogFragment;
    private String mNext; // 下一个方
    private boolean mIsCanHold;
    private int mTotalHeight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        ButterKnife.bind(this);
        init();

    }


    private void init() {
        initSoundPool();
        initView();
        initData();
        ColorUtil.init(getResources());
        initDialog();
        showDialog("开始游戏");


    }

    private void initSoundPool() {
        AudioManager manager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
        if (manager != null) {
            int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int currentVolume = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
            mVolume = currentVolume / (float) maxVolume;
        }
        SoundPool.Builder builder = new SoundPool.Builder();
        //传入音频的数量
        builder.setMaxStreams(3);
        //AudioAttributes是一个封装音频各种属性的类
        AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
        //设置音频流的合适属性
        attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
        builder.setAudioAttributes(attrBuilder.build());
        mSoundPool = builder.build();

        mMusicMap = new HashMap<>();
        mMusicMap.put("hold", mSoundPool.load(this, R.raw.hold, 0));
        mMusicMap.put("launch", mSoundPool.load(this, R.raw.launch, 0));
        mMusicMap.put("merge", mSoundPool.load(this, R.raw.merge, 0));
        mMusicMap.put("move", mSoundPool.load(this, R.raw.move, 0));
        mSoundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                Log.e(TAG, "onLoadComplete: ");
            }
        });

    }

    private void initView() {
        TetrisAdapter tetrisAdapter = new TetrisAdapter(TetrisActivity.this, 11 * 6);
        mGvGame.setAdapter(tetrisAdapter);
    }

    private void initData() {
        mBlockViews = new BlockView[11][6];
        mHeight = new int[]{0, 0, 0, 0, 0, 0};
        mLinearInterpolator = new LinearInterpolator();
        mBlockViews = new BlockView[11][6];
        mBlockText = new String[11][6];
        mNext = WordGenerateUtil.getInstance().getRandomWord();

    }

    private void generateBlock() {

        mCurrentBlockView = (BlockView) LayoutInflater.from(TetrisActivity.this)
                .inflate(R.layout.view_block, mRvGame, false);

        mCurrentBlockView.setText(String.format("%s", mNext));
        mNext = WordGenerateUtil.getInstance().getRandomWord();
        mTvNextText.setText(String.format("%s", mNext));


        mRvGame.addView(mCurrentBlockView);
        mCurrentBlockView.bringToFront();
        mCurrentBlockView.setRow(0).setColumn(3).setColor(ColorUtil.getColor());
        //到达顶部，游戏结束
        if (mCurrentBlockView.getRow() + 1 + mHeight[mCurrentBlockView.getColumn()] >= 11) {
            Log.e(TAG, "generateBlock: ");
            return;
        }
        mIsDropMove = false;
        mBtnDrop.setEnabled(true);
        mIsCanHold = true;
        verticalAnimation();

    }

    @OnClick({R.id.btn_left, R.id.btn_right, R.id.btn_drop, R.id.btn_pause, R.id.tv_hold_text, R.id.tv_hold_tips})
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
            case R.id.btn_drop:
                if (mBtnDrop.isEnabled()) {
                    mIsDropMove = true;
                    mBtnDrop.setEnabled(false);
                }
                break;
            case R.id.btn_pause:
                if (mObjectAnimator != null && !mObjectAnimator.isPaused()) {
                    mObjectAnimator.pause();
                    mBtnPause.setText(R.string.go_on);
                    showDialog("继续游戏");
                    return;
                }

                break;
            case R.id.tv_hold_text:
            case R.id.tv_hold_tips:

                if (!mIsCanHold) {
                    return;
                }
                if (TextUtils.isEmpty(mTvHoldText.getText())) {
                    mTvHoldText.setText(mCurrentBlockView.getText());
                    mCurrentBlockView.setText(String.format("%s", mNext));
                    mNext = WordGenerateUtil.getInstance().getRandomWord();
                    mTvNextText.setText(String.format("%s", mNext));
                } else {
                    CharSequence charSequence = mCurrentBlockView.getText();
                    mCurrentBlockView.setText(mTvHoldText.getText());
                    mTvHoldText.setText(charSequence);

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
                    mIsCanHold = false; //表示不能替换

                    mSoundPool.play(mMusicMap.get("launch"), mVolume, mVolume, 1, 0, 1);

                    mBlockViews[row][col] = mCurrentBlockView;
                    mBlockText[row][col] = String.valueOf(mCurrentBlockView.getText());

                    List<Gap> list = MatchUtil.getsINSTANCE(TetrisActivity.this).match(mBlockText, mBlockViews);
                    if (list.size() != 0) {
                        //removeView();
                        merge(list);
                        return;
                    }

                    generateBlock();
                    return;
                }
                //快速下落
                if (mIsDropMove) {
                    dropAnimation();
                    return;
                }
                //没有任何操作，直接再次下滑
                verticalAnimation();
            }
        });
        mObjectAnimator.start();

    }

    private void horizonAnimation(final int nextCol) {
        mSoundPool.play(mMusicMap.get("move"), mVolume, mVolume, 0, 0, 1);
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
                    mIsCanHold = false; //表示不能替换
                    mHeight[col]++;
                    mSoundPool.play(mMusicMap.get("launch"), mVolume, mVolume, 0, 0, 1);

                    mBlockViews[row][col] = mCurrentBlockView;
                    mBlockText[row][col] = String.valueOf(mCurrentBlockView.getText());

                    List<Gap> list = MatchUtil.getsINSTANCE(TetrisActivity.this).match(mBlockText, mBlockViews);
                    if (list.size() != 0) {
                        //removeView();
                        merge(list);
                        return;
                    }


                    generateBlock();
                    return;
                }
                if (mIsDropMove) {
                    dropAnimation();
                    return;
                }
                //没有到底就继续下落
                verticalAnimation();
            }
        });

        mObjectAnimator.start();
    }

    private void dropAnimation() {
        int height = 10 - mHeight[mCurrentBlockView.getColumn()] - mCurrentBlockView.getRow(); //需要下落的高度
        mCurrentBlockView.setRow(height + mCurrentBlockView.getRow());//设置到底
        mCurrentBlockView.setLayerType(View.LAYER_TYPE_HARDWARE, null); //硬件加速
        mObjectAnimator = ObjectAnimator.ofFloat(mCurrentBlockView, "translationY", DensityUtil.dip2px(this, (mCurrentBlockView.getRow()) * 40));
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setRepeatCount(0);
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                int row = mCurrentBlockView.getRow();
                int col = mCurrentBlockView.getColumn();
                mCurrentBlockView.setLayerType(View.LAYER_TYPE_NONE, null);
                //已经到达底部，记录方块并生成下一个方块
                if (row + 1 + mHeight[col] >= 11) {
                    mIsCanHold = false; //表示不能替换
                    mHeight[col]++;
                    mSoundPool.play(mMusicMap.get("launch"), mVolume, mVolume, 1, 0, 1);
                    mBlockViews[row][col] = mCurrentBlockView;
                    mBlockText[row][col] = String.valueOf(mCurrentBlockView.getText());


                    List<Gap> list = MatchUtil.getsINSTANCE(TetrisActivity.this).match(mBlockText, mBlockViews);
                    if (list.size() != 0) {
                        //removeView();
                        merge(list);
                        return;
                    }

                    generateBlock();

                }
            }
        });
        mObjectAnimator.start();
    }

    private void merge(List<Gap> gaps) {

        if (gaps.size() != 0) {
            mDrvGame.bringToFront();
            mDrvGame.update(gaps);

            mergeAnimation();
        }


    }


    private void removeView() {


        boolean isShake = false;
        boolean[][] shakeBlocks = new boolean[11][6];
        for (int i = 0; i < 6; i++) {
            for (int j = 10; j > -1; j--) {
                BlockView blockView = mBlockViews[j][i];

                String blockText = mBlockText[j][i];
                if (blockView == null) {
                    continue;
                }


                //移除被合并的 View
                if (TextUtils.isEmpty(blockText)) {
                    mHeight[blockView.getColumn()]--;
                    mRvGame.removeView(blockView);
                    mBlockViews[j][i] = null;

                }


            }
        }


        for (int i = 0; i < 6; i++) {
            for (int j = 10; j > -1; j--) {
                BlockView blockView = mBlockViews[j][i];

                String blockText = mBlockText[j][i];
                if (blockView == null) {
                    continue;
                }
                if (!TextUtils.isEmpty(blockText) && !blockText.equals(blockView.getText())) {
                    //这里有个动画效果 弹起一下的滑动...
                    //如果方块上是空白的话才允许抖动
                    mSoundPool.play(mMusicMap.get("merge"), mVolume, mVolume, 0, 0, 1);
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
        mTotalHeight = 0;
        for (int i = 0; i < 6; i++) {
            int height = 0;
            for (int j = 10; j > -1; j--) {
                if (mBlockViews[j][i] == null) {
                    height++; //最后的方块都会null ,所以dropHeight都为默认值 0
                } else {
                    dropHeight[j][i] = height;
                    mTotalHeight += height;
                }
            }
        }
        boolean isdrop = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 10; j > -1; j--) {
                if (dropHeight[j][i] != 0) {
                    isdrop = true;
                    directlyDropAnimation(mBlockViews[j][i], mBlockViews[j][i].getRow() + dropHeight[j][i]);
                }
            }
        }

        if (!isdrop) {
            generateBlock();
        }

    }

    private void mergeAnimation() {
        mObjectAnimator = ObjectAnimator.ofFloat(mGvGame, "alpha", 1.0f, 1.0f);
        mObjectAnimator.setDuration(400);
        mObjectAnimator.setRepeatCount(0);
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mDrvGame.restore();
                removeView();
            }
        });

        mObjectAnimator.start();
    }

    private void directlyDropAnimation(final BlockView view, final int targetRow) {
        view.setRow(view.getRow() + 1);

        mObjectAnimator = ObjectAnimator.ofFloat(view
                , "translationY", DensityUtil.dip2px(this, (view.getRow()) * 40));
        mObjectAnimator.setDuration(500);
        mObjectAnimator.setRepeatCount(0);
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTotalHeight--;//结束的时候才能
                if (view.getRow() != targetRow) {
                    directlyDropAnimation(view, targetRow);
                } else {
                    int row = view.getRow();
                    int col = view.getColumn();
                    mBlockViews[row][col] = view;
                    mBlockText[row][col] = String.valueOf(view.getText());

                    if (mTotalHeight == 0) {
                        Log.e(TAG, "onAnimationEnd: ");
                        List<Gap> list = MatchUtil.getsINSTANCE(TetrisActivity.this).match(mBlockText, mBlockViews);
                        if (list.size() != 0) {
                            merge(list);
                            return;
                        }

                        generateBlock();
                    }
                }
            }
        });
        mObjectAnimator.start();
    }

    private void shakeAnimation(boolean[][] shakeBlock) {
        Queue<ObjectAnimator> queue = new LinkedList<>();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                if (shakeBlock[i][j]) {
                    Log.e(TAG, "shakeAnimation: ");
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
        Log.e(TAG, "executeShakeAnimation: ");
        mObjectAnimator = (ObjectAnimator) queue.poll();
        mObjectAnimator.setDuration(250);
        mObjectAnimator.setRepeatCount(1);
        mObjectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mObjectAnimator.setInterpolator(mLinearInterpolator);
        mObjectAnimator.start();
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //如果还有方块没有动画就继续
                if (!queue.isEmpty()) {
                    executeShakeAnimation(queue);
                } else {
                    //如果所有的抖动了就开始下落
                    dropBlocks();
                }
            }
        });
    }

    private void initDialog() {
        mDialogFragment = new TetrisDialogFragment();
        mDialogFragment.setOnClickListener(new TetrisDialogFragment.OnClickListener() {
            @Override
            public void onClick(String s) {
                switch (s) {
                    case "开始游戏":
                        generateBlock();
                        mDialogFragment.dismiss();
                        break;
                    case "继续游戏":
                        if (mObjectAnimator != null && mObjectAnimator.isPaused()) {
                            mObjectAnimator.resume();
                            mBtnPause.setText(R.string.pause);
                        }
                        mDialogFragment.dismiss();
                        break;

                }
            }
        });
    }

    private void showDialog(String s) {
        mDialogFragment.setOperate(s);
        mDialogFragment.show(getSupportFragmentManager(), "dialog");
    }


}
