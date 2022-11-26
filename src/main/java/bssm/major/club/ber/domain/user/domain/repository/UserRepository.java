package bssm.major.club.ber.domain.user.domain.repository;

import bssm.major.club.ber.domain.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = {"managerPost"})
    @Query("select u from User u where u.nickname like %:nickname%")
    List<User> findByNickname(@Param("nickname") String nickname);

    @EntityGraph(attributePaths = {"managerPost"})
    Optional<User> findByEmail(String email);

    void deleteByEmail(String email);

    @Modifying
    @Query("update User u set u.role = 'ROLE_MANAGER' where u.id = :id")
    void updateRole(@Param("id") Long id);
}
