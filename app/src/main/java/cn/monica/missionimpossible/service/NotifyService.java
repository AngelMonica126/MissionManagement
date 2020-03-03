package cn.monica.missionimpossible.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import cn.monica.missionimpossible.bean.RecordDatabase;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.SemaphoreUtil;
import cn.monica.missionimpossible.util.ToastUtil;

public class NotifyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("monica",123+"");
        new Thread(){
            @Override
            public void run() {
                try {
                     while(true)
                    {
                        SemaphoreUtil.getInstance().Lock();
                        RecordDatabase remainTime = RecordManager.getInstance().getRemainData();
                        if(remainTime!=null&&CalenderUtil.getInstance().getDateName().equals(remainTime.getRemain_time()))
                        {
                            ToastUtil.makeToast(getBaseContext(),"Monica");
                        }
                        SemaphoreUtil.getInstance().UnLock();
                        sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
