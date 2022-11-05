package bssm.major.club.ber.domain.category.post.domain;

import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class PostCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_post_id")
    @JsonIgnore
    private ManagerPost managerPost;

    @Builder
    public PostCategory(Long id, String name, User user, ManagerPost managerPost) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.managerPost = managerPost;
    }

    public void confirmUser(User user) {
        user.addPostCategories(this);
        this.user = user;
    }

    public void confirmManagerPost(ManagerPost managerPost) {
        managerPost.addCategory(this);
        this.managerPost = managerPost;
    }

}