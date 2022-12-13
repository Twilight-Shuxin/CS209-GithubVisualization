package cs209.app.service.impl;

import cs209.app.dto.WordCntDTO;
import cs209.app.repository.WordCntRepository;
import cs209.app.service.WordCntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static cs209.app.util.DTOUtil.toWordCntDTO;

@Service
public class WordCntServiceImpl implements WordCntService {
    @Autowired
    WordCntRepository wordCntRepository;

    @Override
    public Page<WordCntDTO> getByRepoWordCntDesc(int repoId, Pageable paging) {
        return wordCntRepository.findByRepoIdOrderByCntDesc(repoId, paging)
                .map(wordCnt -> toWordCntDTO(wordCnt));
    }

    @Override
    public Page<WordCntDTO> getByRepoWordCntDesc(String repoName, Pageable paging) {
        return wordCntRepository.findByRepoRepoNameOrderByCntDesc(repoName, paging)
                .map(wordCnt -> toWordCntDTO(wordCnt));
    }
}
