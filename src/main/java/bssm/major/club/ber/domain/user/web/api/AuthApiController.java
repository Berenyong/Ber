package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.service.AuthService;
import bssm.major.club.ber.domain.user.web.dto.auth.LoginRequestDto;
import bssm.major.club.ber.domain.user.web.dto.auth.TokenResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto login(@RequestBody @Valid LoginRequestDto request) {
        return authService.login(request);
    }

    @DeleteMapping("/logout")
    public void logout(HttpServletRequest request) {
        authService.logout(request.getHeader("ACCESS-TOKEN"));
    }
}
