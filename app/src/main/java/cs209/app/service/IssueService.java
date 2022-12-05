package cs209.app.service;

import cs209.app.dto.IssueDTO;
import cs209.app.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IssueService {
    public Page<IssueDTO> getIssueByRepoId(int repoId, Pageable paging);
    public Page<IssueDTO> getIssueByRepoIdWithState(int repoId, String state, Pageable paging);
    public Page<IssueDTO> getIssueByRepoName(String repoName, Pageable paging);

}
