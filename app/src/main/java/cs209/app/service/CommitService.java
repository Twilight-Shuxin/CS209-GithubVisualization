package cs209.app.service;

import cs209.app.dto.CommitDTO;
import cs209.app.dto.ReleaseDTO;
import cs209.app.util.CommonUtil;
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
            (String repoName, int weekDay, Pageable page);
    public Page<CommitDTO> getCommitByRepoIdWeekDay
            (int repoId, int weekDay, Pageable page);

    public Page<CommitDTO> getCommitByRepoIdAfterTime(int repoId, OffsetDateTime start,
                                                        Pageable paging);

    public Page<CommitDTO> getCommitByRepoIdBeforeTime(int repoId, OffsetDateTime end,
                                                         Pageable paging);
}
