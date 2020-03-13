package cn.monica.missionimpossible.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.RemindUtil;
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
//                        SemaphoreUtil.getInstance().Lock();
                        RecordDatabase remainTime = RecordManager.getInstance().getRemainData();
                        if(remainTime!=null&&CalenderUtil.getInstance().getTimeByDate(remainTime.getRemain_time())>CalenderUtil.getInstance().getDayFromOriginal())
                        {
                            Log.e("monica",12+"Monica");
                            RemindUtil.getInstance().Remind(0,remainTime);
                           // RemindUtil.getInstance().setEmail(remainTime);

                       //     stopSelf();
//                            RecordManager.getInstance().Update();
                        }
//                        SemaphoreUtil.getInstance().UnLock();
                     //   Log.e("monica",12+"");
                        sleep(100000);
                    }
                } catch (Exception e) {
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
