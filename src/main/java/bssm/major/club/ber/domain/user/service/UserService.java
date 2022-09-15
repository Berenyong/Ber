package bssm.major.club.ber.domain.user.service;

import bssm.major.club.ber.domain.category.user.service.UserCategoryService;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.web.dto.user.*;
import bssm.major.club.ber.global.config.security.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserCategoryService userCategoryService;
    private String email;

    @Transactional
    public UserResponseDto signup(UserJoinRequestDto request) throws Exception {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXISTS_USER);
        }

        if (!request.getPassword().equals(request.getCheckPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        if (emailService.verifyCode(request.getCheckEmailCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_CODE);
        }

        User user = userRepository.save(request.toEntity());
        user.encodePassword(passwordEncoder);
        user.addUserAuthority();

        if (request.getGender().equals("MAN")) {
            user.setMan();
        } else {
            user.setWoman();
        }

        return new UserResponseDto(user);
    }

    public UserResponseDto findUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new UserResponseDto(user);
    }

    public List<UserResponseDto> findByNickname(String nickname) {
        return userRepository.findByNickname(nickname)
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseDto updateUser(UserUpdateRequestDto request) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        user.updatePassword(passwordEncoder, request.getPassword());
        user.updateNickname(request.getNickname());
        user.updateGitLink(request.getGitLink());
        user.updateBlogLink(request.getBlogLink());
        user.updateStatusMessage(request.getStatusMassage());

        request.getCategories()
                .forEach(c -> userCategoryService.createCategory(c.getName()));

        return new UserResponseDto(user);
    }

    @Transactional
    public String updatePassword(UserPasswordRequestDto request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!Objects.equals(request.getPassword(), request.getCheckPassword())) {
            throw new CustomException(ErrorCode.NOT_MATCH_PASSWORD);
        }

        user.updatePassword(passwordEncoder, request.getPassword());

        this.email = null;

        return "비밀번호가 정상적으로 변경되었습니다.";
    }

    @Transactional
    public String deleteUser(UserDeleteRequestDto request) throws Exception{
        if (SecurityUtil.getLoginUserEmail() == null) {
            throw new CustomException(ErrorCode.USER_NOT_LOGIN);
        }

        if (emailService.verifyCode(request.getCheckEmailCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_CODE);
        }

        String myAccount = SecurityUtil.getLoginUserEmail();
        userRepository.deleteByEmail(myAccount);
        
        return myAccount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transactional
    public void authorization(Long id) {
        userRepository.updateRole(id);
    }
}