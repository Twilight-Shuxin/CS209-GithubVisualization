package cs209.app.dto;

import java.io.Serializable;

public record WordCntDTO (
        int id,
        int repoId,
        String word,
        int wordCnt
) implements Serializable {};
