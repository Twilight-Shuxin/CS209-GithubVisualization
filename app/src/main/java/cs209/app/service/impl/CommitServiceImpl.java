package cs209.app.service.impl;

import cs209.app.AppApplication;
import cs209.app.dto.CommitDTO;
import cs209.app.repository.CommitRepository;
import cs209.app.service.CommitService;
import cs209.app.service.RepoService;
import cs209.app.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

import static cs209.app.util.DTOUtil.toCommitDTO;
import static cs209.app.util.DTOUtil.toReleaseDTO;

@Service
public class CommitServiceImpl implements CommitService {
    @Autowired
    CommitRepository commitRepository;

    @Autowired
    RepoService repoService;

    @Override
    public Page<CommitDTO> getCommitByRepo(int repoId, Pageable page) {
        return commitRepository.findByRepoId(repoId, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepo(String repoName, Pageable page) {
        return commitRepository.findByRepoRepoName(repoName, page)
                .map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoTimeInterval(int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page) {
        return commitRepository.findAllByRepoIdAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
                (repoId, startTime, endTime, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoTimeInterval(String repoName, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page) {
        return commitRepository.findAllByRepoRepoNameAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
                (repoName, startTime, endTime, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoWeekDay(int repoId, int weekDay, Pageable page) {
        return commitRepository.findByRepoIdAndWeekDay(
                repoId, weekDay, page
        ).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoWeekDay(String repoName, int weekDay, Pageable page) {
        return commitRepository.findByRepoIdAndWeekDay(
                repoService.getRepoByName(repoName).get().getId(), weekDay, page
        ).map(commit -> toCommitDTO(commit));
    }


    @Override
    public Page<CommitDTO> getCommitByRepoAfterTime(int repoId, OffsetDateTime startTime, Pageable paging) {
        return commitRepository.findAllByRepoIdAndCommitTimeGreaterThanEqual
                (repoId, startTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoAfterTime(String repoName, OffsetDateTime startTime, Pageable paging) {
        return commitRepository.findAllByRepoRepoNameAndCommitTimeGreaterThanEqual
                (repoName, startTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoBeforeTime(int repoId, OffsetDateTime endTime, Pageable paging) {
        return commitRepository.findAllByRepoIdAndCommitTimeLessThanEqual
                (repoId, endTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoBeforeTime(String repoName, OffsetDateTime endTime, Pageable paging) {
        return commitRepository.findAllByRepoRepoNameAndCommitTimeLessThanEqual
                (repoName, endTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public int getTotalContributorCountsByRepo(int repoId) {
        Pageable paging = PageRequest.of(0, AppApplication.pageSize);
        return (int) commitRepository.findDistinctContributorIdByRepoId(repoId, paging).getTotalElements();
    }

    @Override
    public int getTotalContributorCountsByRepo(String repoName) {
        Pageable paging = PageRequest.of(0, AppApplication.pageSize);
        return (int) commitRepository.findDistinctContributorIdByRepoRepoName(repoName, paging).getTotalElements();
    }


}
