package bssm.major.club.ber.domain.category.post.dto.response;

import bssm.major.club.ber.domain.category.post.domain.PostCategory;
import lombok.Getter;

@Getter
public class PostCategoryResponseDto {
    private final String name;

    public PostCategoryResponseDto(PostCategory category) {
        this.name = category.getName();
    }
}
