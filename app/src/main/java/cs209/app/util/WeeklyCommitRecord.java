package cs209.app.util;

public class WeeklyCommitRecord {
    int weekday, cnt;
    public WeeklyCommitRecord(int weekday, int cnt) {
        this.weekday = weekday;
        this.cnt = cnt;
    }

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
}
