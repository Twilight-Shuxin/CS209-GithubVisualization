package cs209.app.service;

import cs209.app.model.Contributor;

public interface ContributorService {
    public Contributor getContributorById(int contributorId);
    public Contributor getContributorByName(String contributorName);
}
