package yishengma.sino_tetris.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 * Created by PirateHat on 18-10-8.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String WORLD_DOWN_CENTER_UP = "create table down_center_up(" +
            "id integer primary key autoincrement, " +
            "down text," +
            "center text" +
            "up text," +
            "result text)";
    private static final String WORLD_DOWN_UP_RIGHT = "create table down_up_left(" +
            "id integer primary key autoincrement, " +
            "down text," +
            "up text," +
            "left text," +
            "result text)";
    private static final String WORLD_DOWN_UP = "create table down_up(" +
            "id integer primary key autoincrement, " +
            "down text," +
            "up text," +
            "result text)";
    private static final String WORLD_LEFT_CENTER_RIGHT = "create table left_center_right(" +
            "id integer primary key autoincrement, " +
            "left text," +
            "center text" +
            "right text," +
            "result text)";
    private static final String WORLD_LEFT_DOWN_UP = "create table left_down_up(" +
            "id integer primary key autoincrement, " +
            "left text," +
            "down text," +
            "up text," +
            "result text)";

    private static final String WORLD_LEFT_RIGHT = "create table left_right(" +
            "id integer primary key autoincrement, " +
            "left text," +
            "right text," +
            "result text)";

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(WORLD_DOWN_CENTER_UP);
        db.execSQL(WORLD_DOWN_UP_RIGHT);
        db.execSQL(WORLD_DOWN_UP);
        db.execSQL(WORLD_LEFT_CENTER_RIGHT);
        db.execSQL(WORLD_LEFT_DOWN_UP);
        db.execSQL(WORLD_LEFT_RIGHT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
