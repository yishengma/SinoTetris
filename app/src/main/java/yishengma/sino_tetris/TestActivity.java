package yishengma.sino_tetris;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.SeekBar;

import java.util.ArrayList;


public class TestActivity extends AppCompatActivity {
    private GridView mGridView;
    private BlockAdapter mBlockAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

    }


    private void init(){
        mGridView = findViewById(R.id.tetrisView);
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
}
