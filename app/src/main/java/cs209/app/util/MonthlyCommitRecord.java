package cs209.app.util;

public class MonthlyCommitRecord {
    int year, month, commitCnt;
    String name;
    int value;

    public MonthlyCommitRecord(int year, int month, int commitCnt) {
        this.year = year;
        this.month = month;
        this.commitCnt = commitCnt;
        name = year + "/" + month;
        value = commitCnt;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCommitCnt() {
        return commitCnt;
    }

    public void setCommitCnt(int commitCnt) {
        this.commitCnt = commitCnt;
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