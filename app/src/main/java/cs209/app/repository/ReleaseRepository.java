package cs209.app.repository;

import cs209.app.model.Commit;
import cs209.app.model.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.OffsetDateTime;
import java.util.List;

public interface ReleaseRepository extends JpaRepository<Release, Integer> {
    Page<Release> findByRepoId(int repoId, Pageable page);
    Page<Release> findByRepoRepoName(String repoName, Pageable page);

    public Page<Release> findAllByRepoIdAndPublishTimeGreaterThanEqualAndPublishTimeLessThanEqual
            (int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);
    public Page<Release> findAllByRepoRepoNameAndPublishTimeGreaterThanEqualAndPublishTimeLessThanEqual
            (String repoName, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);


    public Page<Release> findAllByRepoIdAndPublishTimeGreaterThanEqual
            (int repoId, OffsetDateTime startTime, Pageable page);
    public Page<Release> findAllByRepoRepoNameAndPublishTimeGreaterThanEqual
            (String repoName, OffsetDateTime startTime, Pageable page);
    public Page<Release> findAllByRepoIdAndPublishTimeLessThanEqual
            (int repoId, OffsetDateTime endTime, Pageable page);
    public Page<Release> findAllByRepoRepoNameAndPublishTimeLessThanEqual
            (String repoName, OffsetDateTime endTime, Pageable page);

    @Query(value = "SELECT * FROM RELEASES WHERE REPO_ID = ?1 AND extract(isodow from PUBLISHED_AT) -1 = ?2",
            countQuery = "SELECT count(*) FROM RELEASES WHERE REPO_ID = ?1 AND extract(isodow from PUBLISHED_AT) -1 = ?2",
            nativeQuery = true)
    Page<Release> findByRepoIdAndWeekDay(int repoId, int weekDayId, Pageable pageable);
}