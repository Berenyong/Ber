package bssm.major.club.ber.domain.post.manager.web.dto.response;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import lombok.Getter;

@Getter
public class ManagerPostCommentResponseDto {

    private final Long id;
    private final String comment;
    private final String nickname;

    public ManagerPostCommentResponseDto(ManagerPostComment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.nickname = comment.getWriter().getNickname();
    }
}
