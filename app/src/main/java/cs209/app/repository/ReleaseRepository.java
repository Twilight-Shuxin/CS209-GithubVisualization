package cs209.app.repository;

import cs209.app.model.Commit;
import cs209.app.model.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.OffsetDateTime;

public interface ReleaseRepository extends JpaRepository<Release, Integer> {
    public Page<Release> findAllByRepoIdAndPublishTimeGreaterThanEqualAndPublishTimeLessThanEqual
            (int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);

    Page<Release> findByRepoId(int repoId, Pageable page);

    public Page<Release> findAllByRepoIdAndPublishTimeGreaterThanEqual
            (int repoId, OffsetDateTime startTime, Pageable page);
    public Page<Release> findAllByRepoIdAndPublishTimeLessThanEqual
            (int repoId, OffsetDateTime endTime, Pageable page);
}