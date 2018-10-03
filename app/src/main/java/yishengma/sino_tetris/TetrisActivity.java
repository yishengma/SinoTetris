package yishengma.sino_tetris;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import yishengma.CharacterBlock;

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

    private TetrisAdapter mTetrisAdapter;
    private CharacterBlock[][] mCharacterBlocks;
    private Timer mTimer;
    private int mLastRow;
    private int mLastColumn;
    private int mOffsetRow;
    private int mOffsetColumn;
    private TimerHandler mHandler;
    private int mHeight[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);
        ButterKnife.bind(this);
        init();

    }


    private void init() {

        mCharacterBlocks = new CharacterBlock[11][6];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                mCharacterBlocks[i][j] = new CharacterBlock(true);

            }
        }
        mTetrisAdapter = new TetrisAdapter(TetrisActivity.this, mCharacterBlocks);
        mGvGame.setAdapter(mTetrisAdapter);

        mOffsetColumn = 3;
        mOffsetRow = -1;
        mHeight = new int[6];
        for (int i = 0; i < 6; i++) {
            mHeight[i] = 0;
        }
        mHandler = new TimerHandler();
        mTimer = new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0);
            }
        }, 0, 800);

    }

    @OnClick({R.id.btn_left, R.id.btn_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_left:
                if (mOffsetColumn!=0){
                    mOffsetColumn--;

                }
                mHandler.sendEmptyMessage(1);
                break;
            case R.id.btn_right:
                if (mOffsetColumn!=5){
                    mOffsetColumn++;
                }
                mHandler.sendEmptyMessage(1);
                break;
        }
    }

    private class TimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 0) {

                if (mOffsetRow < 11 - mHeight[mOffsetColumn]){

                    mOffsetRow++;
                    if (mOffsetRow != 0) {
                        mCharacterBlocks[mLastRow][mLastColumn].setEmpty(true);
                        mCharacterBlocks[mLastRow][mLastColumn].setText("");
                    }
                    mCharacterBlocks[mOffsetRow][mOffsetColumn].setEmpty(false);
                    mCharacterBlocks[mOffsetRow][mOffsetColumn].setText("子");

                    mLastRow = mOffsetRow;
                    mLastColumn = mOffsetColumn;




                }else {
                    mOffsetRow=0;
                    mHeight[mOffsetColumn]++;

                }



            }else {

                if (mOffsetRow != 0) {
                    mCharacterBlocks[mLastRow][mLastColumn].setEmpty(true);
                    mCharacterBlocks[mLastRow][mLastColumn].setText("");
                }

                mCharacterBlocks[mOffsetRow][mOffsetColumn].setEmpty(false);
                mCharacterBlocks[mOffsetRow][mOffsetColumn].setText("子");

                mLastRow = mOffsetRow;
                mLastColumn = mOffsetColumn;
            }

            mTetrisAdapter.notifyDataSetChanged();
        }
    }

}
