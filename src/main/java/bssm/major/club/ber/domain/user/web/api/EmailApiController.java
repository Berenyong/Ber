package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.domain.user.service.EmailService;
import bssm.major.club.ber.domain.user.service.UserService;
import bssm.major.club.ber.domain.user.web.dto.email.EmailCodeCheckRequestDto;
import bssm.major.club.ber.domain.user.web.dto.email.EmailDto;
import bssm.major.club.ber.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/email")
@RestController
@Slf4j
public class EmailApiController {

    private final EmailService emailService;
    private final UserService userService;
    private final UserFacade userFacade;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public String emailConfirm(@RequestBody @Valid EmailDto request) throws Exception{
        emailService.sendSimpleMessage(request.getEmail());
        return "코드 발송 완료!\n" + request.getEmail() + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/password")
    @ResponseStatus(HttpStatus.OK)
    public String confirmForgetPasswordEmailSender(@RequestBody @Valid EmailDto request) throws Exception {
        emailService.sendForgetPassword(request.getEmail());
        userService.setEmail(request.getEmail());
        return "코드 발송 완료!\n" + request.getEmail() + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public String confirmDeleteEmailSender() throws Exception {
        String myAccount = userFacade.getCurrentUser().getEmail();
        log.error(myAccount);
        emailService.sendWithdrawalMessage(myAccount);
        return "코드 발송 완료!\n" + myAccount + "에서 메일을 확인해주세요.";
    }

    @PostMapping("/checkedCode")
    @ResponseStatus(HttpStatus.OK)
    public boolean checkedCode(@RequestBody @Valid EmailCodeCheckRequestDto request) {
        // return true: 다음 화면
        return emailService.confirmCode(request.getCode());
    }
}
