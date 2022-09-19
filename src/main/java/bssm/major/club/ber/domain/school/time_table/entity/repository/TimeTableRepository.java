package bssm.major.club.ber.domain.school.time_table.entity.repository;

import bssm.major.club.ber.domain.school.time_table.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {
}
