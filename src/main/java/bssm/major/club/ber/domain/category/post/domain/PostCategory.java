package bssm.major.club.ber.domain.category.post.domain;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Entity
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_post_id")
    @JsonIgnore
    private ManagerPost managerPost;

    @Builder
    public PostCategory(Long id, String name, ManagerPost managerPost) {
        this.id = id;
        this.name = name;
        this.managerPost = managerPost;
    }

    public void confirmManagerPost(ManagerPost managerPost) {
        managerPost.addCategory(this);
        this.managerPost = managerPost;
    }

}
