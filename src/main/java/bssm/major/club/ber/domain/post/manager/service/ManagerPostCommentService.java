package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostCommentRepository;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostReCommentRepository;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCommentRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostReCommentCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostCommentResponseDto;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class ManagerPostCommentService {

    private final ManagerPostCommentRepository managerPostCommentRepository;
    private final ManagerPostRepository managerPostRepository;
    private final ManagerPostReCommentRepository managerPostReCommentRepository;
    private final UserFacade userFacade;

    @Transactional
    public Long createComment(Long id, ManagerPostCommentRequestDto request) {
        Optional<ManagerPost> byId = Optional.of(managerPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND)));

        ManagerPostComment managerPostComment = request.toEntity();

        managerPostComment.confirmWriter(userFacade.getCurrentUser());

        managerPostComment
                .confirmPost(managerPostRepository
                        .findById(id)
                        .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND)));

        managerPostCommentRepository.save(managerPostComment);

        return managerPostComment.getId();
    }

    @Transactional
    public Long createReComment(Long postId, Long commentId, ManagerPostReCommentCreateRequestDto request) {

        ManagerPost managerPost = managerPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        ManagerPostComment managerPostComment = managerPostCommentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));

        ManagerPostReComment managerPostReComment = managerPostReCommentRepository.save(request.toEntity());

        User user = userFacade.getCurrentUser();

        managerPostReComment.confirmParent(managerPostComment);
        managerPostReComment.confirmWriter(user);
        managerPostReComment.setWriterName(user.getNickname());

        return managerPostReComment.getId();
    }

    public List<ManagerPostCommentResponseDto> findAllDesc(Long id) {
        return managerPostCommentRepository.findAllDesc(id)
                .stream()
                .map(ManagerPostCommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public Long delete(Long id) {
        ManagerPostComment managerPostComment = managerPostCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        if (!managerPostComment.getWriter().equals(userFacade.getCurrentUser())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        managerPostCommentRepository.delete(managerPostComment);

        return id;
    }

    @Transactional
    public Long deleteRe(Long id) {
        ManagerPostReComment managerPostReComment = managerPostReCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));

        log.error(managerPostReComment.getWriter().getEmail());
        if (!managerPostReComment.getWriter().equals(userFacade.getCurrentUser())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        managerPostReCommentRepository.delete(managerPostReComment);

        return id;
    }
}
