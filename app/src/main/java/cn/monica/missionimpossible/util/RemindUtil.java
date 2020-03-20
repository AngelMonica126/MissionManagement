package cn.monica.missionimpossible.util;


import android.content.Context;
import android.util.Log;

import java.io.File;

import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.engine.RecordManager;

public class RemindUtil {
    private static RemindUtil remindUtil = new RemindUtil();
    private Context context;

    public static RemindUtil getInstance() {
        return remindUtil;
    }
    public  void init(Context context){this.context = context;}
    public void Remind( RecordDatabase remindData)
    {
        switch (remindData.getAlarm())
        {
            case 0:
                setEmail(remindData);
                break;
            case 1:
                break;
        }
        RecordManager.getInstance().Remind(remindData);
    }

    public void setEmail(final RecordDatabase remindData) {
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
    }
}
