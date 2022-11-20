package bssm.major.club.ber.domain.post.manager.domain;

import bssm.major.club.ber.domain.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.entity.BasePostEntity;
import bssm.major.club.ber.domain.likes.domain.Likes;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "manager_post")
@Entity
public class ManagerPost extends BasePostEntity {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    private int view = 1;

    // cascade = OneToMany -> ManyToOne
    // Ex) ManyToOne 에 cascade 적용시
    // 게시글 삭제시 유저 삭제
    @OneToMany(mappedBy = "managerPost", cascade = ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "managerPost", cascade = ALL)
    private List<ManagerPostComment> managerPostComment = new ArrayList<>();

    @OneToMany(mappedBy = "managerPost", cascade = ALL)
    private List<PostCategory> postCategories = new ArrayList<>();

    @OneToMany(mappedBy = "managerPost", cascade = ALL)
    private List<PostImg> postImgs = new ArrayList<>();

    @Builder
    public ManagerPost(String title, String content, User writer, int view, List<Likes> likes, List<ManagerPostComment> managerPostComment, List<PostCategory> postCategories, List<PostImg> postImgs) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.view = view;
        this.likes = likes;
        this.managerPostComment = managerPostComment;
        this.postCategories = postCategories;
        this.postImgs = postImgs;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void upView() {
        this.view += 1;
    }

    public void confirmComment(ManagerPostComment comment) {
        this.managerPostComment.add(comment);
    }

    public void addCategory(PostCategory category) {
        postCategories.add(category);
    }
}
