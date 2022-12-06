package cs209.app.controller;

import cs209.app.AppApplication;
import cs209.app.dto.CommitDTO;
import cs209.app.dto.IssueDTO;
import cs209.app.model.Issue;
import cs209.app.model.Repo;
import cs209.app.service.CommitService;
import cs209.app.service.IssueService;
import cs209.app.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.OffsetDateTime;
import java.util.Optional;

@RestController
public class RepoController {
    @Autowired private RepoService repoService;
    @Autowired
    IssueService issueService;
    @Autowired
    CommitService commitService;

    @PostMapping("/repo")
    public Repo addRepo(@RequestBody Repo repo) {
        System.out.println(repo.getId() + " " + repo.getRepoName());
        return repoService.addRepo(repo);
    }

    @GetMapping("/repo/{id}")
    public Optional<Repo> getRepo(@PathVariable("id") int repoId) {
        return repoService.getRepoById(repoId);
    }

    @GetMapping("/repo")
    public Optional<Repo> getRepo(@RequestParam("repo_name") String repoName) {
        return repoService.getRepoByName(repoName);
    }

    @GetMapping("/repo/{id}/issue")
    public Page<IssueDTO> getIssueByRepoId(@PathVariable("id") int repoId,
                                           @RequestParam(value = "state", required = false) String state,
                                           @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        if(state == null) {
            Page<IssueDTO> issueDTOS = issueService.getIssueByRepoId(repoId, paging);
            OffsetDateTime timestamp = issueDTOS.getContent().get(0).closedTime();
            System.out.println(timestamp);
            return issueDTOS;
        }
        else return issueService.getIssueByRepoIdWithState(repoId, state, paging);
    }

    @GetMapping("/repo/{id}/commit")
    public Page<CommitDTO> getCommitByRepoIdTimeInterval(@PathVariable("id") int repoId,
                                                         @RequestParam(value = "start", required = false) String startTimeStr,
                                                         @RequestParam(value = "end", required = false) String endTimeStr,
                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int pageNum,
                                                         @RequestParam(value = "weekday", required = false, defaultValue = "-1") int weekday) {
        Pageable paging = PageRequest.of(pageNum, AppApplication.pageSize);
        if(weekday >= 0) {
            return commitService.getCommitByRepoIdWeekDay(repoId, weekday, paging);
        }
        if(startTimeStr == null) {
            if(startTimeStr == null && endTimeStr == null) {
                return commitService.getCommitByRepoId(repoId, paging);
            }
            return null;
        }
        if(endTimeStr == null) {
            return null;
        }
        OffsetDateTime startTime = OffsetDateTime.parse(startTimeStr);
        OffsetDateTime endTime = OffsetDateTime.parse(endTimeStr);
        return commitService.getCommitByRepoIdTimeInterval(repoId, startTime, endTime, paging);
    }
}
