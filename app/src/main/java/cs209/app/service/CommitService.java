package cs209.app.service;

import cs209.app.dto.CommitDTO;
import cs209.app.dto.MonthlyCommitSummaryDTO;
import cs209.app.dto.ReleaseDTO;
import cs209.app.dto.WeeklyCommitSummaryDTO;
import cs209.app.util.CommonUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;


public interface CommitService {
    public Page<CommitDTO> getCommitByRepo(int repoId, Pageable page);
    public Page<CommitDTO> getCommitByRepo(String repoName, Pageable page);

    public Page<CommitDTO> getCommitByRepoTimeInterval
            (int repoId, OffsetDateTime start, OffsetDateTime end, Pageable page);
    public Page<CommitDTO> getCommitByRepoTimeInterval
            (String repoName,OffsetDateTime start, OffsetDateTime end, Pageable page);

    public Page<CommitDTO> getCommitByRepoWeekDay
            (int repoId, int weekDay, Pageable page);
    public Page<CommitDTO> getCommitByRepoWeekDay
            (String repoName, int weekDay, Pageable page);

    public Page<CommitDTO> getCommitByRepoAfterTime(int repoId, OffsetDateTime start,
                                                        Pageable paging);
    public Page<CommitDTO> getCommitByRepoAfterTime(String repoName, OffsetDateTime start,
                                                      Pageable paging);

    public Page<CommitDTO> getCommitByRepoBeforeTime(int repoId, OffsetDateTime end,
                                                         Pageable paging);
    public Page<CommitDTO> getCommitByRepoBeforeTime(String repoName, OffsetDateTime end,
                                                       Pageable paging);

    public int getTotalContributorCountsByRepo(int repoId);
    public int getTotalContributorCountsByRepo(String repoName);

    public MonthlyCommitSummaryDTO getCommitMonthlySummaryByRepo(int repoId);
    public MonthlyCommitSummaryDTO getCommitMonthlySummaryByRepo(String repoName);

    WeeklyCommitSummaryDTO getCommitWeeklySummaryByRepo(int repoId);
    WeeklyCommitSummaryDTO getCommitWeeklySummaryByRepo(String repoName);
}
