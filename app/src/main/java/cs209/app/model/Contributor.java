package cs209.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "contributors")
public class Contributor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "name")
    String name;

    @Column(name = "followers")
    int followerCnt;

    @Column(name = "followings")
    int followingCnt;

    @Column(name = "public_repos")
    int publicRepoCnt;

//    @JsonIgnore
//    @OneToMany
//    @JoinColumn(name = "id")
//    Set<Contribution> contributionSet;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFollowerCnt() {
        return followerCnt;
    }

    public void setFollowerCnt(int followerCnt) {
        this.followerCnt = followerCnt;
    }

    public int getFollowingCnt() {
        return followingCnt;
    }

    public void setFollowingCnt(int followingCnt) {
        this.followingCnt = followingCnt;
    }

    public int getPublicRepoCnt() {
        return publicRepoCnt;
    }

    public void setPublicRepoCnt(int publicRepoCnt) {
        this.publicRepoCnt = publicRepoCnt;
    }
}
