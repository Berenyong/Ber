package bssm.major.club.ber.domain.category.post.service;

import bssm.major.club.ber.domain.category.post.domain.PostCategory;
import bssm.major.club.ber.domain.category.post.repository.PostCategoryRepository;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.post.manager.repository.ManagerPostRepository;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.global.util.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostCategoryService {
    private final UserRepository userRepository;
    private final PostCategoryRepository postCategoryRepository;
    private final ManagerPostRepository managerPostRepository;

    @Transactional
    public Long createCategory(ManagerPost managerPost, String category) {
        User user = userRepository.findByEmail(SecurityUtil.getLoginUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_LOGIN));

        PostCategory ca = postCategoryRepository.save(
                PostCategory.builder()
                        .name(category)
                        .build());

        ca.confirmManagerPost(managerPost);
        ca.confirmUser(user);

        System.out.println("category : " + ca.getId());
        return ca.getId();
    }
}
