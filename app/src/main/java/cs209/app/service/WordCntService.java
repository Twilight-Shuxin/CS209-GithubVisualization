package cs209.app.service;

import cs209.app.dto.WordCntDTO;
import cs209.app.model.Repo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WordCntService {
    public Page<WordCntDTO> getByRepoWordCntDesc(int repoId, Pageable paging);
    public Page<WordCntDTO> getByRepoWordCntDesc(String repoName, Pageable paging);
}