package cs209.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.ValueGenerationType;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.OffsetDateTime;

@Entity
@Table(name = "commits")
public class Commit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    @JsonIgnore
    Repo repo;

    @Column(name = "contributor_id")
    int contributorId;

    @Column(name = "message")
    String message;

    @Column(name = "commit_time")
    OffsetDateTime commitTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(OffsetDateTime commitTime) {
        this.commitTime = commitTime;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    public int getContributorId() {
        return contributorId;
    }

    public void setContributorId(int contributorId) {
        this.contributorId = contributorId;
    }
}
