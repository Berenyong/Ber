package bssm.major.club.ber.domain.user.service;

import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.domain.user.web.dto.auth.LoginRequestDto;
import bssm.major.club.ber.domain.user.web.dto.auth.TokenResponseDto;
import bssm.major.club.ber.global.config.redis.RedisService;
import bssm.major.club.ber.global.util.CookieUtil;
import bssm.major.club.ber.global.jwt.JwtTokenProvider;
import bssm.major.club.ber.global.jwt.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;

import static bssm.major.club.ber.global.jwt.JwtProperties.ACCESS_TOKEN_VALID_TIME;
import static bssm.major.club.ber.global.jwt.JwtProperties.REFRESH_TOKEN_VALID_TIME;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserFacade userFacade;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtValidateService jwtValidateService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final CookieUtil cookieUtil;

    public TokenResponseDto login(LoginRequestDto request) {
        User user = userFacade.getCurrentUser();

        user.matchedPassword(passwordEncoder, user, request.getPassword());

        final String accessToken = jwtTokenProvider.createAccessToken(request.getEmail(), user.getNickname());
        final String refreshToken = jwtTokenProvider.createRefreshToken(request.getEmail(), user.getNickname());

        redisService.setDataExpire(request.getEmail(), refreshToken, REFRESH_TOKEN_VALID_TIME);

        Cookie accessTokenCookie = cookieUtil.createCookie("ACCESS-TOKEN", accessToken, ACCESS_TOKEN_VALID_TIME);
        Cookie refreshTokenCookie = cookieUtil.createCookie("REFRESH-TOKEN", accessToken, REFRESH_TOKEN_VALID_TIME);

        return TokenResponseDto.builder()
                .accessToken(accessTokenCookie)
                .refreshToken(refreshTokenCookie)
                .build();
    }

    public void logout(String accessToken) {
        User user = userFacade.getCurrentUser();

        jwtTokenProvider.logout(user.getEmail(), accessToken);
    }

    public TokenResponseDto getNewAccessToken(String refreshToken) {
        jwtValidateService.validateToken(refreshToken);
        String nickname = jwtValidateService.getNickname(refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(jwtValidateService.getEmail(refreshToken), nickname);
        Cookie accessTokenCookie = cookieUtil.createCookie("ACCESS-TOKEN", accessToken, ACCESS_TOKEN_VALID_TIME);
        return TokenResponseDto.builder()
                .accessToken(accessTokenCookie)
                .build();
    }
}
