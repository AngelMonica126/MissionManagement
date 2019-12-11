package cn.monica.missionimpossible.bean;

public class ResetTitleMessage {
    String color;
    int topId;
    String title;
    FragmentType type;

    public ResetTitleMessage() {
    }

        public ResetTitleMessage(String color, int topId, String title, FragmentType type) {
        this.color = color;
        this.topId = topId;
        this.title = title;
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getTopId() {
        return topId;
    }

    public void setTopId(int topId) {
        this.topId = topId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FragmentType getType() {
        return type;
    }

    public void setType(FragmentType type) {
        this.type = type;
    }
}
