package bssm.major.club.ber.domain.category.post.service;

import bssm.major.club.ber.domain.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.category.post.repository.PostCategoryRepository;
import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
@Slf4j
public class PostCategoryService {
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final UserFacade userFacade;

    @Transactional
    public void createCategory(ManagerPost managerPost, List<PostCategory> categories) {
        log.error("실행됨22");
        User user = userFacade.getCurrentUser();

        log.error("실행됨33");
        categories.forEach(c -> {
                    postCategoryRepository.save(
                            PostCategory.builder()
                                    .name(c.getName())
                                    .build());

                    c.confirmManagerPost(managerPost);
                    c.confirmUser(user);
                    log.error(c.getUser().getEmail());
                }
        );

    }
}
