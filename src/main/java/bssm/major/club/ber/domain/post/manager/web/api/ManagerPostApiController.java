package bssm.major.club.ber.domain.post.manager.web.api;

import bssm.major.club.ber.domain.post.manager.service.ManagerPostService;
import bssm.major.club.ber.domain.post.manager.web.dto.request.PostTitleRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerPostApiController {

    private final ManagerPostService managerPostService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.OK)
    public Long create(@RequestBody ManagerPostCreateRequestDto request) {
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
                              @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                              Pageable pageable) {
        List<ManagerPostResponseDto> post = managerPostService.findByTitle(request.getTitle(), pageable);
        return new Result(post.size(), post);
    }

    @GetMapping("/find/all")
    @ResponseStatus(HttpStatus.OK)
    public Result pagePosts(
            @PageableDefault(size = 10)
            Pageable pageable) {

        List<ManagerPostResponseDto> managerPost = managerPostService.all(pageable);

        return new Result(managerPost.size(), managerPost);
    }

    @PutMapping("/update/{id}")
    public ManagerPostResponseDto update(@PathVariable Long id, @RequestBody @Valid ManagerPostCreateRequestDto request) {
        return managerPostService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        return managerPostService.delete(id);
    }

}