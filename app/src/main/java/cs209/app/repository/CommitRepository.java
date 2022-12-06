package cs209.app.repository;

import cs209.app.dto.CommitDTO;
import cs209.app.model.Commit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface CommitRepository extends JpaRepository<Commit, Integer> {
    public Page<Commit> findAllByRepoIdAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
            (int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page);

    Page<Commit> findByRepoId(int repoId, Pageable page);

    public Page<Commit> findAllByRepoIdAndCommitTimeGreaterThanEqual
            (int repoId, OffsetDateTime startTime, Pageable page);
    public Page<Commit> findAllByRepoIdAndCommitTimeLessThanEqual
            (int repoId, OffsetDateTime endTime, Pageable page);
}
