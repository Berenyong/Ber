package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostCommentRepository;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCommentRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostCommentResponseDto;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.global.config.security.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ManagerPostCommentService {

    private final ManagerPostCommentRepository managerPostCommentRepository;
    private final UserRepository userRepository;
    private final ManagerPostRepository managerPostRepository;

    @Transactional
    public Long createComment(Long id, ManagerPostCommentRequestDto request) {
        Optional<ManagerPost> byId = Optional.of(managerPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND)));

        ManagerPostComment managerPostComment = request.toEntity();

        managerPostComment
                .confirmWriter(userRepository
                        .findByEmail(SecurityUtil.getLoginUserEmail())
                        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN)));

        managerPostComment
                .confirmPost(managerPostRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND)));

        managerPostCommentRepository.save(managerPostComment);

        return managerPostComment.getId();
    }

    public List<ManagerPostCommentResponseDto> findAllDesc(Long id) {
        return managerPostCommentRepository.findAllDesc(id)
                .stream()
                .map(ManagerPostCommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
