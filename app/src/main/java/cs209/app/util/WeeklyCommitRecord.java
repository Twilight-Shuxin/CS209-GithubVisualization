package cs209.app.util;

public class WeeklyCommitRecord {
    String name;
    int value;
    public WeeklyCommitRecord(String weekday, int cnt) {
        this.name = weekday;
        this.value = cnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
