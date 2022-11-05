package bssm.major.club.ber.domain.manager_post.manager.repository;

import bssm.major.club.ber.domain.manager_post.manager.domain.ManagerPostComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerPostCommentRepository extends JpaRepository<ManagerPostComment, Long> {

    @EntityGraph(attributePaths = {"writer", "managerPost"})
    @Query("select mpc from ManagerPostComment mpc where mpc.managerPost.id = :id order by mpc.createdAt desc")
    List<ManagerPostComment> findAllDesc(@Param("id") Long id);

}
