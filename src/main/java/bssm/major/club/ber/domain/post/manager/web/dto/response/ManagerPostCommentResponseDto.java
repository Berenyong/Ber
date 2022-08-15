package bssm.major.club.ber.domain.post.manager.web.dto.response;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ManagerPostCommentResponseDto {

    private final Long id;
    private final String comment;
    private final String nickname;
    private final List<ManagerPostReComment> reComment = new ArrayList<>();

    public ManagerPostCommentResponseDto(ManagerPostComment comment) {
        this.id = comment.getId();
        this.comment = comment.getComment();
        this.nickname = comment.getWriter().getNickname();
        reComment.addAll(comment.getReComment());
    }

}
