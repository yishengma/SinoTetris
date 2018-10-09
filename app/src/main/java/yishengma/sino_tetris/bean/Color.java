package yishengma.sino_tetris.bean;

/**
 *
 * Created by PirateHat on 18-10-5.
 */

public class Color {
    private String mColor;
    private String mName;


    public String getName() {
        return mName == null ? "" : mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getColor() {
        return mColor == null ? "" : mColor;
    }

    public void setColor(String color) {
        this.mColor = color;
    }
}
