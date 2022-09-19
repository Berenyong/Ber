package bssm.major.club.ber.global.config.file;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileResponseDto {

    private String imgPath;
    private String imgUrl;

    @Builder
    public FileResponseDto(String imgPath, String imgUrl) {
        this.imgPath = imgPath;
        this.imgUrl = imgUrl;
    }
}
