package bssm.major.club.ber.domain.manager_post.manager.facade;

import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.manager_post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ManagerPostFacade {

    private final ManagerPostRepository managerPostRepository;

    public ManagerPost findById(Long id) {
        return managerPostRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POSTS_NOT_FOUND));
    }

}
