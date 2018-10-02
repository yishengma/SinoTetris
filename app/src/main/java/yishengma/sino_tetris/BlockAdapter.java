package yishengma.sino_tetris;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;



public class BlockAdapter extends CommonAdapter {
    Context context;
    List<Integer> mDatas;
    private onClickListener mOnClickListener;
    private static final String TAG = "BlockAdapter";

    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public interface  onClickListener{
        void  onClick(Integer integer);
    }
    public BlockAdapter(Context context, List mDatas, int mLayoutId) {
        super(context, mDatas, mLayoutId);
        this.context = context;
        this.mDatas=mDatas;
    }

    @Override
    public void convert(ViewHolder helper, final Object item,final  int position) {
        ImageView imageView= helper.getView(R.id.adapter_image);

        final Integer integer = (Integer) item;
        if (integer > 0) {
//            imageView.setBackgroundResource(StateFang.color[integer-1]);
            Glide.with(context)
                    .load(StateFang.color[integer - 1])
                    .into(imageView);
//            imageView.setBackgroundColor(Color.BLUE);
        }else {
            imageView.setBackgroundColor(Color.parseColor("#29505B"));
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: "+position);
            }
        });

    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
//        convertView.setLayoutParams(new GridView.LayoutParams(22,22));
//        return convertView;
//    }

}
