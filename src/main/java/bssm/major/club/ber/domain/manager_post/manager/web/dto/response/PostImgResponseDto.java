package bssm.major.club.ber.domain.manager_post.manager.web.dto.response;

import bssm.major.club.ber.domain.manager_post.manager.domain.PostImg;
import lombok.Getter;

@Getter
public class PostImgResponseDto {

    private final String imgUrl;

    public PostImgResponseDto(PostImg postImg) {
        this.imgUrl = postImg.getImgUrl();
    }
}
