package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllFollows",
                query = "SELECT f FROM Follow AS f ORDER BY f.id DESC"
        ),
        @NamedQuery(
                name = "getFollowsCount",
                query = "SELECT COUNT(f) FROM Follow AS f"
        ),
        @NamedQuery(
                name = "getFollowId",
                query = "SELECT f.id FROM Follow as f WHERE f.followee = :followee AND f.follower = :follower"
        ),
        @NamedQuery(
                name = "getEmployeeFollows",
                query = "SELECT f FROM Follow AS f WHERE f.follower = :employee ORDER BY f.id DESC"
        ),
        @NamedQuery(
                name = "getEmployeeFollowsCount",
                query = "SELECT COUNT(f) FROM Follow AS f WHERE f.follower = :employee"
        ),
        @NamedQuery(
                name = "isFollowed",
                query = "SELECT COUNT(f) FROM Follow AS f WHERE f.followee = :followee AND f.follower = :follower"
        )
})
@Table(name = "Follow")
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follower", nullable = false)
    private Employee follower;

    @ManyToOne
    @JoinColumn(name = "followee", nullable = false)
    private Employee followee;

    @Column(name = "follow_at", nullable = false)
    private Timestamp followAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollower() {
        return follower;
    }

    public void setFollower(Employee follower) {
        this.follower = follower;
    }

    public Employee getFollowee() {
        return followee;
    }

    public void setFollowee(Employee followee) {
        this.followee = followee;
    }

    public Timestamp getFollowAt() {
        return followAt;
    }

    public void setFollowAt(Timestamp followAt) {
        this.followAt = followAt;
    }

}