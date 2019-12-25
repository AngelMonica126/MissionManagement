package cn.monica.missionimpossible.activity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
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
