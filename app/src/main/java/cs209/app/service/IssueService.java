package cs209.app.service;

import cs209.app.dto.CommitDTO;
import cs209.app.dto.IssueDTO;
import cs209.app.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;
import org.postgresql.util.PGInterval;

public interface IssueService {
    public Page<IssueDTO> getIssueByRepo(int repoId, Pageable paging);
    public Page<IssueDTO> getIssueByRepo(String repoName, Pageable paging);


    public Page<IssueDTO> getIssueByRepoWithState(int repoId, String state, Pageable paging);
    public Page<IssueDTO> getIssueByRepoWithState(String repoName, String state, Pageable paging);

    public Page<IssueDTO> getIssueByRepoTimeInterval
            (int repoId, OffsetDateTime start, OffsetDateTime end, Pageable page);
    public Page<IssueDTO> getIssueByRepoTimeInterval
            (String repoName,OffsetDateTime start, OffsetDateTime end, Pageable page);

    public PGInterval getAverageIntervalByRepo(String repoName);
    public PGInterval getAverageIntervalByRepo(int repoId);
}
