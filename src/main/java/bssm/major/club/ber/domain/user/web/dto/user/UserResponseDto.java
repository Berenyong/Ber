package bssm.major.club.ber.domain.user.web.dto.user;

import bssm.major.club.ber.domain.ber.category.user.domain.UserCategory;
import bssm.major.club.ber.domain.post.web.dto.response.PostResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String nickname;
    private final int age;
    private final String role;
    private final String gitLink;
    private final String blogLink;
    private final String img;
    private final int warning;
    private final List<PostResponseDto> posts;
    private final List<UserCategory> userCategories;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.role = user.getRole().name();
        this.gitLink = user.getGitLink();
        this.blogLink = user.getBlogLink();
        this.img = user.getImgUrl();
        this.warning = user.getWarning();
        this.posts = user.getPost().stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
        this.userCategories = user.getCategories();
    }
}
