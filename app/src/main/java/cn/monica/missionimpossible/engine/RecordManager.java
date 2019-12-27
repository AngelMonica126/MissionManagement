package cn.monica.missionimpossible.engine;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import cn.monica.missionimpossible.bean.RecordDatabase;
import cn.monica.missionimpossible.util.CalenderUtil;


public class RecordManager {
    private static RecordManager recordManager = new RecordManager();
    private static List<RecordDatabase> recordDatabases;
    private int nowday= CalenderUtil.getInstance().getDayFromOriginal();;
    public List<RecordDatabase> getRecordDatabases() {
        Update();
        return recordDatabases;
    }
    public void Update(){ recordDatabases = RecordDatabase.listAll(RecordDatabase.class); sort();
    }
    public static RecordManager getInstance() {
        return recordManager;
    }
    public void sort()
    {
        Collections.sort(recordDatabases, new Comparator<RecordDatabase>() {
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

}
