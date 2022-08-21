package bssm.major.club.ber.domain.user.domain;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.domain.likes.domain.Likes;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import bssm.major.club.ber.global.entity.BaseTimeEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int classNumber;
    private String email;

    private String nickname;

    private int age;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String gitLink;

    private String blogLink;

    @OneToMany(mappedBy = "user", cascade = ALL)
    private final List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private final List<ManagerPost> managerPost = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private final List<ManagerPostComment> managerPostComments = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    @JsonIgnore
    private final List<ManagerPostReComment> managerPostReComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL)
    @JsonIgnore
    private final List<Ber> ber = new ArrayList<>();

    @Builder
    public User(Long id, String email, String nickname, int age, String password, Role role, String gitLink, String blogLink) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.age = age;
        this.password = password;
        this.role = role;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
    }

    // Update User
    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String password) {
        this.password = passwordEncoder.encode(password);
    }

    public void updateGitLink(String gitLink) {
        this.gitLink = gitLink;
    }

    public void updateBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    // auth
    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public void matchedPassword(PasswordEncoder passwordEncoder, User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }
    }

    public void addUserAuthority() {
        this.role = Role.ROLE_USER;
    }

    //== 연관관계 편의 메소드==/
    public void addManagerPost(ManagerPost managerPost) {
        this.getManagerPost().add(managerPost);
    }

    public void addComment(ManagerPostComment comment) {
        this.getManagerPostComments().add(comment);
    }

    public void addReComment(ManagerPostReComment reComment) {
        this.getManagerPostReComments().add(reComment);
    }

    public void addBer(Ber ber) {
        this.ber.add(ber);
    }
}
