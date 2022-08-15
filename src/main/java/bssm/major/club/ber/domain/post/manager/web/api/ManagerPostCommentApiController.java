package bssm.major.club.ber.domain.post.manager.web.api;

import bssm.major.club.ber.domain.post.manager.service.ManagerPostCommentService;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCommentRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostCommentResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/manager/comment")
@RestController
public class ManagerPostCommentApiController {

    private final ManagerPostCommentService managerPostCommentService;

    @PostMapping("/create/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long create(@PathVariable Long id, @RequestBody @Valid ManagerPostCommentRequestDto request) {
        return managerPostCommentService.createComment(id, request);
    }

    @GetMapping("/all/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result detail(@PathVariable Long id) {
        List<ManagerPostCommentResponseDto> managerPostCommentResponse = managerPostCommentService.findAllDesc(id);
        return new Result(managerPostCommentResponse.size(), managerPostCommentResponse);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long delete(@PathVariable Long id) {
        return managerPostCommentService.delete(id);
    }

}
