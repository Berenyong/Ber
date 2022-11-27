package bssm.major.club.ber.domain.post.domain;

import bssm.major.club.ber.domain.ber.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.likes.domain.Likes;
import bssm.major.club.ber.domain.post.domain.type.PostKind;
import bssm.major.club.ber.domain.post.web.dto.request.PostRequestDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.entity.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;


    private String title;

    @Enumerated(EnumType.STRING)
    private PostKind postKind;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "writer_id")
    private User writer;

    private int view = 1;

    // cascade = OneToMany -> ManyToOne
    // Ex) ManyToOne 에 cascade 적용시
    // 게시글 삭제시 유저 삭제
    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<Likes> likes = new ArrayList<>();

    /***
     * 나중에 댓글 추가
     */
    // @OneToMany(mappedBy = "managerPost", cascade = ALL)
    // private List<PostComment> managerPostComment = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = ALL)
    private List<PostCategory> postCategories = new ArrayList<>();

    public void addCategory(PostCategory postCategory) {
        postCategories.add(postCategory);
    }

    /**
     * 나중에 이미지 추가
     */
    // @OneToMany(mappedBy = "managerPost", cascade = ALL)
    // private List<PostImg> postImgs = new ArrayList<>();


    @Builder
    public Post(String title, PostKind postKind, String content, List<PostCategory> postCategories) {
        this.title = title;
        this.postKind = postKind;
        this.content = content;
        this.postCategories = postCategories;
    }

    public void confirmWriter(User writer){
        this.writer = writer;
    }

    public void update(PostRequestDto request){
        this.title = request.getTitle();
        this.postKind = request.getPostKind();
        this.content = request.getContent();
        this.postCategories = request.getPostCategories();
    }
}
