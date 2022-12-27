package cs209.app.repository;

import cs209.app.dto.CommitDTO;
import cs209.app.model.Commit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Integer> {
    Page<Commit> findByRepoId(int repoId, Pageable page);

    Page<Commit> findByRepoRepoName(String repoName, Pageable page);

    public Page<Commit> findAllByRepoIdAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
            (int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);

    public Page<Commit> findAllByRepoRepoNameAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
            (String repoName, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);

    public Page<Commit> findAllByRepoIdAndCommitTimeGreaterThanEqual
            (int repoId, OffsetDateTime startTime, Pageable page);
    public Page<Commit> findAllByRepoRepoNameAndCommitTimeGreaterThanEqual
            (String repoName, OffsetDateTime startTime, Pageable page);

    public Page<Commit> findAllByRepoIdAndCommitTimeLessThanEqual
            (int repoId, OffsetDateTime endTime, Pageable page);

    public Page<Commit> findAllByRepoRepoNameAndCommitTimeLessThanEqual
            (String repoName, OffsetDateTime endTime, Pageable page);


    @Query(value = "SELECT * FROM COMMITS WHERE REPO_ID = ?1 AND extract(isodow from COMMIT_TIME) -1 = ?2",
            countQuery = "SELECT count(*) FROM COMMITS WHERE REPO_ID = ?1 AND extract(isodow from COMMIT_TIME) -1 = ?2",
            nativeQuery = true)
    Page<Commit> findByRepoIdAndWeekDay(int repoId, int weekDayId, Pageable pageable);

    Page<Commit> findDistinctContributorIdByRepoId(int repoId, Pageable page);
    Page<Commit> findDistinctContributorIdByRepoRepoName(String repoName, Pageable page);


}
