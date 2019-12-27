package cn.monica.missionimpossible.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dream on 2018/7/4.
 */
//3600000
public class CalenderUtil {
    private static CalenderUtil calenderUtil = new CalenderUtil();
    private long trans = 60000;
    private SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
    public long getTimeByDate(String num)
    {
        try {
            Date temp =  date.parse(num);
            return temp.getTime();
        } catch(ParseException px) {
            px.printStackTrace();
        }
            return -1;
    }
    public String changeToDate(int createDay) {
        long now =createDay*trans;
        return date.format(now);
    }
}
