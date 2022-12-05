package cs209.app.repository;

import cs209.app.model.Repo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepoRepository extends JpaRepository<Repo, Integer> {
    public Optional<Repo> findByRepoName(String repoName);
}
