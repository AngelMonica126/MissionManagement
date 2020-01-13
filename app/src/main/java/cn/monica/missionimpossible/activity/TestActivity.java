package cn.monica.missionimpossible.activity;
import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.serchinastico.coolswitch.CoolSwitch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.adapter.RecordExpandableAdapter;
import cn.monica.missionimpossible.test.MyScheduledExecutor;
import cn.monica.missionimpossible.util.EmailUtil;
import cn.monica.missionimpossible.util.SendMailUtil;
import cn.monica.missionimpossible.util.ShareUtils;

public class TestActivity extends AppCompatActivity  {
    private EditText sendAddEt, mailAuthCode,
            sendServer, sendPortNumber, toAddEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
//        RemoteViews views = new RemoteViews(getPackageName(),R.layout.notification_content);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(TestActivity.this);
//        builder.setSmallIcon(R.mipmap.ic_home_fill).setContent(views);//设置通知内容
//        NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notifyManager.notify(111, builder.build());
        try {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification notification = new Notification();
//notification.icon=R.drawable.ic_launcher;
            notification.flags = Notification.FLAG_AUTO_CANCEL;
            RemoteViews remoteView = new RemoteViews(getPackageName(), R.layout.notification_content);
            notification.icon=R.drawable.ic_launcher;
            notification.contentView = remoteView;//显示布局
            Intent inte = new Intent(getApplicationContext(), MainActivity.class);
            inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pi = PendingIntent.getActivity(this, 0, inte, 0);
            notification.contentIntent = pi;
            notificationManager.notify(100, notification);
            Log.d("testDIY", "ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
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
