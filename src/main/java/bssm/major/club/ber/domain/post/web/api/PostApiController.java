package bssm.major.club.ber.domain.post.web.api;

import bssm.major.club.ber.domain.post.domain.type.PostKind;
import bssm.major.club.ber.domain.post.service.PostService;
import bssm.major.club.ber.domain.post.web.dto.request.PostRequestDto;
import bssm.major.club.ber.domain.post.web.dto.response.PostResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/post")
@RestController
public class PostApiController {
    private final PostService postService;

    @PostMapping("/api/create")
    public Long createPost(@RequestBody PostRequestDto request){
        return postService.createPost(request);
    }

//    @GetMapping("/find/{postKind}")
//    public Result<List<PostResponseDto>> findByPostKind(@PathVariable PostKind postKind, ){
//        postService.findByPostKind(postKind);
//    }
}
