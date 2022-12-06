package cs209.app.service.impl;

import cs209.app.dto.CommitDTO;
import cs209.app.repository.CommitRepository;
import cs209.app.service.CommitService;
import cs209.app.service.RepoService;
import cs209.app.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    public Page<CommitDTO> getCommitByRepoNameWeekDay(String repoName, int weekDay, Pageable page) {
        return commitRepository.findByRepoIdAndWeekDay(
                repoService.getRepoByName(repoName).get().getId()
                , weekDay, page
        ).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoIdWeekDay(int repoId, int weekDay, Pageable page) {
        return commitRepository.findByRepoIdAndWeekDay(
                repoId, weekDay, page
        ).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoIdAfterTime(int repoId, OffsetDateTime startTime, Pageable paging) {
        return commitRepository.findAllByRepoIdAndCommitTimeGreaterThanEqual
                (repoId, startTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoIdBeforeTime(int repoId, OffsetDateTime endTime, Pageable paging) {
        return commitRepository.findAllByRepoIdAndCommitTimeLessThanEqual
                (repoId, endTime, paging).map(commit -> toCommitDTO(commit));
    }


}
