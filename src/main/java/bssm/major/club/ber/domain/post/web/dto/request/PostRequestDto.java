package bssm.major.club.ber.domain.post.web.dto.request;

import bssm.major.club.ber.domain.ber.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.domain.type.PostKind;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Data
@NoArgsConstructor
public class PostRequestDto {

    @NotBlank(message = "제목을 필수입니다")
    private String title;

    @NotBlank(message = "게시판 종류는 필수입니다")
    private PostKind postKind;

    @NotBlank(message = "내용을 필수입니다")
    private String content;

    private List<PostCategory> postCategories = new ArrayList<>();

    @Builder
    public PostRequestDto(String title, PostKind postKind, String content, List<PostCategory> postCategories) {
        this.title = title;
        this.postKind = postKind;
        this.content = content;
        this.postCategories = postCategories;
    }

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .postKind(postKind)
                .content(content)
                .postCategories(postCategories)
                .build();
    }
}
