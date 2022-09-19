package bssm.major.club.ber.domain.school.time_table.web.api.dto;

import bssm.major.club.ber.domain.school.time_table.entity.TimeTable;
import lombok.Getter;

@Getter
public class TimeTableResponseDto {

    private final Long idx;
    private final String classname;
    private final int dayOfWeek;

    public TimeTableResponseDto(TimeTable timeTable) {
        this.idx = timeTable.getIdx();
        this.classname = timeTable.getClassName();
        this.dayOfWeek = Math.toIntExact(timeTable.getDayOfWeek());
    }
}