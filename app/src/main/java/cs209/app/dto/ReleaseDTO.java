package cs209.app.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;

public record ReleaseDTO(
        int id,
        int repoId,
        String releaseName,
        OffsetDateTime publishTime,
        String message
) implements Serializable {};
