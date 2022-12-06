package cs209.app.service.impl;

import cs209.app.AppApplication;
import cs209.app.dto.ReleaseDTO;
import cs209.app.model.Commit;
import cs209.app.model.Release;
import cs209.app.repository.CommitRepository;
import cs209.app.repository.ReleaseRepository;
import cs209.app.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import static cs209.app.util.DTOUtil.toReleaseDTO;

@Service
public class ReleaseServiceImpl implements ReleaseService {
    @Autowired
    private ReleaseRepository releaseRepository;

    @Autowired
    private CommitRepository commitRepository;

    @Override
    public Page<ReleaseDTO> getByRepoName(String repoName, Pageable paging) {

        return null;
    }

    @Override
    public Page<ReleaseDTO> getByRepoNameTimeInterval(String repoName, OffsetDateTime start, OffsetDateTime end, Pageable paging) {
        return null;
    }

    @Override
    public Page<ReleaseDTO> getByRepoId(int repoId, Pageable paging) {
        return releaseRepository.findByRepoId(repoId, paging)
                .map(release -> toReleaseDTO(release));
    }

    @Override
    public Page<ReleaseDTO> getByRepoIdTimeInterval(int repoId,
                                                    OffsetDateTime start, OffsetDateTime end, Pageable paging) {
        return releaseRepository.findAllByRepoIdAndPublishTimeGreaterThanEqualAndPublishTimeLessThanEqual(repoId, start, end, paging)
                .map(release -> toReleaseDTO(release));
    }

    @Override
    public Page<ReleaseDTO> getReleaseByRepoIdAfterTime(int repoId, OffsetDateTime start, Pageable paging) {
        return releaseRepository.findAllByRepoIdAndPublishTimeGreaterThanEqual(repoId, start, paging)
                .map(release -> toReleaseDTO(release));
    }

    @Override
    public Page<ReleaseDTO> getReleaseByRepoIdBeforeTime(int repoId, OffsetDateTime end, Pageable paging) {
        return releaseRepository.findAllByRepoIdAndPublishTimeLessThanEqual(repoId, end, paging)
                .map(release -> toReleaseDTO(release));
    }

    @Override
    public int getAverageCommitCntBetweenRelease(int repoId) {
        Pageable paging = PageRequest.of(0, AppApplication.pageSize);
        Page<Release> releases = releaseRepository.findByRepoId(repoId, paging);
        Page<Commit> commits = commitRepository.findByRepoId(repoId, paging);
        int totalReleaseCnt = (int) releases.getTotalElements();
        int totalCommitsCnt = (int) commits.getTotalElements();
        return totalCommitsCnt / totalReleaseCnt;
    }
}
