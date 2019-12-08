package cn.monica.missionimpossible.bean;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

/**
 * Created by dream on 2018/7/4.
 */

public class ViewDatabase extends SugarRecord implements Serializable {
    @Unique
    private Long id;
    private String name;
    private String info;
    private String title;
    public ViewDatabase() {
    }

    public ViewDatabase(String name, String info, String title) {
        this.name = name;
        this.info = info;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return  "name:" + name+" info:"+info;
    }
}
