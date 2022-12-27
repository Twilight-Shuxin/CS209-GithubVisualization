package cs209.app.service.impl;

import cs209.app.AppApplication;
import cs209.app.dto.CommitDTO;
import cs209.app.dto.MonthlyCommitSummaryDTO;
import cs209.app.dto.WeeklyCommitSummaryDTO;
import cs209.app.repository.CommitRepository;
import cs209.app.service.CommitService;
import cs209.app.service.RepoService;
import cs209.app.util.CommonUtil;
import cs209.app.util.DTOUtil;
import cs209.app.util.MonthlyCommitRecord;
import cs209.app.util.WeeklyCommitRecord;
import static cs209.app.util.DTOUtil.toCommitDTO;
import static cs209.app.util.DTOUtil.toReleaseDTO;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommitServiceImpl implements CommitService {
    @Autowired
    CommitRepository commitRepository;

    @Autowired
    RepoService repoService;

    @Override
    public Page<CommitDTO> getCommitByRepo(int repoId, Pageable page) {
        return commitRepository.findByRepoId(repoId, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepo(String repoName, Pageable page) {
        return commitRepository.findByRepoRepoName(repoName, page)
                .map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoTimeInterval(int repoId, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page) {
        return commitRepository.findAllByRepoIdAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
                (repoId, startTime, endTime, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoTimeInterval(String repoName, OffsetDateTime startTime, OffsetDateTime endTime, Pageable page) {
        return commitRepository.findAllByRepoRepoNameAndCommitTimeGreaterThanEqualAndCommitTimeLessThanEqual
                (repoName, startTime, endTime, page).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoWeekDay(int repoId, int weekDay, Pageable page) {
        return commitRepository.findByRepoIdAndWeekDay(
                repoId, weekDay, page
        ).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoWeekDay(String repoName, int weekDay, Pageable page) {
        return commitRepository.findByRepoIdAndWeekDay(
                repoService.getRepoByName(repoName).get().getId(), weekDay, page
        ).map(commit -> toCommitDTO(commit));
    }


    @Override
    public Page<CommitDTO> getCommitByRepoAfterTime(int repoId, OffsetDateTime startTime, Pageable paging) {
        return commitRepository.findAllByRepoIdAndCommitTimeGreaterThanEqual
                (repoId, startTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoAfterTime(String repoName, OffsetDateTime startTime, Pageable paging) {
        return commitRepository.findAllByRepoRepoNameAndCommitTimeGreaterThanEqual
                (repoName, startTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoBeforeTime(int repoId, OffsetDateTime endTime, Pageable paging) {
        return commitRepository.findAllByRepoIdAndCommitTimeLessThanEqual
                (repoId, endTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public Page<CommitDTO> getCommitByRepoBeforeTime(String repoName, OffsetDateTime endTime, Pageable paging) {
        return commitRepository.findAllByRepoRepoNameAndCommitTimeLessThanEqual
                (repoName, endTime, paging).map(commit -> toCommitDTO(commit));
    }

    @Override
    public int getTotalContributorCountsByRepo(int repoId) {
        Pageable paging = PageRequest.of(0, AppApplication.pageSize);
        return (int) commitRepository.findDistinctContributorIdByRepoId(repoId, paging).getTotalElements();
    }

    @Override
    public int getTotalContributorCountsByRepo(String repoName) {
        Pageable paging = PageRequest.of(0, AppApplication.pageSize);
        return (int) commitRepository.findDistinctContributorIdByRepoRepoName(repoName, paging).getTotalElements();
    }

    @Override
    public MonthlyCommitSummaryDTO getCommitMonthlySummaryByRepo(int repoId) {
        LocalDateTime now = LocalDateTime.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        Pageable paging = PageRequest.of(0, AppApplication.pageSize);
        List<MonthlyCommitRecord> records = new ArrayList<>();
        for (int i = 0; i < 12; i ++) {
            if (month <= 0) {
                year -= 1;
                month = 12;
            }
            String monthStr = month < 10 ? "0" + month : Integer.toString(month);
            String startStr = year + "-" + monthStr + "-01T00:00:00+00:00";
            YearMonth yearMonthObject = YearMonth.of(year, month);
            int daysInMonth = yearMonthObject.lengthOfMonth();
            String endStr = year + "-" + monthStr + "-" + daysInMonth + "T23:59:59+00:00";
            OffsetDateTime startTime = OffsetDateTime.parse(startStr);
            OffsetDateTime endTime = OffsetDateTime.parse(endStr);
            Page<CommitDTO> page = getCommitByRepoTimeInterval(repoId, startTime, endTime, paging);
            records.add(new MonthlyCommitRecord(year, month, (int) page.getTotalElements()));
            month -= 1;
        }
        return new MonthlyCommitSummaryDTO(records);
    }

    @Override
    public MonthlyCommitSummaryDTO getCommitMonthlySummaryByRepo(String repoName) {
        int repoId = repoService.getRepoByName(repoName).get().getId();
        return getCommitMonthlySummaryByRepo(repoId);
    }

    @Override
    public WeeklyCommitSummaryDTO getCommitWeeklySummaryByRepo(int repoId) {
        List<WeeklyCommitRecord> records = new ArrayList<>();
        Pageable paging = PageRequest.of(0, AppApplication.pageSize);
        for(int i = 0; i <= 6; i ++) {
            Page<CommitDTO> page = getCommitByRepoWeekDay(repoId, i, paging);
            records.add(new WeeklyCommitRecord(DTOUtil.getWeekdayName(i), (int) page.getTotalElements()));
        }
        return new WeeklyCommitSummaryDTO(records);
    }

    @Override
    public WeeklyCommitSummaryDTO getCommitWeeklySummaryByRepo(String repoName) {
        int repoId = repoService.getRepoByName(repoName).get().getId();
        return getCommitWeeklySummaryByRepo(repoId);
    }


}
