package bssm.major.club.ber.domain.post.manager.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class PostTitleRequestDto {

    @NotBlank
    private String title;

}
