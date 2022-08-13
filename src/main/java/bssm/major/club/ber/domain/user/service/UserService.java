package bssm.major.club.ber.domain.user.service;

import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.web.dto.user.UserDeleteRequestDto;
import bssm.major.club.ber.domain.user.web.dto.user.UserUpdateRequestDto;
import bssm.major.club.ber.domain.user.web.dto.user.UserJoinRequestDto;
import bssm.major.club.ber.domain.user.web.dto.user.UserResponseDto;
import bssm.major.club.ber.global.config.security.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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

        return new UserResponseDto(user);
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
}
