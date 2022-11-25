package bssm.major.club.ber.domain.post.domain.repository;

import bssm.major.club.ber.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
