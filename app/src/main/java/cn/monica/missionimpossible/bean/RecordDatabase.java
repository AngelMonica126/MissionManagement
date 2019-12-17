package cn.monica.missionimpossible.bean;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

/**
 * Created by dream on 2018/7/4.
 */

public class RecordDatabase extends SugarRecord implements Serializable {
    @Unique
    private String name;
    private String title;
    private String remain_time;
    private String deadline;
    private int begin_time;
    private int priority;
    private int step;
    public RecordDatabase() {
    }

    public RecordDatabase(String name, String title, String remain_time, String deadline, int begin_time, int priority, int step) {
        this.name = name;
        this.title = title;
        this.remain_time = remain_time;
        this.deadline = deadline;
        this.begin_time = begin_time;
        this.priority = priority;
        this.step = step;
    }

    public int getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(int begin_time) {
        this.begin_time = begin_time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemain_time() {
        return remain_time;
    }

    public void setRemain_time(String remain_time) {
        this.remain_time = remain_time;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "id:" + getId() + " name:" +
                name + " title:" + title ;

    }
}
