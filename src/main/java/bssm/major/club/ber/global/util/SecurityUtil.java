package bssm.major.club.ber.global.util;

import bssm.major.club.ber.global.auth.CustomUserDetail;
import bssm.major.club.ber.global.exception.CustomException;
import bssm.major.club.ber.global.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {

    public static CustomUserDetail getCurrentUser() {
        CustomUserDetail principal = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getUser() == null) throw new CustomException(ErrorCode.USER_NOT_LOGIN);
        return principal;
    }

}
