package cn.monica.missionimpossible.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.monica.missionimpossible.database.RecordDatabase;
import cn.monica.missionimpossible.myinterface.OnFinishDeleteRecord;
import cn.monica.missionimpossible.myinterface.OnFinishLoadRecord;
import cn.monica.missionimpossible.util.CalenderUtil;
import cn.monica.missionimpossible.util.SemaphoreUtil;


public class RecordManager {
    private static RecordManager recordManager = new RecordManager();
    private static List<RecordDatabase> recordDatabases = new ArrayList<>();

    public void Update(final OnFinishLoadRecord onFinishLoadRecord) {
        new Thread() {
            @Override
            public void run() {
                try {
                    SemaphoreUtil.getInstance().Lock();
                    if (RecordDatabase.count(RecordDatabase.class) <= 0) {
                        return;
                    }
                    recordDatabases = RecordDatabase.listAll(RecordDatabase.class);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    SemaphoreUtil.getInstance().UnLock();
                    onFinishLoadRecord.onFinish();
                }
            }
        }.start();

    }
    public void Delete(final RecordDatabase recordDatabase, final OnFinishDeleteRecord onFinishDeleteRecord)
    {
        new Thread() {
            @Override
            public void run() {
                try {
                    SemaphoreUtil.getInstance().Lock();
                    recordDatabases.remove(recordDatabase);
                    recordDatabase.delete();
                    onFinishDeleteRecord.onFinish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    SemaphoreUtil.getInstance().UnLock();
                }
            }
        }.start();
    }

    public List<RecordDatabase> getAllRecordDatabases() {
        return recordDatabases;
    }

    public void Add(final RecordDatabase recordDatabase) {
        new Thread() {
            @Override
            public void run() {
                try {
                    SemaphoreUtil.getInstance().Lock();
                    recordDatabase.save();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SemaphoreUtil.getInstance().UnLock();
                }
            }
        }.start();
    }

    public static RecordManager getInstance() {
        return recordManager;
    }

    public void sort(List<RecordDatabase> databases) {
        Collections.sort(databases, new Comparator<RecordDatabase>() {
            /*
             * int compare(Person p1, Person p2) 返回一个基本类型的整型，
             * 返回负数表示：p1 小于p2，
             * 返回0 表示：p1和p2相等，
             * 返回正数表示：p1大于p2
             */
            public int compare(RecordDatabase p1, RecordDatabase p2) {
                long p1Time = CalenderUtil.getInstance().getTimeByDate(p1.getRemain_time());
                long p2Time = CalenderUtil.getInstance().getTimeByDate(p2.getRemain_time());
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

    public void Remind(final RecordDatabase remindData) {
        new Thread() {
            @Override
            public void run() {
                try {
                    SemaphoreUtil.getInstance().Lock();
                    remindData.setRemind_times(remindData.getRemind_times() + 1);
                    remindData.save();
                } catch (
                        InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    SemaphoreUtil.getInstance().UnLock();
                }
            }
        }.start();
    }
}
