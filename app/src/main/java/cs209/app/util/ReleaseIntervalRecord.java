package cs209.app.util;

public class ReleaseIntervalRecord {
    int releaseId, commitCnt;
    String name;
    int value;

    public ReleaseIntervalRecord(int releaseId, int commitCnt) {
        this.releaseId = releaseId;
        this.commitCnt = commitCnt;
        name = Integer.toString(releaseId);
        value = commitCnt;
    }

    public int getReleaseId() {
        return releaseId;
    }

    public void setReleaseId(int releaseId) {
        this.releaseId = releaseId;
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
