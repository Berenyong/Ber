package bssm.major.club.ber.domain.post.web.api;

import bssm.major.club.ber.domain.post.service.PostService;
import bssm.major.club.ber.domain.post.web.dto.request.PostRequestDto;
import bssm.major.club.ber.domain.post.web.dto.response.PostResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping("/find/{postKind}")
    public Result<List<PostResponseDto>> findByPostKind(@PathVariable String postKindTitle,
                                                        @PageableDefault(size = 9) Pageable pageable){
        List<PostResponseDto> posts = postService.findByPostKind(pageable, postKindTitle);

        return new Result<>(posts.size(), posts);
    }

    @GetMapping("/find/{postKind}/{postTile}")
    public Result<List<PostResponseDto>> findByPostTItle(@PathVariable String postKindTitle, @PathVariable String postTitle, @PageableDefault Pageable pageable){
        List<PostResponseDto> posts = postService.findByTitle(pageable, postKindTitle, postTitle);

        return new Result<>(posts.size(), posts);
    }

    @PutMapping("api/update/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto){
        return postService.update(id, postRequestDto);
    }

    @DeleteMapping("api/delete/{id}")
    public Long delete(@PathVariable Long id){
        return postService.delete(id);
    }
}
