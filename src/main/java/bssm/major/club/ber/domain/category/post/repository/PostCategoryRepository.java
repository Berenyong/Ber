package bssm.major.club.ber.domain.category.post.repository;

import bssm.major.club.ber.domain.category.post.domain.PostCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCategoryRepository extends JpaRepository<PostCategory, Long> {
}
