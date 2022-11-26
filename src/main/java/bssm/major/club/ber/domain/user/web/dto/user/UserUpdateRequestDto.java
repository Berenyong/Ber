package bssm.major.club.ber.domain.user.web.dto.user;

import bssm.major.club.ber.domain.ber.category.user.domain.UserCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserUpdateRequestDto {

    private final String nickname;
    private final String password;
    private final String gitLink;
    private final String blogLink;
    private final String statusMassage;
    private List<UserCategory> categories;

    @Builder
    public UserUpdateRequestDto(String nickname, String password, String gitLink, String blogLink, String statusMassage, List<UserCategory> categories) {
        this.nickname = nickname;
        this.password = password;
        this.gitLink = gitLink;
        this.blogLink = blogLink;
        this.statusMassage = statusMassage;
        this.categories = categories;
    }
}
