package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.service.EmailService;
import bssm.major.club.ber.domain.user.service.UserService;
import bssm.major.club.ber.domain.user.web.dto.user.UserJoinRequestDto;
import bssm.major.club.ber.domain.user.web.dto.user.UserResponseDto;
import bssm.major.club.ber.domain.user.web.dto.email.EmailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/email")
    @ResponseStatus(HttpStatus.OK)
    public String emailConfirm(@RequestBody @Valid EmailDto request) throws Exception{
        emailService.sendSimpleMessage(request.getEmail());
        return "코드 발송 완료!\n" + request.getEmail() + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto signup(@RequestBody @Valid UserJoinRequestDto request) throws Exception {
        return userService.signup(request);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto User(@PathVariable Long id) {
        return userService.findUser(id);
    }

}
