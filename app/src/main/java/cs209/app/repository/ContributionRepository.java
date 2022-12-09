package cs209.app.repository;

import cs209.app.model.Commit;
import cs209.app.model.Contribution;
import cs209.app.model.Issue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContributionRepository extends JpaRepository<Contribution, Integer> {
    Page<Contribution> findByRepoId(int repoId, Pageable paging);
    Page<Contribution> findByRepoIdOrderByCommitCntDesc(int repoId, Pageable paging);
}
