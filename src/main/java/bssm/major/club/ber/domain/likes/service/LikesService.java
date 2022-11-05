package bssm.major.club.ber.domain.likes.service;

import bssm.major.club.ber.domain.likes.domain.Likes;
import bssm.major.club.ber.domain.likes.repository.LikesRepository;
import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.manager_post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final UserFacade userFacade;
    private final ManagerPostRepository managerPostRepository;

    @Transactional
    public String mlikes(long postId) {
        User user = userFacade.getCurrentUser();

        ManagerPost managerPost = managerPostRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));

        List<Likes> collect = managerPost.getLikes().stream()
                .filter(l -> Objects.equals(l.getUser().getId(), user.getId()))
                .collect(Collectors.toList());

        if (collect.size() == 0) {
            likesRepository.mlikes(postId, user.getId());
            return "좋아요";
        } else {
            likesRepository.munLikes(postId, user.getId());
            return "좋아요 취소";
        }
    }

}
