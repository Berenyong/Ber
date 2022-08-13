package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.service.EmailService;
import bssm.major.club.ber.domain.user.service.UserService;
import bssm.major.club.ber.domain.user.web.dto.UserJoinRequestDto;
import bssm.major.club.ber.domain.user.web.dto.UserResponseDto;
import bssm.major.club.ber.domain.user.web.dto.email.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public String emailConfirm(@RequestBody EmailDto request) throws Exception{
        emailService.sendSimpleMessage(request.getEmail());
        return "코드 발송 완료!\n" + request.getEmail() + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto signup(@RequestBody UserJoinRequestDto request) {
        return userService.signup(request);
    }

}
