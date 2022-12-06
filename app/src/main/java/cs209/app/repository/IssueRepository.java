package cs209.app.repository;

import cs209.app.model.Commit;
import cs209.app.model.Issue;
import org.apache.ibatis.ognl.BooleanExpression;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Integer> {

    Page<Issue> findByRepoId(int repoId, Pageable paging);

    Page<Issue> findByRepoIdAndStateClosed(int repoId, Boolean stateClosed, Pageable paging);

//    public Page<Issue> findAllByRepoIdAndCreateTimeGreaterThanEqualAndCreateTimeLessThanEqual
//            (int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);
}