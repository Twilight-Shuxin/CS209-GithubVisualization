package cs209.app.service;

import cs209.app.model.Repo;

import java.util.List;
import java.util.Optional;

public interface RepoService {
    public List<Repo> getRepo();
    public Repo addRepo(Repo repo);
    public Optional<Repo> getRepoById(int repoId);
    public Optional<Repo> getRepoByName(String repoName);
}
