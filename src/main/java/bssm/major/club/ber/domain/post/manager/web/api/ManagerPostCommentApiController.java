package bssm.major.club.ber.domain.post.manager.web.api;

import bssm.major.club.ber.domain.post.manager.service.ManagerPostCommentService;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCommentRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostReCommentCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostCommentResponseDto;
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
    public void create(@PathVariable Long id, @RequestBody @Valid ManagerPostCommentRequestDto request) {
        managerPostCommentService.createComment(id, request);
    }

    @PostMapping("/create/{postId}/{commentId}")
    public void saveReComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody ManagerPostReCommentCreateRequestDto request
    ) {
        managerPostCommentService.createReComment(commentId, request);
    }

    @GetMapping("/all/{id}")
    public Result<List<ManagerPostCommentResponseDto>> all(@PathVariable Long id) {
        List<ManagerPostCommentResponseDto> managerPostCommentResponse = managerPostCommentService.findAllDesc(id);
        return new Result<>(managerPostCommentResponse.size(), managerPostCommentResponse);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        managerPostCommentService.deleteComment(id);
    }

    @DeleteMapping("/delete/re/{id}")
    public void deleteRe(@PathVariable Long id) {
        managerPostCommentService.deleteReComment(id);
    }

}