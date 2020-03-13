package cn.monica.missionimpossible.util;


import android.content.Context;

import java.io.File;

import cn.monica.missionimpossible.database.RecordDatabase;

public class RemindUtil {
    private static RemindUtil remindUtil = new RemindUtil();
    private Context context;

    public static RemindUtil getInstance() {
        return remindUtil;
    }
    public  void init(Context context){this.context = context;}
    public void Remind(int alarm, RecordDatabase remindData)
    {
        switch (alarm)
        {
            case 0:
                setEmail(remindData);
                break;
            case 1:
                break;
        }

    }

    public void setEmail(final RecordDatabase remindData) {
        File file = new File(context.getFilesDir(), remindData.getName() + ContentValueUtil.REMARKS);
        final String des = FileUtil.readFile(file);

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
