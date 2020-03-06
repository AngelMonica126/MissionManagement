package cn.monica.missionimpossible.util;


public class UesrUtil {
    private static UesrUtil uesrUtil = new UesrUtil();
    public static UesrUtil getInstance() {
        return uesrUtil;
    }
    private String email = "1071209504@qq.com";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
