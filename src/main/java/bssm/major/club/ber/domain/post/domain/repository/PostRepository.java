package bssm.major.club.ber.domain.post.domain.repository;

import bssm.major.club.ber.domain.post.domain.Post;
import bssm.major.club.ber.domain.post.domain.type.PostKind;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.postKind = (:postKind) order by p.view , p.createdAt asc")
    Page<Post> findByPostKind(Pageable pageable, PostKind postKind);

    @Query("select p from Post p where p.postKind = :postKind and p.title like :title order by p.view desc, p.createdAt asc")
    Page<Post> findByTitle(Pageable pageable, PostKind postKind, String title);
}
