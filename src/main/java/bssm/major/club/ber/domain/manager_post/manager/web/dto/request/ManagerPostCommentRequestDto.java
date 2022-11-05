package bssm.major.club.ber.domain.manager_post.manager.web.dto.request;

import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPostComment;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;


@Getter
public class ManagerPostCommentRequestDto {

    @NotBlank
    private final String comment;
    private final ManagerPost managerPost;

    @Builder
    public ManagerPostCommentRequestDto(String comment, ManagerPost managerPost) {
        this.comment = comment;
        this.managerPost = managerPost;
    }

    public ManagerPostComment toEntity() {
        return ManagerPostComment.builder()
                .comment(comment)
                .managerPost(managerPost)
                .build();
    }

}
