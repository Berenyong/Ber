package bssm.major.club.ber.domain.user.service;

import bssm.major.club.ber.domain.ber.domain.type.Gender;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.domain.type.Role;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.domain.user.web.dto.auth.LoginRequestDto;
import bssm.major.club.ber.domain.user.web.dto.auth.TokenResponseDto;
import bssm.major.club.ber.global.config.redis.RedisService;
import bssm.major.club.ber.global.jwt.JwtTokenProvider;
import bssm.major.club.ber.global.jwt.JwtValidateService;
import bssm.major.club.ber.global.util.CookieUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("인증 서비스")
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks private AuthService authService;

    @Mock private UserRepository userRepository;
    @Mock private UserFacade userFacade;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private JwtValidateService jwtValidateService;
    @Mock private RedisService redisService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private CookieUtil cookieUtil;

    User user = User.builder()
            .email("rltgjqmduftlagl@gmail.com")
            .gender(Gender.MAN)
            .name("최원용")
            .classNumber(14)
            .age(18)
            .blogLink("asdfasf.com")
            .disciplinePeriod(LocalDate.ofEpochDay(0))
            .gitLink("asdf.com")
            .nickname("nyong")
            .password("!@#$1234QWER")
            .role(Role.ROLE_ADMIN)
            .warning(1)
            .build();
}