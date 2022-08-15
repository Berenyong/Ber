package bssm.major.club.ber.domain.post.manager.web.api;

import bssm.major.club.ber.domain.post.manager.service.ManagerPostCommentService;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

}
