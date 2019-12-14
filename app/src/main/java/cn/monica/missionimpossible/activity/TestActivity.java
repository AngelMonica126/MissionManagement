package cn.monica.missionimpossible.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.adapter.RecordExpandableAdapter;

public class TestActivity extends AppCompatActivity {
    private String[] groups={"好友","同学"};
    private String[][] childs={{"Tom","Jerry","Jeck"},{"XY","WX","YH"}};
    android.widget.ExpandableListView ExpandableListView;
    RecordExpandableAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        ExpandableListView= (ExpandableListView) findViewById(R.id.expand_listview);
        adapter=new RecordExpandableAdapter(getBaseContext(),groups,childs);
        ExpandableListView.setAdapter(adapter);
        //设置子项布局监听
        ExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(TestActivity.this,
                        "当前位置"+childs[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

}
