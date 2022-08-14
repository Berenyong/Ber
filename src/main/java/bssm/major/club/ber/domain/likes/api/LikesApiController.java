package bssm.major.club.ber.domain.likes.api;

import bssm.major.club.ber.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/likes")
@RestController
public class LikesApiController {

    private final LikesService likesService;

    @PutMapping("/manager/{id}")
    public String mlikes(@PathVariable long id) {
        return likesService.mlikes(id);
    }

}