package cs209.app.controller;

import cs209.app.AppApplication;
import cs209.app.dto.IssueDTO;
import cs209.app.model.Issue;
import cs209.app.model.Repo;
import cs209.app.service.IssueService;
import cs209.app.service.RepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class RepoController {
    @Autowired private RepoService repoService;
    @Autowired
    IssueService issueService;

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
            return issueService.getIssueByRepoId(repoId, paging);
        }
        else return issueService.getIssueByRepoIdWithState(repoId, state, paging);
    }
}
