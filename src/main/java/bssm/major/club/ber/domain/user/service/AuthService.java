package bssm.major.club.ber.domain.user.service;

import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.web.dto.auth.LoginRequestDto;
import bssm.major.club.ber.domain.user.web.dto.auth.TokenResponseDto;
import bssm.major.club.ber.global.config.redis.RedisService;
import bssm.major.club.ber.global.config.security.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import bssm.major.club.ber.global.jwt.JwtTokenProvider;
import bssm.major.club.ber.global.jwt.JwtValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static bssm.major.club.ber.global.jwt.JwtProperties.REFRESH_TOKEN_VALID_TIME;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtValidateService jwtValidateService;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;

    public TokenResponseDto login(LoginRequestDto request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        user.matchedPassword(passwordEncoder, user, request.getPassword());

        List<String> roles = new ArrayList<>();
        roles.add(user.getRole().name());

        final String accessToken = jwtTokenProvider.createAccessToken(request.getEmail(), roles);
        final String refreshToken = jwtTokenProvider.createRefreshToken(request.getEmail(), roles);
        redisService.setDataExpire(request.getEmail(), refreshToken, REFRESH_TOKEN_VALID_TIME);

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String accessToken) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        jwtTokenProvider.logout(user.getEmail(), accessToken);
    }

    public TokenResponseDto getNewAccessToken(String refreshToken) {
        jwtValidateService.validateRefreshToken(refreshToken);

        List<String> roles = new ArrayList<>();
        roles.add(jwtValidateService.getRole(refreshToken));

        return TokenResponseDto.builder()
                .accessToken(jwtTokenProvider.createAccessToken(
                        jwtValidateService.getEmail(refreshToken), roles))
                .build();
    }
}
