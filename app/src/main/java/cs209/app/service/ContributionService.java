package cs209.app.service;

import cs209.app.dto.ContributionDTO;
import cs209.app.dto.ContributorInRepoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
public interface ContributionService {
    public Page<ContributionDTO> getContributionByRepo(int repoId, Pageable page);
    public Page<ContributionDTO> getContributionByRepo(String repoName, Pageable page);

    public Page<ContributorInRepoDTO> getContributorByContributionInRepoSorted(int repoId, Pageable page);
    public Page<ContributorInRepoDTO> getContributorByContributionInRepoSorted(String repoName, Pageable page);
}
