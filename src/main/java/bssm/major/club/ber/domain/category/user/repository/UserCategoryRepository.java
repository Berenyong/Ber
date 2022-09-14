package bssm.major.club.ber.domain.category.user.repository;

import bssm.major.club.ber.domain.category.user.domain.UserCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {
}
