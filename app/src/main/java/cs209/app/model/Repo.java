package cs209.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "repos")
public class Repo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "repo_name")
    String repoName;

    @OneToMany
    @JoinColumn(name = "repo_id")
    Set<Commit> commitSet;

    @OneToMany
    @JoinColumn(name = "repo_id")
    Set<Contribution> contributionSet;

    @OneToMany
    @JoinColumn(name = "repo_id")
    Set<Issue> issueSet;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "repo_id")
    Set<Release> releaseSet;

    @OneToMany
    @JoinColumn(name = "repo_id")
    Set<Issue> wordCntSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepoName() {
        return repoName;
    }

    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }
}
