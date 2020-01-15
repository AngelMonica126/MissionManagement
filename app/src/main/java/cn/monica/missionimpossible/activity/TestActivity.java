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


import cn.monica.missionimpossible.R;


public class TestActivity extends AppCompatActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
    }
    public void senTextMail(View view) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EmailUtil.autoSendFileMail("Monica","好想我的小章鱼哦！！！！\n mua~~~~~~","1124270948@qq.com", new String[]{"/storage/emulated/0/Tencent/Tim_Images/IMG_20191222_101023.jpg"});
//            }
//        }).start();
    }
}
