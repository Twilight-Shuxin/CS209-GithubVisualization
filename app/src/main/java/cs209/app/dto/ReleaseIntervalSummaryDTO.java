package cs209.app.dto;

import cs209.app.util.ReleaseIntervalRecord;
import cs209.app.util.WeeklyCommitRecord;

import java.io.Serializable;
import java.util.List;

public record ReleaseIntervalSummaryDTO(
        List<ReleaseIntervalRecord> records
) implements Serializable {}
