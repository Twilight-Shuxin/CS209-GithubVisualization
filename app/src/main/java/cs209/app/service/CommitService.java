package cs209.app.service;

import cs209.app.dto.CommitDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;


public interface CommitService {
    public Page<CommitDTO> getCommitByRepoId(int repoId, Pageable page);
    public Page<CommitDTO> getCommitByRepoName(String repoName, Pageable page);

    public Page<CommitDTO> getCommitByRepoIdTimeInterval
            (int repoId, OffsetDateTime start, OffsetDateTime end, Pageable page);
    public Page<CommitDTO> getCommitByRepoNameTimeInterval
            (String repoName,OffsetDateTime start, OffsetDateTime end, Pageable page);

    public Page<CommitDTO> getCommitByRepoNameWeekDay
            (String repoName, String weekDay, Pageable page);
    public Page<CommitDTO> getCommitByRepoIdWeekDay
            (int repoId, String weekDay, Pageable page);
}
