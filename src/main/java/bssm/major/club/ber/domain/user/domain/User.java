package bssm.major.club.ber.domain.user.domain;

import bssm.major.club.ber.domain.ber.domain.Ber;
import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.category.user.domain.UserCategory;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.domain.likes.domain.Likes;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import bssm.major.club.ber.global.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4)
    private String name;

    @Column(unique = true)
    private int classNumber; // 학번

    @Column(length = 64)
    private String email; // 학교 이메일

    @Column(length = 64)
    private String nickname;

    @Column(length = 4)
    private int age;

    @Column(length = 256)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 256)
    private String gitLink;

    @Column(length = 256)
    private String blogLink;

    @Column(length = 128)
    private String statusMessage;

    private int warning = 0;

    private LocalDate disciplinePeriod;

    public void add2Days() {
        this.disciplinePeriod = LocalDate.now();
        this.disciplinePeriod = disciplinePeriod.plusDays(2);
    }

    public void initDisciplinePeriod() {
        this.disciplinePeriod = null;
    }

    @OneToMany(mappedBy = "user", cascade = ALL)
    private final List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private final List<ManagerPost> managerPost = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private final List<ManagerPostComment> managerPostComments = new ArrayList<>();

    @OneToMany(mappedBy = "writer", cascade = ALL)
    private final List<ManagerPostReComment> managerPostReComments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL)
    private final List<Ber> ber = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserCategory> categories = new ArrayList<>();

    @Builder
    public User(Long id, String name, int classNumber, String email, String nickname, int age, String password, Role role, Gender gender, String gitLink, String blogLink, int warning, LocalDate disciplinePeriod) {
        this.id = id;
        this.name = name;
        this.classNumber = classNumber;
        this.email = email;
        this.nickname = nickname;
        this.age = age;
        this.password = password;
        this.role = role;
        this.gender = gender;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
        this.warning = warning;
        this.disciplinePeriod = disciplinePeriod;
    }

    // Update User Info
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
    public void updateStatusMessage(String statusMassage) {
        this.statusMessage = statusMassage;
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

    public void addManager() {
        this.role = Role.ROLE_MANAGER;
    }

    public void addAdmin() {
        this.role = Role.ROLE_ADMIN;
    }

    public void addDiscipline() {
        this.role = Role.ROLE_DISCIPLINE;
    }


    public void setMan() {
        this.gender = Gender.MAN;
    }

    public void setWoman() {
        this.gender = Gender.WOMAN;
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

    public void addCategories(UserCategory category) {
        this.categories.add(category);
    }

    //== 베르실 예약 ==/
    public void addBer(Ber ber) {
        this.ber.add(ber);
    }
    public void addWarning() {
        this.warning ++;
    }

    public void initWarning() {
        this.warning = 0;
    }

}
