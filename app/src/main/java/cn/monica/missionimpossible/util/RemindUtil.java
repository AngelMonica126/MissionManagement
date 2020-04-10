package cn.monica.missionimpossible.util;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;

import java.io.File;
import java.io.IOException;

import cn.monica.missionimpossible.activity.MainActivity;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.engine.RecordManager;
import cn.monica.missionimpossible.myinterface.OnRemindActionListener;
import cn.monica.missionimpossible.service.NotifyService;

public class RemindUtil {
    private static RemindUtil remindUtil = new RemindUtil();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Vibrator vib;
    public static RemindUtil getInstance() {
        return remindUtil;
    }
    public void Remind( RecordDatabase remindData,Context context)
    {
        vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        File file = new File(UesrUtil.getInstance().getAlarmPath(),UesrUtil.getInstance().getAlarmFile());
        try {
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (remindData.getAlarm())
        {
            case 0:
                setEmail(remindData, context);
                break;
            case 1:
                setAlarm(remindData,context);
                break;
            case 2:
                setEmail(remindData,context);
                setAlarm(remindData,context);
                break;
        }

    }
    public void setAlarm(final RecordDatabase recordDatabase, final Context context)
    {
        if (!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
        vib.vibrate(new long[]{1000, 1000, 1000, 1000},1);
        DialogHelper.getInstance().createRemindDialog(context, recordDatabase, new OnRemindActionListener() {
            @Override
            public void Confirm() {
                mediaPlayer.stop();
                vib.cancel();
                RecordManager.getInstance().Remind(recordDatabase);
            }

            @Override
            public void Delay() {
                mediaPlayer.stop();
                vib.cancel();
                long remindTime =  CalenderUtil.getInstance().getTimeByDate(recordDatabase.getRemain_time())+10;
                recordDatabase.setRemain_time(CalenderUtil.getInstance().changeToDate((int) remindTime));
                recordDatabase.setRemind_times(0);
                RecordManager.getInstance().Add(recordDatabase);

            }

            @Override
            public void View() {
                mediaPlayer.stop();
                vib.cancel();
                RecordManager.getInstance().Remind(recordDatabase);
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("message", true);
                intent.putExtra("record",recordDatabase);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }
    public void setEmail(final RecordDatabase remindData,Context context) {
        File file = new File(context.getFilesDir(), remindData.getName() + ContentValueUtil.REMARKS);
        final String des = FileUtil.readFile(file);
        Log.e("Monica",des);
        File originalFile = new File(context.getFilesDir(), remindData.getName() + ContentValueUtil.ORIGINALPICTURE);
        String originalPath = FileUtil.readFile(originalFile);
        final String[] originalPaths = originalPath.split(ContentValueUtil.DIVIDE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                EmailUtil.autoSendFileMail(remindData.getTitle(),des+"\n mua~~~~~~",UesrUtil.getInstance().getEmail(), null);
            }
        }).start();
        RecordManager.getInstance().Remind(remindData);
    }
}
