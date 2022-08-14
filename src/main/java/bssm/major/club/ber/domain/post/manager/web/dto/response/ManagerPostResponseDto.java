package bssm.major.club.ber.domain.post.manager.web.dto.response;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.likes.domain.Likes;
import lombok.Getter;

import java.util.List;

@Getter
public class ManagerPostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final Long writerId;
    private final int view;
    private final List<Likes> likes;

    public ManagerPostResponseDto(ManagerPost managerPost) {
        this.id = managerPost.getId();
        this.title = managerPost.getTitle();
        this.content = managerPost.getContent();
        this.writer = managerPost.getWriter().getNickname();
        this.writerId = managerPost.getWriter().getId();
        this.view = managerPost.getView();
        this.likes = managerPost.getLikes();
    }
}
