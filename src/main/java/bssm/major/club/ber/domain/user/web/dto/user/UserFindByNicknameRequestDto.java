package bssm.major.club.ber.domain.user.web.dto.user;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserFindByNicknameRequestDto {

    @NotBlank
    private String nickname;
}
