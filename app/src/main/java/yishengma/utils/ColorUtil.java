package yishengma.utils;

import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import yishengma.bean.Color;


/**
 *
 * Created by PirateHat on 18-10-5.
 */

public class ColorUtil {

    private static final String TAG = "ColorUtil";

    public static void choseColor(Resources resources) {

        try {
            List<Color> colors = pull2xml(resources.getAssets().open("colors.xml"));
            for (Color c : colors) {
                Log.e(TAG, "choseColor: " + c.getColor());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                    }else if ("color".equals(parser.getName())) {

                        //获取name属性
                        String name = parser.getAttributeValue(null,"name");
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
