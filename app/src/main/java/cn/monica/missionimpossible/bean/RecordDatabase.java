package cn.monica.missionimpossible.bean;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

import java.io.Serializable;

/**
 * Created by dream on 2018/7/4.
 */

public class RecordDatabase extends SugarRecord implements Serializable {
    @Unique
    private Long id;
    private String name;
    private String title;
    private String remain_time;
    private String deadline;
    private int Priority;
    private int step;
    public RecordDatabase() {
    }

    public RecordDatabase(Long id, String name, String title, String remain_time, String deadline, int priority, int step) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.remain_time = remain_time;
        this.deadline = deadline;
        Priority = priority;
        this.step = step;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
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
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    @Override
    public String toString() {
        return "id:" + id + " name:" +
                name + " title:" + title ;

    }
}
