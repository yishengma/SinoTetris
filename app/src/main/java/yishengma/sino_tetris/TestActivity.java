package yishengma.sino_tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private GridView mGridView;
    private BlockAdapter mBlockAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ArrayList<Integer> mList = new ArrayList<>();
        for(int i=0;i<10;i++){
            for(int j=0;j<15;j++){
                mList.add(0);
            }
        }

        mList.set(149,1);
        mList.set(148,1);

        mGridView = findViewById(R.id.tetrisView);
        mBlockAdapter = new BlockAdapter(this,mList,R.layout.item_adapter);
        mGridView.setAdapter(mBlockAdapter);

    }


    private void init(){
        mGridView = findViewById(R.id.tetrisView);

    }
}
