package cs209.app.service.impl;

import cs209.app.model.Release;
import cs209.app.repository.ReleaseRepository;
import cs209.app.service.ReleaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReleaseServiceImpl implements ReleaseService {
    @Autowired
    private ReleaseRepository releaseRepository;

    @Override
    public List<Release> getByRepoName(String repoName) {

        return null;
    }

    @Override
    public List<Release> getByRepoNameInInterval(String repoName, Timestamp start, Timestamp end, int pageNum) {
        return null;
    }

    @Override
    public List<Release> getByRepoId(int repoId) {
        return null;
    }

    @Override
    public List<Release> getByRepoIdInInterval() {
        return null;
    }
}
