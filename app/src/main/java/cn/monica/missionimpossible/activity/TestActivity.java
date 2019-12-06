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
import cn.monica.missionimpossible.bean.TitleViewStruct;
import cn.monica.missionimpossible.bean.TitleViewType;
import cn.monica.missionimpossible.myinterface.OnDatePickerDialogInterface;
import cn.monica.missionimpossible.myinterface.OnTimePickerDialogInterface;
import cn.monica.missionimpossible.myinterface.OnTitleViewDeletelistener;
import cn.monica.missionimpossible.util.DialogHelper;
import cn.monica.missionimpossible.util.ToastUtil;
import cn.monica.missionimpossible.view.TitleShowView;
import cn.monica.missionimpossible.view.TitleView;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class TestActivity extends ActionBarActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_view_fragment);
//        titleView = new TitleView(this, this,"Monica");
//        linearLayout.addView(titleView);
//
//        titleView.setType(TitleViewType.TimePicker);
//        button.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                Log.e("douhua",titleView.getInfo().getInfo());
//            }
//        });

    }

}
