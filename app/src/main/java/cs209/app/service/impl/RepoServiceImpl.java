package cs209.app.service.impl;

import cs209.app.model.Repo;
import cs209.app.repository.RepoRepository;
import cs209.app.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RepoServiceImpl implements RepoService {
    @Autowired
    RepoRepository repoRepository;

    @Override
    public Repo addRepo(Repo repo) {
        return repoRepository.save(repo);
    }

    @Override
    public Optional<Repo> getRepoById(int repoId) {
        return repoRepository.findById(repoId);
    }

    @Override
    public Optional<Repo> getRepoByName(String repoName) {
        return repoRepository.findByRepoName(repoName);
    }
}
