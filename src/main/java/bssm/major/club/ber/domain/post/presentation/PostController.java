package bssm.major.club.ber.domain.post.presentation;

import bssm.major.club.ber.domain.post.presentation.dto.req.PostCreateRequestDto;
import bssm.major.club.ber.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostController {

    private final PostService postService;

    @PostMapping
    public void create(PostCreateRequestDto req) {
        postService.createPost(req);
    }
}
