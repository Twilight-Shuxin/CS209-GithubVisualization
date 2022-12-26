package cs209.app.repository;

import cs209.app.model.Commit;
import cs209.app.model.Issue;
import org.apache.ibatis.ognl.BooleanExpression;
import org.postgresql.util.PGInterval;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    Page<Issue> findByRepoId(int repoId, Pageable paging);
    Page<Issue> findByRepoRepoName(String repoName, Pageable paging);

    Page<Issue> findByRepoIdAndStateClosed(int repoId, Boolean stateClosed, Pageable paging);
    Page<Issue> findByRepoRepoNameAndStateClosed(String repoId, Boolean stateClosed, Pageable paging);

    public Page<Issue> findAllByRepoIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual
            (int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);

    public Page<Issue> findAllByRepoRepoNameAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual
            (String repoName, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);

    @Query(value = "SELECT avg(closed_at - created_at) FROM ISSUES WHERE REPO_ID = ?1 AND STATE = true;",
            nativeQuery = true)
    public PGInterval getAverageIssueResolveTime(int repoId);

    @Query(value = "SELECT max(closed_at - created_at) FROM ISSUES WHERE REPO_ID = ?1 AND STATE = true;",
            nativeQuery = true)
    public PGInterval getMaxIssueResolveTime(int repoId);

    @Query(value = "SELECT min(closed_at - created_at) FROM ISSUES WHERE REPO_ID = ?1 AND STATE = true;",
            nativeQuery = true)
    public PGInterval getMinIssueResolveTime(int repoId);
}