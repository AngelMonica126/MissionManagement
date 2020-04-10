package cn.monica.missionimpossible.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import cn.monica.missionimpossible.activity.MainActivity;
import cn.monica.missionimpossible.activity.TestActivity;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.myinterface.OnFinishLoadRecord;
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
        new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        List<RecordDatabase> remainData = RecordManager.getInstance().getAllRecordDatabases();
                        if (remainData != null)
                            for (RecordDatabase recordDatabase : remainData) {
                                if (recordDatabase != null && recordDatabase.getStep() != 2 &&
                                        recordDatabase.getRemind_times() == 0 &&
                                        CalenderUtil.getInstance().getTimeByDate(recordDatabase.getRemain_time()) <= CalenderUtil.getInstance().getDayFromOriginal())
                                    RemindUtil.getInstance().Remind(recordDatabase,getApplicationContext());
                            }
                        sleep(60000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.

                start();
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
