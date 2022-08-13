package bssm.major.club.ber.domain.user.web.dto.email;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class EmailCodeCheckRequestDto {

    @NotBlank
    private String code;

}
