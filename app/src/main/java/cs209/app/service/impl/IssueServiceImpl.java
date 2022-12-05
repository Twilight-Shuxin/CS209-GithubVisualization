package cs209.app.service.impl;

import cs209.app.AppApplication;
import cs209.app.dto.IssueDTO;
import cs209.app.model.Issue;
import cs209.app.repository.IssueRepository;
import cs209.app.service.IssueService;
import cs209.app.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import static cs209.app.dto.DTOUtil.toIssueDTO;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    RepoService repoService;


    @Override
    public Page<IssueDTO> getIssueByRepoId(int repoId, Pageable paging) {
        return issueRepository.findByRepoId(repoId, paging).map(issue -> toIssueDTO(issue));
    }

    @Override
    public Page<IssueDTO> getIssueByRepoIdWithState(int repoId, String state, Pageable paging) {
        boolean stateClosed = state.equals("closed") ? true : false;
        return issueRepository.findByRepoIdAndStateClosed(repoId, stateClosed, paging).map(issue -> toIssueDTO(issue));
    }

    @Override
    public Page<IssueDTO> getIssueByRepoName(String repoName, Pageable paging) {
        return issueRepository.findByRepoId(repoService.getRepoByName(repoName).get().getId(),
                paging).map(issue -> toIssueDTO(issue));
    }
}
