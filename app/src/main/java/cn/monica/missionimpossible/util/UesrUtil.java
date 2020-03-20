package cn.monica.missionimpossible.util;


public class UesrUtil {
    private static UesrUtil uesrUtil = new UesrUtil();
    public static UesrUtil getInstance() {
        return uesrUtil;
    }
    private String email = "1071209504@qq.com";
    private String alarmPath = "/storage/emulated/0";
    private String alarmFile = "Test.flac";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlarmPath() {
        return alarmPath;
    }

    public void setAlarmPath(String alarmPath) {
        this.alarmPath = alarmPath;
    }

    public String getAlarmFile() {
        return alarmFile;
    }

    public void setAlarmFile(String alarmFile) {
        this.alarmFile = alarmFile;
    }
}
