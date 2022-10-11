package bssm.major.club.ber.domain.user.facade;

import bssm.major.club.ber.domain.user.domain.User;
import bssm.major.club.ber.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {

    public User getCurrentUser() {
        return SecurityUtil.getCurrentUser().getUser();
    }

}