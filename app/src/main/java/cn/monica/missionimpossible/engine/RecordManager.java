package cn.monica.missionimpossible.engine;

import java.util.List;
import cn.monica.missionimpossible.bean.RecordDatabase;
import cn.monica.missionimpossible.util.CalenderUtil;


public class RecordManager {
    private static RecordManager recordManager = new RecordManager();
    private static List<RecordDatabase> recordDatabases = RecordDatabase.listAll(RecordDatabase.class);
    private int nowday= CalenderUtil.getInstance().getDayFromOriginal();;
    public List<RecordDatabase> getRecordDatabases() {
        return recordDatabases;
    }
    public void Update(){ recordDatabases = RecordDatabase.listAll(RecordDatabase.class);}
    public static RecordManager getInstance() {
        return recordManager;
    }

}
