package cn.monica.missionimpossible.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.myinterface.OnFinishLoadRecord;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.SemaphoreUtil;


public class RecordManager {
    private static RecordManager recordManager = new RecordManager();
    private static List<RecordDatabase> recordDatabases = new ArrayList<>();
    private static List<RecordDatabase> recordRemindDatabases = new ArrayList<>();
    private static RecordDatabase remainData;
    public void Update(final OnFinishLoadRecord onFinishLoadRecord)
    {
        new Thread(){
            @Override
            public void run() {
               if( RecordDatabase.count(RecordDatabase.class)<=0) return;
                recordDatabases = RecordDatabase.listAll(RecordDatabase.class);
                recordRemindDatabases.clear();
                for(RecordDatabase recordDatabase:recordDatabases)
                {
//                    &&CalenderUtil.getInstance().getTimeByDate(recordDatabase.getRemain_time())<
//                            CalenderUtil.getInstance().getDayFromOriginal()
                    if(recordDatabase.getStep() != 2&&
                            recordDatabase.getRemind_times()==0&&
                            recordDatabase.getAlarm() !=2
                           )
                        recordRemindDatabases.add(recordDatabase);
                }
                sort(recordRemindDatabases);
                if(recordRemindDatabases.size()>0)
                    setRemainData(recordRemindDatabases.get(0));
                onFinishLoadRecord.onFinish();
            }
        }.start();

    }
    public List<RecordDatabase> getRemindDatabases()
    {
        return recordRemindDatabases;
    }
    public List<RecordDatabase> getAllRecordDatabases() {
        return recordDatabases;
    }
    public void setRemainData(RecordDatabase temp)
    {
        try {
            SemaphoreUtil.getInstance().Lock();
            remainData = temp;
            SemaphoreUtil.getInstance().UnLock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public RecordDatabase getRemainData()
    {
        return remainData;
    }
    public static RecordManager getInstance() {
        return recordManager;
    }
    public void sort(List<RecordDatabase> databases)
    {
        Collections.sort(databases, new Comparator<RecordDatabase>() {
            /*
             * int compare(Person p1, Person p2) 返回一个基本类型的整型，
             * 返回负数表示：p1 小于p2，
             * 返回0 表示：p1和p2相等，
             * 返回正数表示：p1大于p2
             */
            public int compare(RecordDatabase p1, RecordDatabase p2) {
                long p1Time = CalenderUtil.getInstance().getTimeByDate(p1.getRemain_time());
                long p2Time = CalenderUtil.getInstance() .getTimeByDate(p2.getRemain_time());
                if (p1Time > p2Time) {
                    return 1;
                }
                if (p1Time == p2Time) {
                    return 0;
                }
                return -1;
            }
        });
    }
    public void Remind(RecordDatabase remindData)
    {
        remindData.setRemind_times(remindData.getRemind_times()+1);
        remindData.save();
    }
}
