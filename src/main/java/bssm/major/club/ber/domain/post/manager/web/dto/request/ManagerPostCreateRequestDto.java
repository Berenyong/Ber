package bssm.major.club.ber.domain.post.manager.web.dto.request;

import bssm.major.club.ber.domain.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ManagerPostCreateRequestDto {

    private final String title;
    private final String content;

    @Builder
    public ManagerPostCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public ManagerPost toEntity(User writer) {
        return ManagerPost.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
