package yishengma.sino_tetris.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import yishengma.sino_tetris.app.App;
import yishengma.sino_tetris.db.DataBaseHelper;

/**
 *
 *
 * Created by PirateHat on 18-10-8.
 */

public class DataBaseUtil {
    private static DataBaseUtil sDataBaseUtil;
    private static SQLiteDatabase mDB;
    private static final String TAG = "DataBaseUtil";

    public static DataBaseUtil getInstance() {
        if (sDataBaseUtil == null) {
             sDataBaseUtil = new DataBaseUtil();

        }
        return sDataBaseUtil;
    }

    private DataBaseUtil() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(App.getsContext(), "words", null, 1);
        mDB = dataBaseHelper.getWritableDatabase();

    }

     static String getDown$center$up(String down, String center, String up) {
        Cursor cursor = mDB.query("down_center_up", new String[]{"result"},
                "down = ? and center = ? and up = ? ",
                new String[]{down, center, up},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
             //   Log.e(TAG, "getDown$center$up: "+result );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    static String getDown$up$right(String down, String up, String right) {
        Cursor cursor = mDB.query("down_up_right", new String[]{"result"},
                "down = ? and up = ? and right = ? ",
                new String[]{down, up, right},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
              //  Log.e(TAG, "getDown$up$right: "+result );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

     static String getDown$up(String down, String up) {
        Cursor cursor = mDB.query("down_up", new String[]{"result"},
                "down = ? and up = ? ",
                new String[]{down, up},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
               // Log.e(TAG, "getDown$up: "+result );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

     static String getLeft$center$right(String left, String center, String right) {
        Cursor cursor = mDB.query("left_center_right", new String[]{"result"},
                "left = ? and center = ? and right = ?",
                new String[]{left, center, right},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
              //  Log.e(TAG, "getLeft$center$right: "+result );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

     static String getLeft$down$up(String left, String down, String up) {
        Cursor cursor = mDB.query("left_down_up", new String[]{"result"},
                "left = ? and down = ? and up = ?",
                new String[]{left, down, up},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
                Log.e(TAG, "getLeft$down$up: "+result );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

     static String getLeft$right(String left, String right) {
        Cursor cursor = mDB.query("left_right", new String[]{"result"},
                "left = ? and right = ? ",
                new String[]{left, right},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
              //  Log.e(TAG, "getLeft$right: "+result );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    static String getDown$up$left(String down, String up, String left) {
        Cursor cursor = mDB.query("down_up_left", new String[]{"result"},
                "down = ? and up = ? and left = ?",
                new String[]{down, up, left},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
              //  Log.e(TAG, "getDown$up$left: "+result );
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

     static String getRight$down$up(String right, String down, String up) {
        Cursor cursor = mDB.query("right_down_up", new String[]{"result"},
                "right = ? and down = ? and up = ?",
                new String[]{right, down, up},
                null, null, null, null);
        String result = "";
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(cursor.getColumnIndex("result"));
               // Log.e(TAG, "getRight$down$up: "+ result);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }


    public  void insertData(String db_name, ContentValues values){
        mDB.insert(db_name,null,values);
    }


}
