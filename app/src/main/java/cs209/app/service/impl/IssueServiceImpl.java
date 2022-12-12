package cs209.app.service.impl;

import cs209.app.dto.IssueDTO;
import cs209.app.repository.IssueRepository;
import cs209.app.repository.RepoRepository;
import cs209.app.service.IssueService;
import cs209.app.service.RepoService;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

import static cs209.app.util.DTOUtil.toIssueDTO;

@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    IssueRepository issueRepository;
    @Autowired
    RepoService repoService;
    @Autowired
    private RepoRepository repoRepository;


    @Override
    public Page<IssueDTO> getIssueByRepo(int repoId, Pageable paging) {
        return issueRepository.findByRepoId(repoId, paging).map(issue -> toIssueDTO(issue));
    }

    @Override
    public Page<IssueDTO> getIssueByRepo(String repoName, Pageable paging) {
        return issueRepository.findByRepoRepoName(repoName, paging)
                .map(issue -> toIssueDTO(issue));
    }

    @Override
    public Page<IssueDTO> getIssueByRepoWithState(int repoId, String state, Pageable paging) {
        boolean stateClosed = state.equals("closed") ? true : false;
        return issueRepository.findByRepoIdAndStateClosed(repoId, stateClosed, paging).map(issue -> toIssueDTO(issue));
    }

    @Override
    public Page<IssueDTO> getIssueByRepoWithState(String repoName, String state, Pageable paging) {
        boolean stateClosed = state.equals("closed") ? true : false;
        return issueRepository.findByRepoRepoNameAndStateClosed(repoName, stateClosed, paging).map(issue -> toIssueDTO(issue));
    }

    @Override
    public Page<IssueDTO> getIssueByRepoTimeInterval(int repoId, OffsetDateTime start, OffsetDateTime end, Pageable page) {
        return issueRepository.findAllByRepoIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(repoId, start, end, page)
                .map(issue -> toIssueDTO(issue));
    }

    @Override
    public Page<IssueDTO> getIssueByRepoTimeInterval(String repoName, OffsetDateTime start, OffsetDateTime end, Pageable page) {
        return issueRepository.findAllByRepoRepoNameAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual(repoName, start, end, page)
                .map(issue -> toIssueDTO(issue));
    }

    @Override
    public PGInterval getAverageIntervalByRepo(String repoName) {
        return issueRepository.getAverageIssueResolveTime(
                repoService.getRepoByName(repoName).get().getId());
    }

    public PGInterval getAverageIntervalByRepo(int repoID) {
        return issueRepository.getAverageIssueResolveTime(repoID);
    }

}
