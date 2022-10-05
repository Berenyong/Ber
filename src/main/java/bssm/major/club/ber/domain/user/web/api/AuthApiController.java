package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.service.AuthService;
import bssm.major.club.ber.domain.user.web.dto.auth.LoginRequestDto;
import bssm.major.club.ber.domain.user.web.dto.auth.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    public TokenResponseDto login(
            @RequestBody @Valid LoginRequestDto request,
            HttpServletResponse res
    ) {
        TokenResponseDto tokenRes = authService.login(request);
        res.addCookie(tokenRes.getAccessToken());
        res.addCookie(tokenRes.getRefreshToken());

        return tokenRes;
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        authService.logout(request.getHeader("ACCESS-TOKEN"));
    }

    @PutMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto getNewAccessToken(@RequestHeader(value = "REFRESH-TOKEN") String refreshToken) {
        return authService.getNewAccessToken(refreshToken);
    }
}
