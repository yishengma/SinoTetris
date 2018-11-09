package yishengma.sino_tetris.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by PirateHat on 18-10-9.
 */

public class WordGenerateUtil {
   private static WordGenerateUtil sINSTANCE;
   private static List<String> list;
    public static WordGenerateUtil getInstance() {
        if (sINSTANCE==null){
            sINSTANCE = new WordGenerateUtil();
        }
        return sINSTANCE;
    }

    private WordGenerateUtil() {
        list = new ArrayList<>();
        list.add("水");
        list.add("月");
        list.add("木");
        list.add("山");
        list.add("虫");
        list.add("工");
        list.add("口");
        list.add("金");
        list.add("白");
        list.add("巾");
        list.add("一");
        list.add("二");
        list.add("小");
        list.add("大");
        list.add("十");




    }

    public String getRandomWord(){
        int size = list.size();
        int index = (int) (Math.random() * size);
        return list.get(index);
    }
}
