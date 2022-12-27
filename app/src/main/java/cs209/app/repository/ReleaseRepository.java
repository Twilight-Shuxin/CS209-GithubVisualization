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

    Page<Release> findByRepoIdOrderByPublishTimeAsc(int repoId, Pageable page);
    Page<Release> findByRepoRepoNameOrderByPublishTimeAsc(String repoName, Pageable page);

    List<Release> findAllByRepoIdOrderByPublishTimeAsc(int repoId);
    List<Release> findAllByRepoRepoNameOrderByPublishTimeAsc(String repoName);


    public Page<Release> findAllByRepoIdAndPublishTimeGreaterThanEqualAndPublishTimeLessThanEqualOrderByPublishTimeAsc
            (int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);
    public Page<Release> findAllByRepoRepoNameAndPublishTimeGreaterThanEqualAndPublishTimeLessThanEqualOrderByPublishTimeAsc
            (String repoName, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);


    public Page<Release> findAllByRepoIdAndPublishTimeGreaterThanEqualOrderByPublishTimeAsc
            (int repoId, OffsetDateTime startTime, Pageable page);
    public Page<Release> findAllByRepoRepoNameAndPublishTimeGreaterThanEqualOrderByPublishTimeAsc
            (String repoName, OffsetDateTime startTime, Pageable page);
    public Page<Release> findAllByRepoIdAndPublishTimeLessThanEqualOrderByPublishTimeAsc
            (int repoId, OffsetDateTime endTime, Pageable page);
    public Page<Release> findAllByRepoRepoNameAndPublishTimeLessThanEqualOrderByPublishTimeAsc
            (String repoName, OffsetDateTime endTime, Pageable page);

    @Query(value = "SELECT * FROM RELEASES WHERE REPO_ID = ?1 AND extract(isodow from PUBLISHED_AT) -1 = ?2",
            countQuery = "SELECT count(*) FROM RELEASES WHERE REPO_ID = ?1 AND extract(isodow from PUBLISHED_AT) -1 = ?2",
            nativeQuery = true)
    Page<Release> findByRepoIdAndWeekDay(int repoId, int weekDayId, Pageable pageable);
}