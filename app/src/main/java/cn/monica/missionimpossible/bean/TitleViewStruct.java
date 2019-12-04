package cn.monica.missionimpossible.bean;

public class TitleViewStruct {
    TitleViewType type;
    String title;
    String info;

    public TitleViewStruct() {
    }

    public TitleViewStruct(TitleViewType type, String title, String info) {
        this.type = type;
        this.title = title;
        this.info = info;
    }

    public TitleViewType getType() {
        return type;
    }

    public void setType(TitleViewType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
