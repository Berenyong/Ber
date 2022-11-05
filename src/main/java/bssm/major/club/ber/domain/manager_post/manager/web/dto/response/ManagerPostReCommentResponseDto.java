package bssm.major.club.ber.domain.manager_post.manager.web.dto.response;


import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPostReComment;
import lombok.Getter;

@Getter
public class ManagerPostReCommentResponseDto {
    private final Long id;
    private final String comment;
    private final String nickname;

    public ManagerPostReCommentResponseDto(ManagerPostReComment reComment) {
        this.id = reComment.getId();
        this.comment = reComment.getComment();
        this.nickname = reComment.getWriterName();
    }
}
