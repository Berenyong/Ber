package bssm.major.club.ber.domain.manager_post.manager.web.dto.response;

import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ManagerPostResponseDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final int view;
//    private final int likes;
//    private final List<ManagerPostCommentResponseDto> managerPostComments;
    private final String createMinutesAgo;
//    private final List<PostCategoryResponseDto> postCategories;
    private final List<PostImgResponseDto> imgs;

    public ManagerPostResponseDto(ManagerPost managerPost) {
        this.id = managerPost.getId();
        this.title = managerPost.getTitle();
        this.content = managerPost.getContent();
        this.writer = managerPost.getWriter().getNickname();
        this.view = managerPost.getView();
//        this.likes = managerPost.getLikes().size();
//        this.managerPostComments = managerPost.getManagerPostComment().stream()
//                .map(ManagerPostCommentResponseDto::new)
//                .collect(Collectors.toList());
        this.createMinutesAgo = ChronoUnit.MINUTES.between(
                managerPost.getCreatedAt(), LocalDateTime.now()
        ) + "분전";
//        this.postCategories = managerPost.getPostCategories().stream()
//                .map(PostCategoryResponseDto::new)
//                .collect(Collectors.toList());
        this.imgs = managerPost.getPostImgs().stream()
                .map(PostImgResponseDto::new)
                .collect(Collectors.toList());
    }
}
