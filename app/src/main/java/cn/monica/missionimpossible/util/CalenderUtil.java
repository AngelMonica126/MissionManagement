package cn.monica.missionimpossible.util;

import java.text.SimpleDateFormat;

/**
 * Created by dream on 2018/7/4.
 */
//3600000
public class CalenderUtil {
    private static CalenderUtil calenderUtil = new CalenderUtil();
    private long trans = 60000;
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat createDate = new SimpleDateFormat("yyyy-MM-dd");

    public static CalenderUtil getInstance() {
        return calenderUtil;
    }

    public int getDayFromOriginal() {
        return (int) (System.currentTimeMillis() / trans);
    }

    public String getDateName() {
        long now = System.currentTimeMillis();
        return date.format(now);
    }

    public String changeToDate(int createDay) {
        long now =createDay*trans;
        return date.format(now);
    }
}
