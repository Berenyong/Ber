package bssm.major.club.ber.domain.category.web.api;

import bssm.major.club.ber.domain.category.user.service.UserCategoryService;
import bssm.major.club.ber.domain.category.web.dto.response.UserCategoryResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
public class UserCategoryApiController {

    private final UserCategoryService userCategoryService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result findUserCategoryById(@PathVariable Long id) {
        UserCategoryResponseDto response = userCategoryService.findById(id);

        return new Result(response.getCategories().size(), response);
    }
}
