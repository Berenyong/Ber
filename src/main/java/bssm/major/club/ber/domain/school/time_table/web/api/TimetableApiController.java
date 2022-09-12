package bssm.major.club.ber.domain.school.time_table.web.api;

import bssm.major.club.ber.domain.school.time_table.service.TimetableService;
import bssm.major.club.ber.domain.school.time_table.web.api.dto.TimeTableResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/timetable")
@RequiredArgsConstructor
public class TimetableApiController {

    private final TimetableService timetableService;

    @GetMapping("/{grade}/{classNo}/{day}")
    public TimeTableResponseDto getTimetable(
            @PathVariable int grade,
            @PathVariable int classNo,
            @PathVariable int day
    ) throws IOException {
        return timetableService.getTimetable(grade, classNo, day);
    }

}