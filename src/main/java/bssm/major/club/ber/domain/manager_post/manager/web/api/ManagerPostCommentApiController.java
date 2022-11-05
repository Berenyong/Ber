package bssm.major.club.ber.domain.manager_post.manager.web.api;

import bssm.major.club.ber.domain.manager_post.manager.service.ManagerPostCommentService;
import bssm.major.club.ber.domain.manager_post.manager.web.dto.request.ManagerPostCommentRequestDto;
import bssm.major.club.ber.domain.manager_post.manager.web.dto.request.ManagerPostReCommentCreateRequestDto;
import bssm.major.club.ber.domain.manager_post.manager.web.dto.response.ManagerPostCommentResponseDto;
import bssm.major.club.ber.global.generic.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/manager/comment")
@RestController
public class ManagerPostCommentApiController {

    private final ManagerPostCommentService managerPostCommentService;

    @PostMapping("/create/{id}")
    public Long create(@PathVariable Long id, @RequestBody @Valid ManagerPostCommentRequestDto request) {
        return managerPostCommentService.createComment(id, request);
    }

    @PostMapping("/create/{postId}/{commentId}")
    public Long saveRe(@PathVariable("postId") Long postId,
                       @PathVariable("commentId") Long commentId,
                       @RequestBody ManagerPostReCommentCreateRequestDto request) {
        return managerPostCommentService.createReComment(postId, commentId, request);
    }

    @GetMapping("/all/{id}")
    public Result<List<ManagerPostCommentResponseDto>> all(@PathVariable Long id) {
        List<ManagerPostCommentResponseDto> managerPostCommentResponse = managerPostCommentService.findAllDesc(id);
        return new Result<>(managerPostCommentResponse.size(), managerPostCommentResponse);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable Long id) {
        return managerPostCommentService.delete(id);
    }

    @DeleteMapping("/delete/re/{id}")
    public Long deleteRe(@PathVariable Long id) {
        return managerPostCommentService.deleteRe(id);
    }

}