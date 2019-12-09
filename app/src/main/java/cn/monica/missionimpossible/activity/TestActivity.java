package cn.monica.missionimpossible.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gigamole.navigationtabstrip.NavigationTabStrip;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;
import cn.monica.missionimpossible.util.DialogHelper;

import static android.view.View.*;

public class TestActivity extends ActionBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        NavigationTabStrip strip = (NavigationTabStrip) findViewById(R.id.strip);
        strip.setTabIndex(0);
        strip.setOnTabStripSelectedIndexListener(new NavigationTabStrip.OnTabStripSelectedIndexListener() {
            @Override
            public void onStartTabSelected(String title, int index) {
                Log.e("douhua",index+"");
            }

            @Override
            public void onEndTabSelected(String title, int index) {
                Log.e("douhua",index+"");
            }
        });
//        titleView = new TitleView(this, this,"Monica");
//        linearLayout.addView(titleView);
//
//        titleView.setType(TitleViewType.TimePicker);
//        Button button = (Button) findViewById(R.id.bt);
//        button.setOnClickListener(new OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                DialogHelper.getInstance().createTimePickerDialog(TestActivity.this, new OnTimePickerDialogInterface() {
//                    @Override
//                    public void Save(int year, int mouth, int day, int hours, int minutes) {
//                        Log.e("douhua","year"+year+"mouth"+mouth+"day"+day+"hours"+hours+"minutes"+minutes);
//                    }
//                });
//            }
//        });

    }

}
