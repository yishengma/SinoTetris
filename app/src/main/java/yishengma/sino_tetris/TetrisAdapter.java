package yishengma.sino_tetris;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 *
 * Created by PirateHat on 18-10-3.
 */

public class TetrisAdapter extends BaseAdapter {
    private Context mContext;
    private int mCount;




    TetrisAdapter(Context context, int count) {
        mContext = context;
        mCount = count;


    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_block, null);
            viewHolder = new ViewHolder();
            viewHolder.mBlankBlock = convertView.findViewById(R.id.tv_block_text);
            convertView.setTag(viewHolder);

        }

        return convertView;
    }

    private class ViewHolder {
        TextView mBlankBlock;

    }
}
