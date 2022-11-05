package bssm.major.club.ber.domain.post.service;

import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.facade.PostFacade;
import bssm.major.club.ber.domain.post.presentation.dto.req.PostCreateRequestDto;
import bssm.major.club.ber.domain.user.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostFacade postFacade;
    private final UserFacade userFacade;

    @Transactional
    public void createPost(PostCreateRequestDto req) {
        Post post = postFacade.save(req.toEntity());
        post.confirmWriter(userFacade.getCurrentUser());
    }
}
