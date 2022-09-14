package bssm.major.club.ber.domain.category.web.dto.response;

import bssm.major.club.ber.domain.category.user.domain.UserCategory;
import lombok.Getter;

import java.util.List;

@Getter
public class UserCategoryResponseDto {
    private final List<UserCategory> categories;

    public UserCategoryResponseDto(List<UserCategory> categories) {
        this.categories = categories;
    }
}
