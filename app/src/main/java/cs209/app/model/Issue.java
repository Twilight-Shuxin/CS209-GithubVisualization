package cs209.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "issues")
public class Issue {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "title")
    String title;

    @Column(name = "user_name")
    String userName;

    @Column(name = "created_at")
    OffsetDateTime createTime;

    @Column(name = "closed_at")
    OffsetDateTime closedTime;

    @Column(name = "state")
    boolean stateClosed;

    @Column(name = "comments")
    int commentCnt;

    @Column(name = "message")
    String message;

    @Column(name = "url")
    String url;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public OffsetDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(OffsetDateTime createTime) {
        this.createTime = createTime;
    }

    public OffsetDateTime getClosedTime() {
        return closedTime;
    }

    public void setClosedTime(OffsetDateTime closedTime) {
        this.closedTime = closedTime;
    }

    public boolean isStateClosed() {
        return stateClosed;
    }

    public void setStateClosed(boolean stateClosed) {
        this.stateClosed = stateClosed;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Repo getRepo() {
        return repo;
    }

    public void setRepo(Repo repo) {
        this.repo = repo;
    }

}
