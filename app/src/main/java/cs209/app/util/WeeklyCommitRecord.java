package cs209.app.util;

public class WeeklyCommitRecord {
    String weekday;
    int cnt;
    public WeeklyCommitRecord(String weekday, int cnt) {
        this.weekday = weekday;
        this.cnt = cnt;
    }

    public String getWeekday() {
        return weekday;
    }

    public void setWeekday(String weekday) {
        this.weekday = weekday;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
