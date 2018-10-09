package yishengma.sino_tetris.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import yishengma.sino_tetris.bean.Color;
import yishengma.sino_tetris.bean.Gap;
import yishengma.sino_tetris.widget.BlockView;

/**
 * 汉字的匹配
 * Created by PirateHat on 18-10-8.
 */

public class MatchUtil {
    // up down  left right center
    // 左右


    private MatchBlock[][] matchBlocks;
    private static MatchUtil sINSTANCE;
    private List<Gap> mGaps;
    private Context mContext; //内存泄漏的风险
    private BlockView[][] mBlockViews;
    private static final String TAG = "MatchUtil";


    public static MatchUtil getsINSTANCE(Context context) {
        if (sINSTANCE == null) {
            sINSTANCE = new MatchUtil(context);
        }
        return sINSTANCE;
    }

    private MatchUtil(Context context) {
        mContext = context;
        matchBlocks = new MatchBlock[11][6];
        mBlockViews = new BlockView[11][6];
        mGaps = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                matchBlocks[i][j] = new MatchBlock("");
            }
        }
    }


    public List<Gap> match(String[][] textBlocks, BlockView[][] blockViews) {
        if (sINSTANCE == null) {
            throw new NullPointerException("matchUtil has not init!");
        }
        cloneArrays(textBlocks, blockViews);
        leftToRight(textBlocks);
        rightToLeft(textBlocks);

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                textBlocks[i][j] = matchBlocks[i][j].text;

            }
        }

        List<Gap> list = new ArrayList<>();
        list.addAll(mGaps);
        reset();
        Log.e(TAG, "match: "+list.toString() );
        return list;


    }

    private void reset() {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                matchBlocks[i][j].text = "";
                matchBlocks[i][j].newMark = false;
                mBlockViews[i][j] = null;

            }
        }
        mGaps.clear();
    }

    //从左往右扫描
    private void leftToRight(String[][] textBlocks) {
        for (int i = 10; i > -1; i--) {
            for (int j = 0; j < 6; j++) {
                // 先左边
                left$down$up(textBlocks, i, j);
                left$center$right(textBlocks, i, j);
                left$right(textBlocks, i, j);

                down$up$right(textBlocks, i, j);
                down$center$up(textBlocks, i, j);
                down$up(textBlocks, i, j);


            }
        }
    }

    //从右往左扫描
    private void rightToLeft(String[][] textBlocks) {

        for (int i = 10; i > -1; i--) {
            for (int j = 5; j > -1; j--) {
                right$down$up(textBlocks, i, j);
                down$up$left(textBlocks, i, j);
            }
        }
    }


    private void cloneArrays(String[][] textBlocks, BlockView[][] blockViews) {
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 6; j++) {
                matchBlocks[i][j].text = textBlocks[i][j];
                mBlockViews[i][j] = blockViews[i][j];
            }
        }
    }

    // up         二     大
    // center     大     二
    // down       日     小
    //            春     奈
    // 合并的到 center
    private void down$center$up(String[][] textBlocks, int row, int col) {
        if (row - 2 < 0) {
            return;
        }

        if (matchBlocks[row - 1][col].newMark) {
            return;
        }
        String down = textBlocks[row][col];
        String center = textBlocks[row - 1][col];
        String up = textBlocks[row - 2][col];

        if (TextUtils.isEmpty(up) || TextUtils.isEmpty(center) || TextUtils.isEmpty(down)) {
            return;
        }

        String result = DataBaseUtil.getDown$center$up(down, center, up);
        if (!TextUtils.isEmpty(result)) {

            matchBlocks[row - 1][col].newMark = true;

            matchBlocks[row - 1][col].text = result;
            matchBlocks[row][col].text = "";
            matchBlocks[row - 2][col].text = "";

            if (!matchBlocks[row][col].newMark){
                matchBlocks[row][col].text = "";
            }

            if (!matchBlocks[row][col + 2].newMark){
                matchBlocks[row - 2][col].text = "";
            }

            add3VerticalGap(mBlockViews[row - 2][col], mBlockViews[row - 1][col], mBlockViews[row][col]);
        }


    }

    //up
    //down 合并的 down
    //十     口    一   工    白     一    二  山
    //口     木    二   水    王     白    小  朋
    // 古    呆    三    汞   皇     百    示   崩
    private void down$up(String[][] textBlocks, int row, int col) {
        if (row - 1 < 0) {
            return;

        }
        if (matchBlocks[row][col].newMark||matchBlocks[row-1][col].newMark) {
            return;
        }
        String down = textBlocks[row][col];
        String up = textBlocks[row - 1][col];

        if (TextUtils.isEmpty(up) || TextUtils.isEmpty(down)) {
            return;
        }

        String result = DataBaseUtil.getDown$up(down, up);

        if (!TextUtils.isEmpty(result)) {
            matchBlocks[row][col].newMark = true;

            matchBlocks[row][col].text = result;
            matchBlocks[row - 1][col].text = "";
            if (!matchBlocks[row - 1][col].newMark){
                matchBlocks[row - 1][col].text = "";
            }

            add2VerticalGap(mBlockViews[row - 1][col], mBlockViews[row][col]);

        }


    }

    // up
    // down(row,col) right
    // 合并的 down
   //木       十
    // 木木     口 月
    private void down$up$right(String[][] textBlock, int row, int col) {
        if (row - 1 < 0 || col + 1 > 5) {
            return;
        }

        if (matchBlocks[row][col].newMark) {
            return;
        }
        String down = textBlock[row][col];
        String up = textBlock[row - 1][col];
        String right = textBlock[row][col + 1];

        if (TextUtils.isEmpty(down) || TextUtils.isEmpty(up) || TextUtils.isEmpty(right)) {
            return;
        }


        String result = DataBaseUtil.getDown$up$right(down, up, right);

        if (!TextUtils.isEmpty(result)) {
            matchBlocks[row][col].newMark = true;

            matchBlocks[row][col].text = result;
            matchBlocks[row - 1][col].text = "";
            matchBlocks[row][col + 1].text = "";

            if (!matchBlocks[row - 1][col].newMark){
                matchBlocks[row - 1][col].text = "";
            }

            if (!matchBlocks[row][col + 1].newMark){
                matchBlocks[row][col + 1].text = "";
            }

            add3Gap(mBlockViews[row - 1][col], mBlockViews[row][col], mBlockViews[row][col], mBlockViews[row][col + 1]);
        }

    }

    //left center right
    // 合并到 center
    //木 月  月       水  古  月
    //   棚              湖
    private void left$center$right(String[][] textBlocks, int row, int col) {
        if (col + 2 > 5) {
            return;

        }
        if (matchBlocks[row][col + 1].newMark) {
            return;
        }
        String left = textBlocks[row][col];
        String center = textBlocks[row][col + 1];
        String right = textBlocks[row][col + 2];

        if (TextUtils.isEmpty(right) || TextUtils.isEmpty(center) || TextUtils.isEmpty(left)) {
            return;
        }

        String result = DataBaseUtil.getLeft$center$right(left, center, right);
        if (!TextUtils.isEmpty(result)) {
            matchBlocks[row][col + 1].newMark = true;
            matchBlocks[row][col + 1].text = result;


            if (!matchBlocks[row][col].newMark){
                matchBlocks[row][col].text = "";
            }

            if (!matchBlocks[row][col + 2].newMark){
                matchBlocks[row][col + 2].text = "";
            }

            add3HorizonGap(mBlockViews[row][col], mBlockViews[row][col + 1], mBlockViews[row][col + 2]);
        }
    }

    // left right
    //合并到 right
    // 水 古   水 十  虫 工  木 朋   古月    日 月   金  月   口  十   火 虫  虫 皇   木 月  水 胡   月 月   金  帛   木木
    //   沽      汁    虹      棚    胡       明      钥        叶     烛     蝗      枂     湖     朋       锦      林
    private void left$right(String[][] textBlocks, int row, int col) {
        if (col + 1 > 5) {
            return;
        }
        if (matchBlocks[row][col].newMark||matchBlocks[row][col + 1].newMark) {

            return;
        }
        String right = textBlocks[row][col + 1];
        String left = textBlocks[row][col];
        if (TextUtils.isEmpty(right) || TextUtils.isEmpty(left)) {
            return;
        }
        String result = DataBaseUtil.getLeft$right(left, right);
        if (!TextUtils.isEmpty(result)) {
            matchBlocks[row][col + 1].newMark = true;

            matchBlocks[row][col + 1].text = result;
            matchBlocks[row][col].text = "";
            if (!matchBlocks[row][col].newMark){
                matchBlocks[row][col].text = "";
            }


            add2HorizonGap(mBlockViews[row][col], mBlockViews[row][col + 1]);
        }
    }


    //                up
    //left(row,col ) down
    // 合并的 down
    //   十     木        白       白    山
    //水 口    木木     虫 王    金 巾  月月
    // 沽       森        蝗      锦
    private void left$down$up(String[][] textBlocks, int row, int col) {

        if (row - 1 < 0 || col + 1 > 5) {
            return;
        }

        if (matchBlocks[row - 1][col + 1].newMark) {
            return;
        }


        String left = textBlocks[row][col];
        String down = textBlocks[row][col + 1];
        String up = textBlocks[row-1][col + 1];

        if (TextUtils.isEmpty(left) || TextUtils.isEmpty(down) || TextUtils.isEmpty(up)) {
            return;
        }
        String result = DataBaseUtil.getLeft$down$up(left, down, up);

        if (!TextUtils.isEmpty(result)) {
            matchBlocks[row][col + 1].newMark = true;
            matchBlocks[row ][col + 1].text = result;


            if (!matchBlocks[row][col].newMark){
                matchBlocks[row][col].text = "";
            }
            if (!matchBlocks[row-1][col + 1].newMark){
                matchBlocks[row-1][col + 1].text = "";
            }

            add3Gap(mBlockViews[row-1][col + 1], mBlockViews[row][col + 1], mBlockViews[row][col], mBlockViews[row ][col + 1]);

        }

    }

    //从右往左
    //left up
    //     down
    // 合并的 up
    // 水十    虫 白
    //   口      王
    // 沽
    private void down$up$left(String[][] textBlocks, int row, int col) {
        if (row == 0 || col == 0) {
            return;
        }
        if (matchBlocks[row - 1][col].newMark) {
            return;
        }
        String left = textBlocks[row - 1][col - 1];
        String down = textBlocks[row][col];
        String up = textBlocks[row - 1][col];

        if (TextUtils.isEmpty(left) || TextUtils.isEmpty(down) || TextUtils.isEmpty(up)) {
            return;
        }

        String result = DataBaseUtil.getDown$up$left(down, up, left);
        if (!TextUtils.isEmpty(result)) {
            matchBlocks[row - 1][col].newMark = true;

            matchBlocks[row][col].text = "";
            matchBlocks[row - 1][col].text = result;
            matchBlocks[row - 1][col - 1].text = "";


            if (!matchBlocks[row - 1][col].newMark){
                matchBlocks[row - 1][col].text = "";
            }
            if (!matchBlocks[row - 1][col - 1].newMark){
                matchBlocks[row - 1][col - 1].text = "";
            }



            add3Gap(mBlockViews[row - 1][col], mBlockViews[row][col], mBlockViews[row - 1][col - 1], mBlockViews[row - 1][col]);


        }

    }
     //从右往左
    //up
    //down right
    // 合并到 down
    // 木        十      山
    // 木木      口月    月月
    // 森        胡
    private void right$down$up(String[][] textBlocks, int row, int col) {
        if (row == 0 || col == 0) {
            return;
        }
        if (matchBlocks[row][col - 1].newMark) {
            return;
        }
        String right = textBlocks[row][col];
        String down = textBlocks[row][col - 1];
        String up = textBlocks[row - 1][col - 1];

        if (TextUtils.isEmpty(right) || TextUtils.isEmpty(down) || TextUtils.isEmpty(up)) {
            return;
        }

        String result = DataBaseUtil.getRight$down$up(right, down, up);
        if (!TextUtils.isEmpty(result)) {
            matchBlocks[row][col - 1].newMark = true;

            matchBlocks[row][col].text = "";
            matchBlocks[row][col - 1].text = result;
            matchBlocks[row - 1][col - 1].text = "";

            if (!matchBlocks[row][col].newMark){
                matchBlocks[row][col].text = "";
            }
            if (!matchBlocks[row - 1][col - 1].newMark){
                matchBlocks[row - 1][col - 1].text = "";
            }

            add3Gap(mBlockViews[row - 1][col - 1], mBlockViews[row][col - 1], mBlockViews[row][col - 1], mBlockViews[row][col]);


        }
    }

    private void add2VerticalGap(BlockView up, BlockView down) {

        Color color = ColorUtil.getColor();
        ColorUtil.recyclerColor(up.getColor());
        ColorUtil.recyclerColor(down.getColor());
        Gap gap = new Gap(color, true, up, down, mContext);
        up.setColor(color);
        down.setColor(color);
        mGaps.add(gap);
    }

    private void add2HorizonGap(BlockView left, BlockView right) {

        Color color = ColorUtil.getColor();
        ColorUtil.recyclerColor(left.getColor());
        ColorUtil.recyclerColor(right.getColor());
        Gap gap = new Gap(color, false, left, right, mContext);
        left.setColor(color);
        right.setColor(color);
        mGaps.add(gap);
    }

    private void add3VerticalGap(BlockView up, BlockView center, BlockView down) {
        Color color = ColorUtil.getColor();
        ColorUtil.recyclerColor(up.getColor());
        ColorUtil.recyclerColor(center.getColor());
        ColorUtil.recyclerColor(down.getColor());
        Gap gap = new Gap(color, true, up, center, mContext);
        Gap gap1 = new Gap(color, true, center, down, mContext);

        up.setColor(color);
        center.setColor(color);
        down.setColor(color);
        mGaps.add(gap);
        mGaps.add(gap1);

    }

    private void add3HorizonGap(BlockView left, BlockView center, BlockView right) {
        Color color = ColorUtil.getColor();
        ColorUtil.recyclerColor(left.getColor());
        ColorUtil.recyclerColor(center.getColor());
        ColorUtil.recyclerColor(right.getColor());
        Gap gap = new Gap(color, false, left, center, mContext);
        Gap gap1 = new Gap(color, false, center, right, mContext);

        left.setColor(color);
        center.setColor(color);
        right.setColor(color);

        mGaps.add(gap);
        mGaps.add(gap1);


    }

    // bv1 bv2 左右 ，bv3 bv4 上下
    private void add3Gap(BlockView up, BlockView down, BlockView left, BlockView right) {
        Color color = ColorUtil.getColor();
        ColorUtil.recyclerColor(left.getColor());
        ColorUtil.recyclerColor(right.getColor());
        ColorUtil.recyclerColor(up.getColor());
        ColorUtil.recyclerColor(down.getColor());
        Gap gap = new Gap(color, false, left, right, mContext);
        Gap gap1 = new Gap(color, true, up, down, mContext);

        up.setColor(color);
        down.setColor(color);
        left.setColor(color);
        right.setColor(color);

        mGaps.add(gap);
        mGaps.add(gap1);
    }


    public class MatchBlock {

        boolean newMark;
        String text;

        MatchBlock(String text) {
            this.text = text;
            newMark = false;
        }
    }


}
