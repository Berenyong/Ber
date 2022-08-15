package bssm.major.club.ber.domain.post.manager.web.dto.request;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ManagerPostReCommentCreateRequestDto {

    private final String comment;
    private final ManagerPostComment parent;
    private final User writer;

    @Builder
    public ManagerPostReCommentCreateRequestDto(String comment, ManagerPostComment parent, User writer) {
        this.comment = comment;
        this.parent = parent;
        this.writer = writer;
    }

    public ManagerPostReComment toEntity() {
        return ManagerPostReComment.builder()
                .comment(comment)
                .parent(parent)
                .writer(writer)
                .build();
    }
}
