package bssm.major.club.ber.domain.post.facade;

import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PostFacade {

    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }
}
