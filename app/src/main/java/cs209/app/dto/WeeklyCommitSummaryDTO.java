package cs209.app.dto;

import cs209.app.util.MonthlyCommitRecord;
import cs209.app.util.WeeklyCommitRecord;

import java.io.Serializable;
import java.util.List;

public record WeeklyCommitSummaryDTO(
        List<WeeklyCommitRecord> records
) implements Serializable {}
