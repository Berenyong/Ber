package bssm.major.club.ber.domain.user.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class UserPasswordRequestDto {

    @NotBlank
    private String password;

    @NotBlank
    private String checkPassword;

}
