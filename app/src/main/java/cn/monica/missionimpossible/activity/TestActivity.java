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

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.orm.SugarContext;

import java.util.List;

import cn.monica.missionimpossible.R;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.myinterface.OnFinishLoadRecord;
import cn.monica.missionimpossible.service.LongRunningService;
import cn.monica.missionimpossible.util.RemindUtil;


public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext = TestActivity.this;

    private Button startService;
    private Button stopService;
    private EditText time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);
        startService = (Button) findViewById(R.id.start_serice);
        stopService = (Button) findViewById(R.id.stop_serice);
        time = (EditText) findViewById(R.id.time);
        RecordManager.getInstance().init(getApplicationContext());
        SugarContext.init(getApplicationContext());
        new Thread() {
            @Override
            public void run() {
                try {

//                    while (true) {
                    RecordManager.getInstance().Update(new OnFinishLoadRecord() {
                        @Override
                        public void onFinish() {
                            List<RecordDatabase> remainData = RecordManager.getInstance().getAllRecordDatabases();
//                        if (remainData != null)
//                            for (RecordDatabase recordDatabase : remainData) {
//                                if (recordDatabase != null && recordDatabase.getStep() != 2 &&
//                                        recordDatabase.getRemind_times() == 0 &&
//                                        CalenderUtil.getInstance().getTimeByDate(recordDatabase.getRemain_time()) <= CalenderUtil.getInstance().getDayFromOriginal())
//                                    RemindUtil.getInstance().Remind(recordDatabase,getApplicationContext());
//                            }
                            RemindUtil.getInstance().Remind(remainData.get(0),getApplicationContext());
                            Log.e("Monica","Ver");
                        }
                    });
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.

                start();
    }
    public void senTextMail(View view) {
        SugarContext.init(getApplicationContext());
        RecordManager.getInstance().Update(new OnFinishLoadRecord() {
            @Override
            public void onFinish() {
                List<RecordDatabase>recordDatabases = RecordManager.getInstance().getAllRecordDatabases();
                RemindUtil.getInstance().Remind(recordDatabases.get(0),getApplicationContext());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                EmailUtil.autoSendFileMail("Monica","好想我的小章鱼哦！！！！\n mua~~~~~~","1124270948@qq.com", new String[]{"/storage/emulated/0/Tencent/Tim_Images/IMG_20191222_101023.jpg"});
//            }
//        }).start();

            }
        });

    }
    public static int TIME; //记录时间间隔
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_serice:
                Intent startIntent = new Intent(this, LongRunningService.class);
                TIME = Integer.parseInt(time.getText().toString().trim());
                //通过Intent将时间间隔传递给Service
                startIntent.putExtra("Time", TIME);
                Toast.makeText(TestActivity.this, "开始提醒", Toast.LENGTH_SHORT).show();
                startService(startIntent);
                break;
            case R.id.stop_serice:
                Intent stopIntent = new Intent(this, LongRunningService.class);
                Toast.makeText(TestActivity.this, "结束提醒", Toast.LENGTH_SHORT).show();
                stopService(stopIntent);
                break;
        }
    }
}
