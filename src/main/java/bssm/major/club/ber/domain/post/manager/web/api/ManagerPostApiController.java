package bssm.major.club.ber.domain.post.manager.web.api;

import bssm.major.club.ber.domain.post.manager.service.ManagerPostService;
import bssm.major.club.ber.domain.post.manager.web.dto.request.PostTitleRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerPostApiController {

    private final ManagerPostService managerPostService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Long create(ManagerPostCreateRequestDto request) throws IOException {
        return managerPostService.createPost(request);
    }

    @GetMapping("/find/detail/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ManagerPostResponseDto detail(@PathVariable Long id) {
        return managerPostService.detail(id);
    }

    @GetMapping("/find/title")
    @ResponseStatus(HttpStatus.OK)
    public Result findByTitle(@RequestBody @Valid PostTitleRequestDto request,
                              @PageableDefault(size = 9)
                              Pageable pageable) {
        List<ManagerPostResponseDto> post = managerPostService.findByTitle(request.getTitle(), pageable);

        return new Result(post.size(), post);
    }

    @GetMapping("/find/popular")
    @ResponseStatus(HttpStatus.OK)
    public Result popularPosts(
            @PageableDefault(size = 9)
            Pageable pageable) {
        List<ManagerPostResponseDto> managerPost = managerPostService.popularPosts(pageable);

        return new Result(managerPost.size(), managerPost);
    }

    @GetMapping("/find/all")
    @ResponseStatus(HttpStatus.OK)
    public Result allPosts(
            @PageableDefault(size = 9)
            Pageable pageable) {

        List<ManagerPostResponseDto> response = managerPostService.allPosts(pageable);
        return new Result(response.size(), response);
    }

    @PutMapping("/update/{id}")
    public ManagerPostResponseDto update(@PathVariable Long id, ManagerPostCreateRequestDto request) throws IOException {
        return managerPostService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return managerPostService.delete(id);
    }

}