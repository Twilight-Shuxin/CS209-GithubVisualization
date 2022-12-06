package cs209.app.dto;

import java.io.Serializable;

public record ContributorInRepoDTO(
        int id,
        String name,
        int repoId,
        int commitCnt,
        int followerCnt,
        int followingCnt,
        int publicRepoCnt
) implements Serializable {}
