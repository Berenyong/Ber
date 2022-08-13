package bssm.major.club.ber.domain.user.web.dto.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenResponseDto {

    private String accessToken;
    private String refreshToken;

}
