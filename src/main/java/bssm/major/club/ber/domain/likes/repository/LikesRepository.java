package bssm.major.club.ber.domain.likes.repository;

import bssm.major.club.ber.domain.likes.domain.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    @Modifying
    @Query(value = "INSERT INTO likes(manager_post_id, user_id) VALUES (:postId, :userId)", nativeQuery = true)
    void mlikes(@Param("postId") long postId, @Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE manager_post_id = :postId AND user_id = :userId", nativeQuery = true)
    void munLikes(@Param("postId") long postId, @Param("userId") long userId);
}
