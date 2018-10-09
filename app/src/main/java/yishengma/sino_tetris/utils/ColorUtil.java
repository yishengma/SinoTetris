package yishengma.sino_tetris.utils;

import android.content.res.Resources;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import yishengma.sino_tetris.bean.Color;


/**
 * Created by PirateHat on 18-10-5.
 */

public class ColorUtil {
    private static ColorUtil sColorUtil;
    private static final String TAG = "ColorUtil";
    private static List<Color> mAllColors;

    private ColorUtil(Resources resources) {
        try {
            mAllColors = pull2xml(resources.getAssets().open("colors.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void init(Resources resources) {
        if (sColorUtil == null) {
            sColorUtil = new ColorUtil(resources);

        }
    }


    public static Color getColor() {
        if (sColorUtil == null) {
            throw new NullPointerException("ColorUtil has not init! ");
        }
        int size = mAllColors.size();
        int index = (int) (Math.random() * size);
        Color color = mAllColors.get(index);
        mAllColors.remove(color);
        return color;
    }

    public static void recyclerColor(Color color) {
        if (sColorUtil == null) {
            throw new NullPointerException("ColorUtil has not init! ");
        }
        if (mAllColors.contains(color)) {
            return;
        }
        mAllColors.add(color);
    }

    private static List<Color> pull2xml(InputStream is) throws Exception {
        List<Color> list = null;
        //创建xmlPull解析器
        XmlPullParser parser = Xml.newPullParser();
        ///初始化xmlPull解析器
        parser.setInput(is, "utf-8");
        //读取文件的类型
        int type = parser.getEventType();
        //无限判断文件类型进行读取
        while (type != XmlPullParser.END_DOCUMENT) {
            Color color = new Color();
            switch (type) {
                //开始标签
                case XmlPullParser.START_TAG:
                    if ("colors".equals(parser.getName())) {
                        list = new ArrayList<>();
                    } else if ("color".equals(parser.getName())) {

                        //获取name属性
                        String name = parser.getAttributeValue(null, "name");
                        color.setName(name);
                        //colorStr
                        String colorStr = parser.nextText();
                        color.setColor(colorStr);
                        assert list != null;
                        list.add(color);
                    }
                    break;
                //结束标签
                case XmlPullParser.END_TAG:

                    break;
            }
            //继续往下读取标签类型
            type = parser.next();
        }
        return list;
    }


}
