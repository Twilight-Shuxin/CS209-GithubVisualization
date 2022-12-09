package cs209.app.dto;

import java.io.Serializable;

public record ContributorDTO(
        int id,
        String name,
        Integer followerCnt,
        Integer followingCnt,
        Integer publicRepoCnt
) implements Serializable {}
