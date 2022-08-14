package bssm.major.club.ber.domain.post.manager.web.api;

import bssm.major.club.ber.domain.post.manager.service.ManagerPostService;
import bssm.major.club.ber.domain.post.manager.web.dto.request.ManagerPostCreateRequestDto;
import bssm.major.club.ber.domain.post.manager.web.dto.response.ManagerPostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/manager")
@RestController
public class ManagerPostApiController {

    private final ManagerPostService managerPostService;

    @PostMapping("/create")
    public ManagerPostResponseDto create(@RequestBody ManagerPostCreateRequestDto request) {
        return managerPostService.createPost(request);
    }

}
