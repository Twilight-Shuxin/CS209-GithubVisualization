package cs209.app.util;

public class ReleaseIntervalRecord {
    int releaseId, commitCnt;

    public ReleaseIntervalRecord(int releaseId, int commitCnt) {
        this.releaseId = releaseId;
        this.commitCnt = commitCnt;
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
}
