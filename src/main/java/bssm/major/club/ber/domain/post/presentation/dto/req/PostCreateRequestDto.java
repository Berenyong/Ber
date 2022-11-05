package bssm.major.club.ber.domain.post.presentation.dto.req;

import bssm.major.club.ber.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostCreateRequestDto {

    private final String title;
    private final String content;

    @Builder
    public PostCreateRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post toEntity() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
