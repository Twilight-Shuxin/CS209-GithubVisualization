package cs209.app.service;

import cs209.app.dto.CommitDTO;
import cs209.app.dto.IssueDTO;
import cs209.app.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;

public interface IssueService {
    public Page<IssueDTO> getIssueByRepoId(int repoId, Pageable paging);
    public Page<IssueDTO> getIssueByRepoIdWithState(int repoId, String state, Pageable paging);
    public Page<IssueDTO> getIssueByRepoName(String repoName, Pageable paging);

    public Page<IssueDTO> getIssueByRepoIdTimeInterval
            (int repoId, OffsetDateTime start, OffsetDateTime end, Pageable page);
    public Page<IssueDTO> getIssueByRepoNameTimeInterval
            (String repoName,OffsetDateTime start, OffsetDateTime end, Pageable page);
}
