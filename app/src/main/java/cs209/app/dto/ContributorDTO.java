package cs209.app.dto;

import java.io.Serializable;

public record ContributorDTO(
        int id,
        String name,
        int followerCnt,
        int followingCnt,
        int publicRepoCnt
) implements Serializable {}
