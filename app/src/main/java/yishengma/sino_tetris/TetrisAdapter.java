package yishengma.sino_tetris;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Arrays;
import java.util.List;

import yishengma.CharacterBlock;
import yishengma.utils.ArrayUtil;

/**
 * Created by PirateHat on 18-10-3.
 */

public class TetrisAdapter extends BaseAdapter {
    private Context mContext;
    private CharacterBlock[][] mCharacterBlocks;
    private int mCount;
    private int mNumColumn;
    private int mNumRow;

    private static final String TAG = "TetrisAdapter";

    public TetrisAdapter(Context context,CharacterBlock[][] characterBlocks) {
        mContext = context;
        mCharacterBlocks = characterBlocks;
        mNumRow = characterBlocks.length; //11
        mNumColumn  = characterBlocks[0].length; //6
        mCount = mNumColumn * mNumRow ;


    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        return mCharacterBlocks[ArrayUtil.getRowIndex(position,mNumColumn)][ArrayUtil.getColumnIndex(position,mNumColumn)];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView==null){
            convertView = View.inflate(mContext,R.layout.item_block,null);
            viewHolder = new ViewHolder();
            viewHolder.mBlockText = convertView.findViewById(R.id.tv_block_text);
            convertView.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        CharacterBlock characterBlock = mCharacterBlocks[ArrayUtil.getRowIndex(position,mNumColumn)][ArrayUtil.getColumnIndex(position,mNumColumn)];
        viewHolder.mBlockText.setText(characterBlock.getText());

        return convertView;
    }

    private  class ViewHolder{
         TextView mBlockText;

    }
}
