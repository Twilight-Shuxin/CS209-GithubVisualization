package cs209.app.dto;

import cs209.app.model.Repo;

import java.io.Serializable;
import java.time.OffsetDateTime;

public record CommitDTO(
        int id,
        int repoId,
        int contributorId,
        String message,
        OffsetDateTime commit_time
    ) implements Serializable {
}
