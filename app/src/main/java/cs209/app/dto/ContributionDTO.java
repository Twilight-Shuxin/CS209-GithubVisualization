package cs209.app.dto;

import java.io.Serializable;

public record ContributionDTO(
        int id,
        int contributorId,
        int repoId,
        int commitCnt
) implements Serializable {}