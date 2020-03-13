package cn.monica.missionimpossible.activity;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import android.view.View;


import com.orm.SugarContext;

import java.util.List;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.myinterface.OnFinishLoadRecord;
import cn.monica.missionimpossible.util.RemindUtil;


public class TestActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
    }
    public void senTextMail(View view) {
        RemindUtil.getInstance().init(getApplicationContext());
        SugarContext.init(getApplicationContext());
        RecordManager.getInstance().Update(new OnFinishLoadRecord() {
            @Override
            public void onFinish() {
                List<RecordDatabase>recordDatabases = RecordManager.getInstance().getAllRecordDatabases();
                RemindUtil.getInstance().Remind(recordDatabases.get(0));
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EmailUtil.autoSendFileMail("Monica","好想我的小章鱼哦！！！！\n mua~~~~~~","1124270948@qq.com", new String[]{"/storage/emulated/0/Tencent/Tim_Images/IMG_20191222_101023.jpg"});
//            }
//        }).start();

            }
        });

    }
}
