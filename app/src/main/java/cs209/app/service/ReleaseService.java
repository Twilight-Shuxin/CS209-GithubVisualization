package cs209.app.service;

import cs209.app.model.Release;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.Timestamp;
import java.util.List;

public interface ReleaseService {

    public List<Release> getByRepoName(String repoName);
    public List<Release> getByRepoNameInInterval(String repoName, Timestamp start,
                                                 Timestamp end, int pageNum);

    public List<Release> getByRepoId(int repoId);
    public List<Release> getByRepoIdInInterval();
}
