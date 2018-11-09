package yishengma.sino_tetris.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.ContentValues;
import android.content.Context;

import yishengma.sino_tetris.utils.DataBaseUtil;

/**
 *
 *
 * Created by PirateHat on 18-10-8.
 */

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();

        down$center$up();
        down$up();
        right$down$up();
        left$down$up();
        down$up$right();
        left$center$right();
        left$down$up();
        left$right();
        down$up$left();
        right$down$up();
    }

    public static Context getsContext() {
        return sContext;
    }


    public void down$center$up() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("down", "日");
        contentValues.put("center", "大");
        contentValues.put("up", "二");
        contentValues.put("result", "春");
        DataBaseUtil.getInstance().insertData("down_center_up", contentValues);
        contentValues.clear();
        contentValues.put("down", "小");
        contentValues.put("center", "二");
        contentValues.put("up", "大");
        contentValues.put("result", "奈");
        DataBaseUtil.getInstance().insertData("down_center_up", contentValues);

    }

    private void down$up() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("down", "口");
        contentValues.put("up", "十");
        contentValues.put("result", "古");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);
        contentValues.clear();

        contentValues.put("down", "木");
        contentValues.put("up", "口");
        contentValues.put("result", "呆");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);

        contentValues.clear();
        contentValues.put("down", "二");
        contentValues.put("up", "一");
        contentValues.put("result", "三");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);

        contentValues.clear();
        contentValues.put("down", "水");
        contentValues.put("up", "工");
        contentValues.put("result", "汞");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);


        contentValues.clear();
        contentValues.put("down", "王");
        contentValues.put("up", "白");
        contentValues.put("result", "皇");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);

        contentValues.clear();
        contentValues.put("down", "白");
        contentValues.put("up", "一");
        contentValues.put("result", "百");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);

        contentValues.clear();
        contentValues.put("down", "小");
        contentValues.put("up", "二");
        contentValues.put("result", "示");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);
        contentValues.clear();
        contentValues.put("down", "朋");
        contentValues.put("up", "山");
        contentValues.put("result", "崩");
        DataBaseUtil.getInstance().insertData("down_up", contentValues);

    }


    public void down$up$right() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("down", "木");
        contentValues.put("up", "木");
        contentValues.put("right", "木");
        contentValues.put("result", "森");
        DataBaseUtil.getInstance().insertData("down_up_right", contentValues);
        contentValues.clear();


        contentValues.put("down", "口");
        contentValues.put("up", "十");
        contentValues.put("right", "月");
        contentValues.put("result", "胡");
        DataBaseUtil.getInstance().insertData("down_up_right", contentValues);


    }


    public void left$center$right() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("left", "木");
        contentValues.put("center", "月");
        contentValues.put("right", "月");
        contentValues.put("result", "棚");
        DataBaseUtil.getInstance().insertData("left_center_right", contentValues);
        contentValues.clear();

        contentValues.put("left", "水");
        contentValues.put("center", "古");
        contentValues.put("right", "月");
        contentValues.put("result", "湖");
        DataBaseUtil.getInstance().insertData("left_center_right", contentValues);

    }

    public void left$right() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("left", "水");
        contentValues.put("right", "古");
        contentValues.put("result", "沽");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();

        contentValues.put("left", "水");
        contentValues.put("right", "十");
        contentValues.put("result", "汁");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();

        contentValues.put("left", "虫");
        contentValues.put("right", "工");
        contentValues.put("result", "虹");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();

        contentValues.put("left", "木");
        contentValues.put("right", "朋");
        contentValues.put("result", "棚");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "水");
        contentValues.put("right", "古");
        contentValues.put("result", "沽");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "古");
        contentValues.put("right", "月");
        contentValues.put("result", "胡");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();

        contentValues.put("left", "日");
        contentValues.put("right", "月");
        contentValues.put("result", "明");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "水");
        contentValues.put("right", "古");
        contentValues.put("result", "沽");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "金");
        contentValues.put("right", "月");
        contentValues.put("result", "钥");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "口");
        contentValues.put("right", "十");
        contentValues.put("result", "叶");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "火");
        contentValues.put("right", "虫");
        contentValues.put("result", "烛");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "虫");
        contentValues.put("right", "皇");
        contentValues.put("result", "蝗");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "木");
        contentValues.put("right", "月");
        contentValues.put("result", "枂");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "水");
        contentValues.put("right", "胡");
        contentValues.put("result", "湖");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "月");
        contentValues.put("right", "月");
        contentValues.put("result", "朋");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "金");
        contentValues.put("right", "帛");
        contentValues.put("result", "锦");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);
        contentValues.clear();


        contentValues.put("left", "木");
        contentValues.put("right", "木");
        contentValues.put("result", "林");
        DataBaseUtil.getInstance().insertData("left_right", contentValues);


    }


    public void left$down$up() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("left", "月");
        contentValues.put("down", "月");
        contentValues.put("up", "山");
        contentValues.put("result", "崩");
        DataBaseUtil.getInstance().insertData("left_down_up", contentValues);
        contentValues.clear();


        contentValues.put("left", "木");
        contentValues.put("down", "木");
        contentValues.put("up", "木");
        contentValues.put("result", "森");
        DataBaseUtil.getInstance().insertData("left_down_up", contentValues);
        contentValues.clear();


        contentValues.put("left", "虫");
        contentValues.put("down", "王");
        contentValues.put("up", "白");
        contentValues.put("result", "蝗");
        DataBaseUtil.getInstance().insertData("left_down_up", contentValues);
        contentValues.clear();


        contentValues.put("left", "金");
        contentValues.put("down", "巾");
        contentValues.put("up", "白");
        contentValues.put("result", "锦");
        DataBaseUtil.getInstance().insertData("left_down_up", contentValues);
        contentValues.clear();


    }


    public void down$up$left() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("left", "水");
        contentValues.put("down", "口");
        contentValues.put("up", "十");
        contentValues.put("result", "沽");
        DataBaseUtil.getInstance().insertData("down_up_left", contentValues);
        contentValues.clear();

        contentValues.put("left", "虫");
        contentValues.put("down", "王");
        contentValues.put("up", "白");
        contentValues.put("result", "蝗");
        DataBaseUtil.getInstance().insertData("down_up_left", contentValues);
        contentValues.clear();
    }

    public void right$down$up() {
        ContentValues contentValues = new ContentValues();
        contentValues.put("right", "月");
        contentValues.put("down", "月");
        contentValues.put("up", "山");
        contentValues.put("result", "崩");
        DataBaseUtil.getInstance().insertData("right_down_up", contentValues);
        contentValues.clear();


        contentValues.put("right", "月");
        contentValues.put("down", "口");
        contentValues.put("up", "十");
        contentValues.put("result", "胡");
        DataBaseUtil.getInstance().insertData("right_down_up", contentValues);

    }


}
