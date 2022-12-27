package cs209.app.service;

import cs209.app.dto.ReleaseDTO;
import cs209.app.dto.ReleaseIntervalSummaryDTO;
import cs209.app.model.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.OffsetDateTime;
import java.util.List;

public interface ReleaseService {
    public Page<ReleaseDTO> getByRepo(int repoId, Pageable paging);
    public Page<ReleaseDTO> getByRepo(String repoName, Pageable paging);
    public Page<ReleaseDTO> getByRepoTimeInterval(int repoId, OffsetDateTime start,
                                                  OffsetDateTime end, Pageable paging);
    public Page<ReleaseDTO> getByRepoTimeInterval(String repoName, OffsetDateTime start,
                                                      OffsetDateTime end, Pageable paging);

    public Page<ReleaseDTO> getReleaseByRepoAfterTime(int repoId, OffsetDateTime start,
                                                        Pageable paging);
    public Page<ReleaseDTO> getReleaseByRepoAfterTime(String repoName, OffsetDateTime start,
                                                      Pageable paging);
    public Page<ReleaseDTO> getReleaseByRepoBeforeTime(int repoId, OffsetDateTime end,
                                                       Pageable paging);
    public Page<ReleaseDTO> getReleaseByRepoBeforeTime(String repoName, OffsetDateTime end,
                                                       Pageable paging);

    public int getAverageCommitCntBetweenRelease(int repoId);

    public int getAverageCommitCntBetweenRelease(String repoName);

    public ReleaseIntervalSummaryDTO getCommitCntBetweenRelease(int repoId);
    public ReleaseIntervalSummaryDTO getCommitCntBetweenRelease(String repoName);
}
