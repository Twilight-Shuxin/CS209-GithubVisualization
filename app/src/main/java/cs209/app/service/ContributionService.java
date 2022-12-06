package cs209.app.service;

import cs209.app.dto.ContributionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
public interface ContributionService {
    public Page<ContributionDTO> getContributionByRepoId(int repoId, Pageable page);
    public Page<ContributionDTO> getContributionByRepoName(String repoName, Pageable page);
}
