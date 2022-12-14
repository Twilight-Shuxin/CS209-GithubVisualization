package cs209.app.controller;

import cs209.app.AppApplication;
import cs209.app.dto.*;
import cs209.app.model.Issue;
import cs209.app.model.Repo;
import cs209.app.model.WordCnt;
import cs209.app.service.*;
import java.sql.Time;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.postgresql.util.PGInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/repo")
public class RepoController {
    @Autowired private RepoService repoService;
    @Autowired
    IssueService issueService;
    @Autowired
    CommitService commitService;
    @Autowired
    ContributionService contributionService;
    @Autowired
    ReleaseService releaseService;

    @Autowired
    WordCntService wordCntService;

    @GetMapping("{repo_name}")
    public Optional<Repo> getRepo(@PathVariable("repo_name") String repoName) {
        return repoService.getRepoByName(repoName);
    }

    @GetMapping("all_repo")
    public List<Repo> getAllRepo() {
        return repoService.getRepo();
    }

    @GetMapping("test/{repo_id}")
    public Optional<Repo> getRepo(@PathVariable("repo_id") int repoId) {
        return repoService.getRepoById(repoId);
    }

    @GetMapping("{repo_name}/issue")
    public Page<IssueDTO> getIssueByRepo(@PathVariable("repo_name") String repoName,
                                           @RequestParam(value = "state", required = false) String state,
                                           @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        if (state == null) {
            Page<IssueDTO> issueDTOs = issueService.getIssueByRepo(repoName, paging);
            OffsetDateTime timestamp = issueDTOs.getContent().get(0).closedTime();
            System.out.println(timestamp);
            return issueDTOs;
        }
        else return issueService.getIssueByRepoWithState(repoName, state, paging);
    }

    @GetMapping("{repo_name}/release")
    public Page<ReleaseDTO> getReleaseByRepoTimeInterval(@PathVariable("repo_name") String repoName,
                                                       @RequestParam(value = "start", required = false) String startTimeStr,
                                                       @RequestParam(value = "end", required = false) String endTimeStr,
                                                       @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        if(startTimeStr == null) {
            if(startTimeStr == null && endTimeStr == null) {
                return releaseService.getByRepo(repoName, paging);
            }
            return releaseService.getReleaseByRepoBeforeTime(repoName,
                    OffsetDateTime.parse(startTimeStr), paging);
        }
        if(endTimeStr == null) {
            return releaseService.getReleaseByRepoAfterTime(repoName,
                    OffsetDateTime.parse(endTimeStr), paging);
        }
        OffsetDateTime startTime = OffsetDateTime.parse(startTimeStr);
        OffsetDateTime endTime = OffsetDateTime.parse(endTimeStr);
        return releaseService.getByRepoTimeInterval(repoName, startTime, endTime, paging);
    }


    @GetMapping("{repo_name}/commit")
    public Page<CommitDTO> getCommitByRepoTimeInterval(@PathVariable("repo_name") String repoName,
                                                         @RequestParam(value = "start", required = false) String startTimeStr,
                                                         @RequestParam(value = "end", required = false) String endTimeStr,
                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum,
                                                         @RequestParam(value = "weekday", required = false, defaultValue = "-1") int weekday) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        if(weekday >= 0) {
            return commitService.getCommitByRepoWeekDay(repoName, weekday, paging);
        }
        if(startTimeStr == null) {
            if(startTimeStr == null && endTimeStr == null) {
                return commitService.getCommitByRepo(repoName, paging);
            }
            return commitService.getCommitByRepoBeforeTime(repoName, OffsetDateTime.parse(startTimeStr), paging);
        }
        if(endTimeStr == null) {
            return commitService.getCommitByRepoAfterTime(repoName, OffsetDateTime.parse(endTimeStr), paging);
        }
        OffsetDateTime startTime = OffsetDateTime.parse(startTimeStr);
        OffsetDateTime endTime = OffsetDateTime.parse(endTimeStr);
        return commitService.getCommitByRepoTimeInterval(repoName, startTime, endTime, paging);
    }

    @GetMapping("{repo_name}/year_commit")
    public MonthlyCommitSummaryDTO getCommitByRepoMonthlySummary(@PathVariable("repo_name") String repoName) {
        return commitService.getCommitMonthlySummaryByRepo(repoName);
    }

    @GetMapping("{repo_name}/release_interval")
    public ReleaseIntervalSummaryDTO getCommitBetweenReleaseByRepo(@PathVariable("repo_name") String repoName) {
        return releaseService.getCommitCntBetweenRelease(repoName);
    }

    @GetMapping("{repo_name}/weekly_commit_summary")
    public WeeklyCommitSummaryDTO getCommitByRepoWeeklySummary(@PathVariable("repo_name") String repoName) {
        return commitService.getCommitWeeklySummaryByRepo(repoName);
    }

    @GetMapping("{repo_name}/contribution")
    public Page<ContributionDTO> getContributionByRepo(@PathVariable("repo_name") String repoName,
                                                       @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        return contributionService.getContributionByRepo(repoName, paging);
    }

    @GetMapping("{repo_name}/contributor")
    public Page<ContributorInRepoDTO> getTopContributorsByRepo(@PathVariable("repo_name") String repoName,
                                                               @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        return contributionService.getContributorByContributionInRepoSorted(repoName, paging);
    }

    @GetMapping("{repo_name}/avg_commit_count")
    public int getAverageCommitCntBetweenReleases(@PathVariable("repo_name") String repoName) {
        return releaseService.getAverageCommitCntBetweenRelease(repoName);
    }

    @GetMapping("{repo_name}/avg_resolve_time")
    public PGInterval getAverageIssueResolveTime(@PathVariable("repo_name") String repoName) {
        return issueService.getAverageIntervalByRepo(repoName);
    }

    @GetMapping("{repo_name}/max_resolve_time")
    public PGInterval getMaxIssueResolveTime(@PathVariable("repo_name") String repoName) {
        return issueService.getMaxIntervalByRepo(repoName);
    }

    @GetMapping("{repo_name}/min_resolve_time")
    public PGInterval getMinIssueResolveTime(@PathVariable("repo_name") String repoName) {
        return issueService.getMinIntervalByRepo(repoName);
    }

    @GetMapping("{repo_name}/word_cnt")
    public Page<WordCntDTO> getWordCntByRepo(@PathVariable("repo_name") String repoName,
                                             @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        return wordCntService.getByRepoWordCntDesc(repoName, paging);
    }


}
