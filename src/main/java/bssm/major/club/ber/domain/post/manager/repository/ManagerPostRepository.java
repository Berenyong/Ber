package bssm.major.club.ber.domain.post.manager.repository;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPost;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerPostRepository extends JpaRepository<ManagerPost, Long> {

    @EntityGraph(attributePaths = {"writer"})
    @Query("select m from ManagerPost m where m.title = :title")
    Page<ManagerPost> findByTitle(@Param("title") String title, Pageable pageable);

    @NotNull
    @EntityGraph(attributePaths = {"writer"})
    @Query("select m from ManagerPost m order by m.likes.size desc")
    Page<ManagerPost> findAllByOrderByLikesDesc(@NotNull Pageable pageable);

}
