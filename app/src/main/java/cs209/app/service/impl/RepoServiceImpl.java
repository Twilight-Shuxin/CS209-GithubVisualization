package cs209.app.service.impl;

import cs209.app.dto.IssueDTO;
import cs209.app.model.Repo;
import cs209.app.repository.RepoRepository;
import cs209.app.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static cs209.app.util.DTOUtil.toIssueDTO;

@Service
public class RepoServiceImpl implements RepoService {
    @Autowired
    RepoRepository repoRepository;

    public List<Repo> getRepo() {
        return repoRepository.findAll();
    }

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
