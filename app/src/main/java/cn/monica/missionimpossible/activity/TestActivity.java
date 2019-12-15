package cn.monica.missionimpossible.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.serchinastico.coolswitch.CoolSwitch;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.adapter.RecordExpandableAdapter;

public class TestActivity extends AppCompatActivity implements CoolSwitch.AnimationListener {
    private String[] groups={"好友","同学"};
    private String[][] childs={{"Tom","Jerry","Jeck"},{"XY","WX","YH"}};
    android.widget.ExpandableListView ExpandableListView;
    RecordExpandableAdapter adapter;
    private CoolSwitch coolSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        mapViews();
        coolSwitch.addAnimationListener(this);
//        ExpandableListView= (ExpandableListView) findViewById(R.id.expand_listview);
//        adapter=new RecordExpandableAdapter(getBaseContext(),groups,childs);
//        ExpandableListView.setAdapter(adapter);
//        //设置子项布局监听
//        ExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                Toast.makeText(TestActivity.this,
//                        "当前位置"+childs[groupPosition][childPosition], Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });
        // get our folding cell
    }

    @Override
    public void onCheckedAnimationFinished() {
        // Empty
    }

    @Override
    public void onUncheckedAnimationFinished() {
        // Empty
    }

    private void mapViews() {
        coolSwitch = (CoolSwitch) findViewById(R.id.cool_switch_foo);
    }

    @Override
    protected void onDestroy() {
        coolSwitch.removeAnimationListener(this);

        super.onDestroy();
    }
}
