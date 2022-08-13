package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.service.UserService;
import bssm.major.club.ber.domain.user.web.dto.UserJoinRequestDto;
import bssm.major.club.ber.domain.user.web.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto signup(@RequestBody UserJoinRequestDto request) {
        return userService.signup(request);
    }

}
