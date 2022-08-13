package bssm.major.club.ber.domain.user.web.dto.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

    private final String nickname;
    private final String password;
    private final String gitLink;
    private final String blogLink;

    @Builder
    public UserUpdateRequestDto(String nickname, String password, String gitLink, String blogLink) {
        this.nickname = nickname;
        this.password = password;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
    }
}
