package bssm.major.club.ber.domain.manager_post.manager.service;

import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPost;
import bssm.major.club.ber.domain.manager_post.manager.domain.PostImg;
import bssm.major.club.ber.domain.manager_post.manager.repository.PostImgRepository;
import bssm.major.club.ber.global.file.FileResponseDto;
import bssm.major.club.ber.global.file.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class PostImgService {

    private final PostImgRepository postImgRepository;
    private final S3Uploader s3Uploader;

    @Transactional
    public void updatePostImg(ManagerPost managerPost, MultipartFile multipartFile) throws IOException {
        FileResponseDto fileResponseDto = s3Uploader.saveFile(multipartFile);

        PostImg postImg = PostImg.builder()
                .imgPath(fileResponseDto.getImgPath())
                .imgUrl(fileResponseDto.getImgUrl())
                .managerPost(managerPost)
                .build();

        postImgRepository.save(postImg);
    }
}
