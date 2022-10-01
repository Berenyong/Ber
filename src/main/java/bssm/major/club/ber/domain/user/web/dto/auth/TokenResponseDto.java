package bssm.major.club.ber.domain.user.web.dto.auth;

import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.Cookie;

@Getter
@Builder
public class TokenResponseDto {

    private Cookie accessToken;
    private Cookie refreshToken;

}
