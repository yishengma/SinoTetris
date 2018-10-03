package yishengma;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

import yishengma.sino_tetris.R;

/**
 * Created by PirateHat on 18-10-3.
 */

public class CharacterBlock {
    private String mText;
    private int mBackgroundId;
    private boolean isEmpty;



    public CharacterBlock(boolean isEmpty) {
        this.isEmpty = isEmpty;
        this.mText = "";
        this.mBackgroundId = R.drawable.blank_background;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getText() {
        return mText == null ? "" : mText;
    }


    public void setText(String text) {
        mText = text;
    }

    public int getBackgroundId() {
        return mBackgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        mBackgroundId = backgroundId;
    }
}
