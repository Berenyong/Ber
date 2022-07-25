package bssm.major.club.ber.domain.school.time_table.entity.repository;

import bssm.major.club.ber.domain.school.time_table.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeTableRepository extends JpaRepository<TimeTable, Long> {

    @Query("select t from TimeTable t where t.grade = :grade and t.classNo = :classNo")
    List<TimeTable> getTimeTable(@Param("grade") long grade, @Param("classNo") long classNo);

}
