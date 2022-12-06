package cs209.app.service;

import cs209.app.dto.ReleaseDTO;
import cs209.app.model.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.OffsetDateTime;
import java.util.List;

public interface ReleaseService {

    public Page<ReleaseDTO> getByRepoName(String repoName, Pageable paging);
    public Page<ReleaseDTO> getByRepoNameTimeInterval(String repoName, OffsetDateTime start,
                                                 OffsetDateTime end, Pageable paging);

    public Page<ReleaseDTO> getByRepoId(int repoId, Pageable paging);
    public Page<ReleaseDTO> getByRepoIdTimeInterval(int repoId, OffsetDateTime start,
                                                    OffsetDateTime end, Pageable paging);

    public Page<ReleaseDTO> getReleaseByRepoIdAfterTime(int repoId, OffsetDateTime start,
                                                        Pageable paging);

    public Page<ReleaseDTO> getReleaseByRepoIdBeforeTime(int repoId, OffsetDateTime end,
                                                        Pageable paging);
}
