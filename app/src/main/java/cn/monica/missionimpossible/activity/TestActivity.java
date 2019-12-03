package cn.monica.missionimpossible.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;
import cn.monica.missionimpossible.util.DialogHelper;
import cn.monica.missionimpossible.util.ToastUtil;
import cn.monica.missionimpossible.view.TitleView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class TestActivity extends ActionBarActivity {
    LinearLayout linearLayout;
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        linearLayout = (LinearLayout) findViewById(R.id.layout);
        button = (Button) findViewById(R.id.bt);
        TitleView titleView = new TitleView(this);
        linearLayout.addView(titleView);
        titleView.setType(1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createDialog();
            }
        });
    }
    private void createDialog() {
        DialogHelper.getInstance().createTimePickerDialog(this, new OnTimePickerDialogInterface() {
            @Override
            public void Save(int hours, int minutes) {
                Log.e("DOUHUA",hours+" "+minutes);
            }
        });
    }
}
