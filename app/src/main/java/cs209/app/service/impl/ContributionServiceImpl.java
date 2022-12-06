package cs209.app.service.impl;

import cs209.app.dto.ContributionDTO;
import cs209.app.dto.ContributorInRepoDTO;
import cs209.app.repository.ContributionRepository;
import cs209.app.service.ContributionService;
import cs209.app.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static cs209.app.util.DTOUtil.toContributionDTO;
import static cs209.app.util.DTOUtil.toContributorInRepoDTO;

@Service
public class ContributionServiceImpl implements ContributionService {
    @Autowired
    ContributionRepository contributionRepository;

    @Autowired
    RepoService repoService;

    @Override
    public Page<ContributionDTO> getContributionByRepoId(int repoId, Pageable page) {
        return contributionRepository.findByRepoId(repoId, page).map(contribution -> toContributionDTO(contribution));
    }

    @Override
    public Page<ContributionDTO> getContributionByRepoName(String repoName, Pageable page) {
        return contributionRepository.findByRepoId(
                repoService.getRepoByName(repoName).get().getId(), page)
                .map(contribution -> toContributionDTO(contribution));
    }

    @Override
    public Page<ContributorInRepoDTO> getContributorByContributionInRepoByRepoId(int repoId, Pageable page) {
        return contributionRepository.findByRepoId(repoId, page).map(contribution -> toContributorInRepoDTO(contribution));
    }
}
