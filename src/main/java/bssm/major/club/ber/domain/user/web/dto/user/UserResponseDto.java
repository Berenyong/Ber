package bssm.major.club.ber.domain.user.web.dto.user;

import bssm.major.club.ber.domain.user.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String nickname;
    private final int age;
    private final String role;
    private final String gitLink;
    private final String blogLink;

    // TODO
//    private final Map<Long, String> freePosts;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.age = user.getAge();
        this.role = user.getRole().name();
        this.gitLink = user.getGitLink();
        this.blogLink = user.getBlogLink();
    }
}
