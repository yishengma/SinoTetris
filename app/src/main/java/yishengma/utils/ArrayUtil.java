package yishengma.utils;

import android.util.Log;

/**
 * Created by PirateHat on 18-10-3.
 */

public class ArrayUtil {

    private static final String TAG = "ArrayUtil";
    public static int getRowIndex(int position,int numColumn){
        int a = position % numColumn;
        int i = 0;
        while (i*numColumn + a != position){
            i++;
        }
        return i;

    }

    public static int getColumnIndex(int position,int numColumn){
        return position % numColumn;
    }
}
