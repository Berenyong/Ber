package bssm.major.club.ber.domain.post.web.dto.response;

import bssm.major.club.ber.domain.ber.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.likes.domain.Likes;
import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.domain.type.PostKind;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {

    private Long id;

    private String title;

    private PostKind postKind;

    private String content;

    private User writer;

    private int view = 1;

    // cascade = OneToMany -> ManyToOne
    // Ex) ManyToOne 에 cascade 적용시
    // 게시글 삭제시 유저 삭제
    private List<Likes> likes = new ArrayList<>();

    /***
     * 나중에 댓글 추가
     */
    // private List<PostComment> managerPostComment = new ArrayList<>();

    private List<PostCategory> postCategories = new ArrayList<>();

    /**
     * 나중에 이미지 추가
     */
    // private List<PostImg> postImgs = new ArrayList<>();


    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.postKind = post.getPostKind();
        this.content = post.getContent();
        this.writer = post.getWriter();
        this.view = post.getView();
        this.likes = post.getLikes();
        this.postCategories = post.getPostCategories();
    }
}
