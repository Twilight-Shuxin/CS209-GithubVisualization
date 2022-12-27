package cs209.app.dto;

import cs209.app.util.MonthlyCommitRecord;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public record MonthlyCommitSummaryDTO(
        List<MonthlyCommitRecord> records
) implements Serializable {}
