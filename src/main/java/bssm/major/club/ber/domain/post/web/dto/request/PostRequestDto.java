package bssm.major.club.ber.domain.post.web.dto.request;

import bssm.major.club.ber.domain.ber.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.domain.type.PostKind;
import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Data
public class PostRequestDto {

    @NotBlank(message = "제목을 필수입니다")
    private String title;

    @NotBlank(message = "게시판 종류는 필수입니다")
    private PostKind postKind;

    @NotBlank(message = "내용을 필수입니다")
    private String content;

    private List<PostCategory> postCategories = new ArrayList<>();

    public Post toEntity(){
        return Post.builder()
                .title(title)
                .postKind(postKind)
                .content(content)
                .postCategories(postCategories)
                .build();
    }
}
