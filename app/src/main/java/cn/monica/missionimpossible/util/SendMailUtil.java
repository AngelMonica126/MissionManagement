package cn.monica.missionimpossible.util;

import android.support.annotation.NonNull;


import java.io.File;

import cn.monica.missionimpossible.bean.MailInfo;
import cn.monica.missionimpossible.engine.MailSender;

public class SendMailUtil {

    public static void send(final File file, String toAdd) {
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendFileMail(mailInfo, file);
            }
        }).start();
    }

    public static void send(String toAdd) {
        final MailInfo mailInfo = creatMail(toAdd);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    @NonNull
    private static MailInfo creatMail(String toAdd) {
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost("smtp.qq.com");//发送方邮箱服务器
        mailInfo.setMailServerPort("465");//发送方邮箱端口号
        mailInfo.setValidate(true);
        mailInfo.setUserName("angelmonica@foxmail.com"); // 发送者邮箱地址
        mailInfo.setPassword("zxcvbnm,./753159");// 发送者邮箱授权码
        mailInfo.setFromAddress("angelmonica@foxmail.com"); // 发送者邮箱
        mailInfo.setToAddress("1071209504@qq.com"); // 接收者邮箱
        mailInfo.setSubject("Android应用测试"); // 邮件主题
        mailInfo.setContent("哈哈"); // 邮件文本
        return mailInfo;
    }

}
