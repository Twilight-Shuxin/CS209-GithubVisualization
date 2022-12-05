package cs209.app.service;

import cs209.app.dto.CommitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;

public interface CommitService {
    public Page<CommitDTO> getCommitByRepoId(int repoId, Pageable page);
    public Page<CommitDTO> getCommitByRepoName(String repoName, Pageable page);

    public Page<CommitDTO> getCommitByRepoIdTimeInterval
            (int repoId, Timestamp start, Timestamp end, Pageable page);
    public Page<CommitDTO> getCommitByRepoNameTimeInterval
            (String repoName,Timestamp start, Timestamp end, Pageable page);

    public Page<CommitDTO> getCommitByRepoNameWeekDay
            (String repoName, String weekDay, Pageable page);
    public Page<CommitDTO> getCommitByRepoIdWeekDay
            (int repoId, String weekDay, Pageable page);
}
