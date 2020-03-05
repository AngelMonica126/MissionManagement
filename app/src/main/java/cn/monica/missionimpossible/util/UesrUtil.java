package cn.monica.missionimpossible.util;


import android.content.Context;

import java.io.File;

import cn.monica.missionimpossible.bean.RecordDatabase;

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
