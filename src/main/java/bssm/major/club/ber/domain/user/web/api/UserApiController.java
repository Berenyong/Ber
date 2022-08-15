package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.service.EmailService;
import bssm.major.club.ber.domain.user.service.UserService;
import bssm.major.club.ber.domain.user.web.dto.user.*;
import bssm.major.club.ber.domain.user.web.dto.email.EmailDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto signup(@RequestBody @Valid UserJoinRequestDto request) throws Exception {
        return userService.signup(request);
    }

    @GetMapping("/find/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResponseDto User(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @GetMapping("/find/nickname")
    @ResponseStatus(HttpStatus.OK)
    public Result findAll(@RequestBody @Valid UserFindByNicknameRequestDto request) {
        List<UserResponseDto> users = userService.findByNickname(request.getNickname());
        return new Result(users.size(), users);
    }

   @PutMapping("/update")
   @ResponseStatus(HttpStatus.OK)
   public UserResponseDto update(@RequestBody UserUpdateRequestDto request) {
        return userService.updateUser(request);
   }

    @PutMapping("/update/password")
    @ResponseStatus(HttpStatus.OK)
    public String updatePassword(@RequestBody @Valid UserPasswordRequestDto request) {
        return userService.updatePassword(request);
    }

   @DeleteMapping("/delete")
   @ResponseStatus(HttpStatus.OK)
   public String delete(@RequestBody UserDeleteRequestDto request) throws Exception {
        return userService.deleteUser(request);
   }
}
