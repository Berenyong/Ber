package bssm.major.club.ber.domain.post.manager.service;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import bssm.major.club.ber.domain.post.manager.facade.ManagerPostFacade;
import bssm.major.club.ber.domain.post.manager.facade.MangerPostCommentFacade;
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
    private final ManagerPostFacade managerPostFacade;
    private final MangerPostCommentFacade managerPostCommentFacade;

    @Transactional
    public void createComment(Long id, ManagerPostCommentRequestDto request) {
        ManagerPost managerPost = managerPostFacade.findById(id);
        ManagerPostComment managerPostComment = request.toEntity();

        managerPostComment.confirmWriter(userFacade.getCurrentUser());
        managerPostComment.confirmPost(managerPost);

        managerPostCommentRepository.save(managerPostComment);
    }

    @Transactional
    public void createReComment(Long commentId, ManagerPostReCommentCreateRequestDto request) {
        ManagerPostComment managerPostComment = managerPostCommentFacade.findCommentById(commentId);

        ManagerPostReComment managerPostReComment = managerPostReCommentRepository.save(request.toEntity());

        User user = userFacade.getCurrentUser();

        managerPostReComment.confirmParent(managerPostComment);
        managerPostReComment.confirmWriter(user);
        managerPostReComment.setWriterName(user.getNickname());
    }

    public List<ManagerPostCommentResponseDto> findAllDesc(Long id) {
        return managerPostCommentRepository.findAllDesc(id)
                .stream()
                .map(ManagerPostCommentResponseDto::new)
                .collect(Collectors.toList());
    }

    public void deleteComment(Long id) {
        ManagerPostComment managerPostComment = managerPostCommentFacade.findCommentById(id);

        if (!managerPostComment.getWriter().equals(userFacade.getCurrentUser())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        managerPostCommentRepository.delete(managerPostComment);
    }

    @Transactional
    public void deleteReComment(Long reCommentId) {
        ManagerPostReComment managerPostReComment = managerPostCommentFacade.findReById(reCommentId);

        if (!managerPostReComment.getWriter().equals(userFacade.getCurrentUser())) {
            throw new CustomException(ErrorCode.DONT_ACCESS_OTHER);
        }

        managerPostReCommentRepository.delete(managerPostReComment);
    }
}
