package bssm.major.club.ber.domain.post.manager.web.api;

import bssm.major.club.ber.domain.post.manager.service.ManagerPostService;
import bssm.major.club.ber.domain.post.manager.web.dto.PostTitleRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerPostApiController {

    private final ManagerPostService managerPostService;

    @PostMapping("/create")
    public Long create(@RequestBody ManagerPostCreateRequestDto request) {
        return managerPostService.createPost(request);
    }

    @GetMapping("/find/detail/{id}")
    public ManagerPostResponseDto detail(@PathVariable Long id) {
        return managerPostService.detail(id);
    }

    @GetMapping("/find/title")
    public Result findByTitle(@RequestBody @Valid PostTitleRequestDto request,
                              @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                              Pageable pageable) {
        List<ManagerPostResponseDto> post = managerPostService.findByTitle(request.getTitle(), pageable);
        return new Result(post.size(), post);
    }

}
