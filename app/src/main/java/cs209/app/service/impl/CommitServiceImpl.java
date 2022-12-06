package cs209.app.service.impl;

import cs209.app.dto.CommitDTO;
import cs209.app.repository.CommitRepository;
import cs209.app.repository.RepoRepository;
import cs209.app.service.CommitService;
import cs209.app.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

import static cs209.app.dto.DTOUtil.toCommitDTO;
import static cs209.app.dto.DTOUtil.toIssueDTO;

@Service
public class CommitServiceImpl implements CommitService {
    @Autowired
    CommitRepository commitRepository;

    @Autowired
    RepoService repoService;

    @Override
    public Page<CommitDTO> getCommitByRepoId(int repoId, Pageable page) {
        return commitRepository.findByRepoId(repoId, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoName(String repoName, Pageable page) {
        return commitRepository.findByRepoId(repoService.getRepoByName(repoName).get().getId(), page)
                .map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoIdTimeInterval(int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page) {
        return commitRepository.findAllByRepoIdAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
                (repoId, startTime, endTime, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoNameTimeInterval(String repoName, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page) {
        return commitRepository.findAllByRepoIdAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
                (repoService.getRepoByName(repoName).get().getId(),
                        startTime, endTime, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoNameWeekDay(String repoName, String weekDay, Pageable page) {
        return null;
    }

    @Override
    public Page<CommitDTO> getCommitByRepoIdWeekDay(int repoId, String weekDay, Pageable page) {
        return null;
    }
}
