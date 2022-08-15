package bssm.major.club.ber.domain.post.manager.repository;

import bssm.major.club.ber.domain.post.manager.domain.ManagerPostComment;
import bssm.major.club.ber.domain.post.manager.domain.ManagerPostReComment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerPostReCommentRepository extends JpaRepository<ManagerPostReComment, Long> {

    @EntityGraph(attributePaths = {"writer", "managerPostComment"})
    @Query("select mpc from ManagerPostReComment mpc where mpc.parent.id = :id order by mpc.createAt")
    List<ManagerPostComment> findAllDesc(@Param("id") Long id);

}
