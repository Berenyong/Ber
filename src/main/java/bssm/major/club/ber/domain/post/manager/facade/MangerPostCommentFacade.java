package bssm.major.club.ber.domain.post.manager.facade;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostCommentRepository;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostReCommentRepository;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MangerPostCommentFacade {

    private final ManagerPostCommentRepository managerPostCommentRepository;
    private final ManagerPostReCommentRepository managerPostReCommentRepository;

    public ManagerPostComment findCommentById(Long id) {
        return managerPostCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));
    }

    public ManagerPostReComment findReById(Long id) {
        return managerPostReCommentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENTS_NOT_FOUND));
    }
}
