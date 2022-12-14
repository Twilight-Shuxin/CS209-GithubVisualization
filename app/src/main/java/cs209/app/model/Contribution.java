package cs209.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "contributions")
public class Contribution {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

//    @Column(name = "repo_id")
//    int repoId;

    @Column(name = "commits")
    int commitCnt;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    @JsonIgnore
    Repo repo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "contributor_id")
    Contributor contributor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCommitCnt() {
        return commitCnt;
    }

    public void setCommitCnt(int commitCnt) {
        this.commitCnt = commitCnt;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public Contributor getContributor() {
        return contributor;
    }

    public void setContributor(Contributor contributor) {
        this.contributor = contributor;
    }
}
