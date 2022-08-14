package bssm.major.club.ber.global.jwt;

import bssm.major.club.ber.global.config.redis.RedisService;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtValidateService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public String getEmail(String token) {
        return jwtTokenProvider.extractAllClaims(token)
                .get("email", String.class);
    }

    public String getRole(String token) {
        return jwtTokenProvider.extractAllClaims(token)
                .get("roles", String.class);
    }
    public void validateRefreshToken(String token) {
        if (redisService.getData(getEmail(token)) == null) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }
}
