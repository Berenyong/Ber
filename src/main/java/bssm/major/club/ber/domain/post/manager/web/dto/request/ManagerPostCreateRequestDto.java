package bssm.major.club.ber.domain.post.manager.web.dto.request;

import bssm.major.club.ber.domain.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ManagerPostCreateRequestDto {

    private final String title;
    private final String content;
    private final User writer;
    private final List<String> categories;
    private MultipartFile file;

    @Builder
    public ManagerPostCreateRequestDto(String title, String content, User writer, List<String> categories) {
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.categories = categories;
    }

    public ManagerPost toEntity() {
        return ManagerPost.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
    }
}
