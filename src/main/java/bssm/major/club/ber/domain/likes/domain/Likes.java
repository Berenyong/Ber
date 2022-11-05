package bssm.major.club.ber.domain.likes.domain;

import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor
@Entity
public class Likes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "manager_post_id")
    private ManagerPost managerPost;

    @Builder
    public Likes(Long id, ManagerPost managerPost, User user) {
        this.id = id;
        this.managerPost = managerPost;
        this.user = user;
    }

}
