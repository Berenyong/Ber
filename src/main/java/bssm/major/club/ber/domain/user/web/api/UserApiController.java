package bssm.major.club.ber.domain.user.web.api;

import bssm.major.club.ber.domain.user.service.UserService;
import bssm.major.club.ber.domain.user.web.dto.user.*;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserApiController {

    private final UserService userService;

    @GetMapping
    public UserResponseDto getMyInfo() {
        return userService.findCurrentUser();
    }

    @PostMapping("/join")
    public UserResponseDto signup(@RequestBody @Valid UserJoinRequestDto request) throws Exception {
        return userService.signup(request);
    }

    @GetMapping("/find/{id}")
    public UserResponseDto User(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @GetMapping("/find/nickname")
    public Result<List<UserResponseDto>> findAll(@RequestBody @Valid UserFindByNicknameRequestDto request) {
        List<UserResponseDto> users = userService.findByNickname(request.getNickname());
        return new Result<>(users.size(), users);
    }

   @PutMapping("/update")
   public UserResponseDto update(@RequestBody UserUpdateRequestDto request) {
        return userService.updateUser(request);
   }

    @PutMapping("/update/password")
    public String updatePassword(@RequestBody @Valid UserPasswordRequestDto request) {
        return userService.updatePassword(request);
    }

    @PutMapping("/update/role/{id}")
    public void updateRole(@PathVariable Long id) {
        userService.authorization(id);
    }

    @PutMapping("/update/img")
    public void updateUserProfileImage(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        userService.updateImg(multipartFile);
    }

   @DeleteMapping("/delete")
   public String delete(@RequestBody UserDeleteRequestDto request) throws Exception {
        return userService.deleteUser(request);
   }
}
