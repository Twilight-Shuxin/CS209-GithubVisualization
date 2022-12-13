package cs209.app.repository;

import cs209.app.model.WordCnt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordCntRepository extends JpaRepository<WordCnt, Integer> {
    Page<WordCnt> findByRepoRepoNameOrderByCntDesc(String repoName, Pageable paging);
    Page<WordCnt> findByRepoIdOrderByCntDesc(int repoId, Pageable paging);
}
