package yishengma.sino_tetris.utils;

import android.text.TextUtils;

/**
 * 汉字的匹配
 * Created by PirateHat on 18-10-8.
 */

public class MatchUtil {

    // up down  left right center


    public static void mathch(String[][] textBlocks) {

        for (int i = 10; i >-1 ; i--) {
            for (int j = 0; j < 6; j++) {

            }
        }
    }

    public static void down$center$up(String[][] textBlocks, int row, int col) {
        if (row - 2 < 0) {
            return ;
        }
        String down = textBlocks[row][col];
        String center = textBlocks[row - 1][col];
        String up = textBlocks[row - 2][col];

        if (TextUtils.isEmpty(up) || TextUtils.isEmpty(center)||TextUtils.isEmpty(down)) {
            return ;
        }

        String result = DataBaseUtil.getDown$center$up(down,center,up);

        //去匹配返回结果


    }
    public static void down$up(String[][] textBlocks,int row , int col){
        if (row-1<0){
            return ;

        }
        String down = textBlocks[row][col];
        String up = textBlocks[row-1][col];

        if (TextUtils.isEmpty(up)||TextUtils.isEmpty(down)){
            return ;
        }

      String result = DataBaseUtil.getDown$up(down,up);


    }
    public static void left$center$right(String[][] textBlocks, int row, int col) {
        if (col + 2 > 5) {
            return ;

        }

        String left = textBlocks[row][col];
        String center =  textBlocks[row][col+1];
        String right =  textBlocks[row][col+2];

        if (TextUtils.isEmpty(right) || TextUtils.isEmpty(center)||TextUtils.isEmpty(left)){
           return ;
        }

        String result = DataBaseUtil.getLeft$center$right(left,center,right);
    }

    public static void left$right(String[][] textBlocks, int row , int col){
        if (col+1>5){
            return ;
        }
        String right = textBlocks[row][col+1];
        String left = textBlocks[row][col];
        if (TextUtils.isEmpty(right)||TextUtils.isEmpty(left)){
            return ;
        }

        String result = DataBaseUtil.getLeft$right(left,right);

    }


    //                up
    //left(row,col ) down
    public static void left$down$up(String[][] textBlocks,int row,int col){
        if (row-1<0||col+1>5){
            return ;
        }
        String left =textBlocks[row][col];
        String down = textBlocks[row-1][col+1];
        String up =  textBlocks[row][col+1];

        if (TextUtils.isEmpty(left)||TextUtils.isEmpty(down)||TextUtils.isEmpty(up)){
            return ;
        }

        String result = DataBaseUtil.getLeft$down$up(left,down,up);

    }


    // up
    // down(row,col) right

     public static void down$up$right(String[][] textBlock,int row ,int col){
        if (row-1<0||col+1>5){
            return ;
        }
        String down = textBlock[row][col];
        String up = textBlock[row-1][col];
        String right  = textBlock[row][col+1];

        if (TextUtils.isEmpty(down)||TextUtils.isEmpty(up)||TextUtils.isEmpty(right)){
            return ;
        }

         String result = DataBaseUtil.getDown$up$right(down,up,right);

     }

}
