package bssm.major.club.ber.domain.category.user.service;

import bssm.major.club.ber.domain.category.user.domain.UserCategory;
import bssm.major.club.ber.domain.category.user.repository.UserCategoryRepository;
import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.domain.user.domain.repository.UserRepository;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import bssm.major.club.ber.global.util.SecurityUtil;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserCategoryService {

    private final UserRepository userRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final UserFacade userFacade;

    @Transactional
    public void createCategory(String category) {
        User user = userFacade.getCurrentUser();

        UserCategory userCategory = userCategoryRepository.save(
                UserCategory.builder()
                        .name(category)
                        .build()
        );

        userCategory.confirmUser(user);
    }
}
