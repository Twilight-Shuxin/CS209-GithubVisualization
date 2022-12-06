package cs209.app.dto;


import cs209.app.model.Repo;

import java.io.Serializable;
import java.time.OffsetDateTime;

public record IssueDTO(
        int id,
        int repoId,
        String title,
        String userName,
        OffsetDateTime createTime,
        OffsetDateTime closedTime,
        boolean stateClosed,
        int commentCnt,
        String message,
        String url
) implements Serializable {}
