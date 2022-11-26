package bssm.major.club.ber.domain.likes.domain;

import bssm.major.club.ber.domain.post.domain.Post;
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
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Likes(Long id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }

}
