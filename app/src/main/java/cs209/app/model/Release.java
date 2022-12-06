package cs209.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "releases")
public class Release {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "release_name")
    String releaseName;

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

    @Column(name = "published_at")
    OffsetDateTime publishTime;

    @Column(name = "message")
    String message;

    @ManyToOne
    @JoinColumn(name = "repo_id")
    @JsonIgnore
    Repo repo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReleaseName() {
        return releaseName;
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName;
    }

    public OffsetDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(OffsetDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
