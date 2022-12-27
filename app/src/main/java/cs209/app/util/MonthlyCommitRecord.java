package cs209.app.util;

public class MonthlyCommitRecord {
    int year, month, commit_cnt;

    public MonthlyCommitRecord(int year, int month, int commit_cnt) {
        this.year = year;
        this.month = month;
        this.commit_cnt = commit_cnt;
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

    public int getCommit_cnt() {
        return commit_cnt;
    }

    public void setCommit_cnt(int commit_cnt) {
        this.commit_cnt = commit_cnt;
    }
}