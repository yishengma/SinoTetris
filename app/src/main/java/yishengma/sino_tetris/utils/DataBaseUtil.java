package yishengma.sino_tetris.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


    public static DataBaseUtil getInstance() {
        if (sDataBaseUtil == null) {
            return sDataBaseUtil = new DataBaseUtil();
        }
        return sDataBaseUtil;
    }

    private DataBaseUtil() {
        DataBaseHelper dataBaseHelper = new DataBaseHelper(App.getsContext(), "words", null, 1);
        mDB = dataBaseHelper.getWritableDatabase();

    }

    public static String getDown$center$up(String down, String center, String up) {
        Cursor cursor = mDB.query("down_center_up", new String[]{"result"},
                "where down = ? and center = ? and up = ? ",
                new String[]{down, center, up},
                null, null, null, null);
        String result="";
        if (cursor.moveToFirst()) {
            do {
                result   =     cursor.getString(cursor.getColumnIndex("result"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static String getDown$up$right(String down, String up, String right) {
        Cursor cursor = mDB.query("down_up_right", new String[]{"result"},
                "where down = ? and up = ? and right = ? ",
                new String[]{down, up, right},
                null, null, null, null);
        String result="";
        if (cursor.moveToFirst()) {
            do {
                result   =     cursor.getString(cursor.getColumnIndex("result"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static String getDown$up(String down, String up) {
        Cursor cursor = mDB.query("down_up", new String[]{"result"},
                "where down = ? and up = ? ",
                new String[]{down, up},
                null, null, null, null);
        String result="";
        if (cursor.moveToFirst()) {
            do {
                result   =     cursor.getString(cursor.getColumnIndex("result"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static String getLeft$center$right(String left, String center, String right) {
        Cursor cursor = mDB.query("left_center_right", new String[]{"result"},
                "where left = ? and center = ? and right = ?",
                new String[]{left, center, right},
                null, null, null, null);
        String result="";
        if (cursor.moveToFirst()) {
            do {
                result   =     cursor.getString(cursor.getColumnIndex("result"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static String getLeft$down$up(String left, String down, String up) {
        Cursor cursor = mDB.query("left_down_up", new String[]{"result"},
                "where left = ? and down = ? and up = ?",
                new String[]{left, down, up},
                null, null, null, null);
        String result="";
        if (cursor.moveToFirst()) {
            do {
                result   =     cursor.getString(cursor.getColumnIndex("result"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public static String getLeft$right(String left, String right) {
        Cursor cursor = mDB.query("left_right", new String[]{"result"},
                "where left = ? and right = ? ",
                new String[]{left, right},
                null, null, null, null);
        String result="";
        if (cursor.moveToFirst()) {
            do {
                result   =     cursor.getString(cursor.getColumnIndex("result"));

            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }


}
