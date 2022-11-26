package bssm.major.club.ber.domain.ber.category.post.domain;

import bssm.major.club.ber.domain.post.domain.Post;
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
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    @Builder
    public PostCategory(Long id, String name, User user, Post post) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.post = post;
    }

    public void confirmUser(User user) {
        user.addPostCategories(this);
        this.user = user;
    }

    public void confirmPost(Post Post) {
        post.addCategory(this);
        this.post = post;
    }

}